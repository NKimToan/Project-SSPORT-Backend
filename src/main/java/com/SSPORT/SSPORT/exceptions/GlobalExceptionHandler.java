package com.SSPORT.SSPORT.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.SSPORT.SSPORT.responses.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(HandleRuntimeException.class)
	ResponseEntity<ApiResponse> handleRuntimeException(HandleRuntimeException handleRuntimeException) {
		ErrorCode errorCode = handleRuntimeException.getErrorCode();
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());
		apiResponse.setResult(null);
		
		return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);	
	}

	
	@ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse> handlingAuthorizationDeniedException(AuthorizationDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }
	
	
	@ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        apiResponse.setResult(null);

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
