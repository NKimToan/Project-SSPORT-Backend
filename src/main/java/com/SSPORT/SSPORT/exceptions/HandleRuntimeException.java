package com.SSPORT.SSPORT.exceptions;


public class HandleRuntimeException extends RuntimeException {
	private ErrorCode errorCode;

    public HandleRuntimeException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
