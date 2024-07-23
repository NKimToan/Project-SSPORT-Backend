package com.SSPORT.SSPORT.configuration;

import java.text.ParseException;
import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.SSPORT.SSPORT.requests.VerifyTokenRequest;
import com.SSPORT.SSPORT.services.AdminLoginService;
import com.SSPORT.SSPORT.services.CustomerLoginService;
import com.nimbusds.jose.JOSEException;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Component
@Slf4j
public class CustomJwtDecoder implements JwtDecoder {
	
	@Value("${jwt.signerKey}")
    private String signerKey;
	
	@Autowired
	private AdminLoginService adminLoginService;
	
	@Autowired
	private CustomerLoginService customerLoginService;
	
	private NimbusJwtDecoder nimbusJwtDecoder = null;
	
	
	@Override
	public Jwt decode(String token) {

		try {
			var tokenAdminResponse = adminLoginService.verify(VerifyTokenRequest.builder().token(token).build());
			var tokenCustomerResponse = customerLoginService.verify(VerifyTokenRequest.builder().token(token).build());
			if (!tokenAdminResponse.isValid() || !tokenCustomerResponse.isValid()) {
				throw new JwtException("Token invalid");
			}
			
		} catch (JOSEException | ParseException e) {
			throw new JwtException(e.getMessage());
		} 
		
		if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS256");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS256)
                    .build();
        }
		
		return nimbusJwtDecoder.decode(token);
	}
	
}
