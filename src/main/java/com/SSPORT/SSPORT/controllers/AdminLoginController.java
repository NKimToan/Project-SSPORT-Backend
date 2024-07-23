package com.SSPORT.SSPORT.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SSPORT.SSPORT.exceptions.ScopeInvalid;
import com.SSPORT.SSPORT.requests.LoginRequest;
import com.SSPORT.SSPORT.requests.LogoutRequest;
import com.SSPORT.SSPORT.requests.RefreshTokenRequest;
import com.SSPORT.SSPORT.requests.VerifyTokenRequest;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.SSPORT.SSPORT.responses.LoginResponse;
import com.SSPORT.SSPORT.responses.VerifyTokenResponse;
import com.SSPORT.SSPORT.services.AdminLoginService;
import com.nimbusds.jose.JOSEException;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/admin/auth/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminLoginController {

	@Autowired
	private AdminLoginService adminLoginService;

	@PostMapping("/login")
	ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest)
			throws JOSEException, ParseException {
		return ApiResponse.<LoginResponse>builder().code(200).message("Login successfull!!!")
				.result(adminLoginService.login(loginRequest)).build();
	}

	@PostMapping("/verify")
	ApiResponse<VerifyTokenResponse> verify(@Valid @RequestBody VerifyTokenRequest verifyTokenRequest)
			throws JOSEException, ParseException {
		return ApiResponse.<VerifyTokenResponse>builder().code(200).message("Verify successfull!!!")
				.result(adminLoginService.verify(verifyTokenRequest)).build();
	}

	@PostMapping("/refresh")
	ApiResponse<LoginResponse> refreshNewToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
			throws JOSEException, ParseException {
		try {
			return ApiResponse.<LoginResponse>builder().code(200).message("New token")
					.result(adminLoginService.refreshNewToken(refreshTokenRequest)).build();
		} catch (ScopeInvalid e) {
			return ApiResponse.<LoginResponse>builder().code(401).message("Token invalid").result(null).build();
		}
	}

	@PostMapping("/logout")
	ApiResponse<Void> logout(@Valid @RequestBody LogoutRequest request) throws JOSEException, ParseException {
		try {
			adminLoginService.logout(request);
			return ApiResponse.<Void>builder().code(200).message("Logout successfull").result(null).build();
		} catch (ScopeInvalid e) {
			return ApiResponse.<Void>builder().code(401).message("Token invalid").result(null).build();
		}
	}
}
