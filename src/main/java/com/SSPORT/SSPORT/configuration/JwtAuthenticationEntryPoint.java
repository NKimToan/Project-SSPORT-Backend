package com.SSPORT.SSPORT.configuration;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED; 
        response.setStatus(errorCode.getStatusCode().value()); 
        response.setContentType(
                org.springframework.http.MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .result(null)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse)); // Định dạng lại response

        response.flushBuffer(); // Gửi request về client
    }
}
