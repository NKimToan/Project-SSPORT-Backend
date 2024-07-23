package com.SSPORT.SSPORT.services;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SSPORT.SSPORT.entities.Customer;
import com.SSPORT.SSPORT.entities.Token;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.exceptions.ScopeInvalid;
import com.SSPORT.SSPORT.repositories.CustomerRepository;
import com.SSPORT.SSPORT.repositories.TokenRepository;
import com.SSPORT.SSPORT.requests.LoginRequest;
import com.SSPORT.SSPORT.requests.LogoutRequest;
import com.SSPORT.SSPORT.requests.RefreshTokenRequest;
import com.SSPORT.SSPORT.requests.VerifyTokenRequest;
import com.SSPORT.SSPORT.responses.LoginResponse;
import com.SSPORT.SSPORT.responses.VerifyTokenResponse;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerLoginService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@NonFinal
	@Value("${jwt.signerKey}")
	protected String SIGNER_KEY;

	@NonFinal
	@Value("${jwt.valid-duration}")
	protected long VALID_DURATION;

	@NonFinal
	@Value("${jwt.refreshable-duration}")
	protected long REFRESHABLE_DURATION;

	private String generateToken(Customer customer) {

		JWSHeader headerToken = new JWSHeader(JWSAlgorithm.HS256);

		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().subject(customer.getUsername())
				.issuer("demoLoginSSport.com").issueTime(new Date())
				.expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
				.jwtID(UUID.randomUUID().toString()) 
				.claim("scope", "ROLE_" + customer.getRole()).build();

		Payload payload = new Payload(jwtClaimsSet.toJSONObject());

		JWSObject jwsObject = new JWSObject(headerToken, payload);

		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize();
		} catch (JOSEException e) {
			throw new RuntimeException();
		}
	}

	private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {

		JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

		SignedJWT signedJWT = SignedJWT.parse(token);

		Date expiryTime = (isRefresh)
				? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant()
						.plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
				: signedJWT.getJWTClaimsSet().getExpirationTime();

		var verified = signedJWT.verify(verifier);

		if (!(verified && expiryTime.after(new Date()))) {
			throw new HandleRuntimeException(ErrorCode.TOKEN_INVALID);
		}

		if (tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
			throw new RuntimeException();
		}

		return signedJWT;
	}

	public LoginResponse login(LoginRequest loginRequest) throws JOSEException, ParseException {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

		var user = customerRepository.findByUsername(loginRequest.getUsername())
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CUSTOMER_NOT_EXISTED));

		boolean password = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

		if (!password) {
			throw new HandleRuntimeException(ErrorCode.CUSTOMER_LOGIN_FAILED);
		}

		var token = generateToken(user);
		return LoginResponse.builder().authenticated(password).token(token).build();
	}

	public VerifyTokenResponse verify(VerifyTokenRequest request) throws JOSEException, ParseException {
		var token = request.getToken();
		boolean isValid = true;
		try {
			verifyToken(token, false);
		} catch (RuntimeException e) {
			isValid = false;
		}

		return VerifyTokenResponse.builder().valid(isValid).build();
	}

	public LoginResponse refreshNewToken(RefreshTokenRequest refreshTokenRequest) throws JOSEException, ParseException {

		var signedJWT = verifyToken(refreshTokenRequest.getToken(), true);

		String scope = (String) signedJWT.getJWTClaimsSet().getClaim("scope");

		if ("ROLE_CUSTOMER".equals(scope)) {
			var jit = signedJWT.getJWTClaimsSet().getJWTID();

			var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

			Token token = Token.builder().Id(jit).expiryTime(expiryTime).build();

			tokenRepository.save(token);

			var username = signedJWT.getJWTClaimsSet().getSubject();
			var customer = customerRepository.findByUsername(username)
					.orElseThrow(() -> new HandleRuntimeException(ErrorCode.TOKEN_NOT_FOUND));

			var tokenRefresh = generateToken(customer);
			return LoginResponse.builder().token(tokenRefresh).authenticated(true).build();
		} else {
			throw new ScopeInvalid("Token Invalid");
		}
	}

	public void logout(LogoutRequest logoutRequest) throws JOSEException, ParseException {
		var signToken = verifyToken(logoutRequest.getToken(), true);
		String scope = (String) signToken.getJWTClaimsSet().getClaim("scope");
		if ("ROLE_CUSTOMER".equals(scope)) {
			String jit = signToken.getJWTClaimsSet().getJWTID();
			Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

			Token token = Token.builder().Id(jit).expiryTime(expiryTime).build();

			tokenRepository.save(token);
		} else {
			throw new ScopeInvalid("Token Invalid");
		}
	}

}
