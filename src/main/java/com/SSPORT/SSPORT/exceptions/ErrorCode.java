package com.SSPORT.SSPORT.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(
            9999,
            "ERROR",
            HttpStatus.INTERNAL_SERVER_ERROR), // Lỗi mặc định được thông báo khi exception xảy ra nhưng chưa được xử lý
    UNAUTHENTICATED(401, "You unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "You do not have permission", HttpStatus.FORBIDDEN),
    
    ADMIN_EXISTED(409, "Admin existed", HttpStatus.BAD_REQUEST),
    ADMIN_NOT_EXISTED(404, "Admin doesn't exist", HttpStatus.NOT_FOUND),
    ADMIN_LOGIN_FAILED(401, "Password incorrect", HttpStatus.BAD_REQUEST),
    
	TOKEN_NOT_FOUND(404, "Not found token", HttpStatus.BAD_REQUEST),
	TOKEN_INVALID(401, "Token invalid", HttpStatus.BAD_REQUEST),
	
	CUSTOMER_EXISTED(409, "Customer existed", HttpStatus.BAD_REQUEST),
	CUSTOMER_NOT_EXISTED(404, "Customer doesn't exist", HttpStatus.NOT_FOUND),
	CUSTOMER_LOGIN_FAILED(401, "Username or password incorrect", HttpStatus.BAD_REQUEST),
	
	CATEGORY_NOT_FOUND(404, "Not found category", HttpStatus.NOT_FOUND),
	CATEGORY_EXISTED(409, "This category existed", HttpStatus.BAD_REQUEST),
	CATEGORY_NOT_EXISTED(404, "This category doesn't exist", HttpStatus.BAD_REQUEST),
	
	PRODUCT_NOT_FOUND(404, "Not found product", HttpStatus.NOT_FOUND),
	PRODUCT_EXISTED(409, "This product existed", HttpStatus.BAD_REQUEST),
	PRODUCT_NOT_EXISTED(409, "This product doesn't exist", HttpStatus.BAD_REQUEST),
	UNIT_INVALID(401, "Unit only [Cái, Kg, Lít]", HttpStatus.BAD_REQUEST),
	
	PROMOTION_NOT_FOUND(404, "Not found promotion", HttpStatus.NOT_FOUND),
	PROMOTION_INVALID(401,"Date create must be greater than date end", HttpStatus.BAD_REQUEST),
	PROMOTION_NOT_EXISTS(409, "This promotion doesn't exist", HttpStatus.BAD_REQUEST),
	
	REVIEW_NOT_FOUND(404, "Not found review", HttpStatus.NOT_FOUND),
	REVIEW_NOT_EXIST(409, "This review doesn't exist", HttpStatus.BAD_REQUEST),
	REVIEW_NOT_ACCESS(403, "You don't have permission to access this review",HttpStatus.FORBIDDEN),
	CANNOT_REVIEW(403, "You have not purchased this product yet",HttpStatus.FORBIDDEN),
	
	CART_NOT_FOUND(404, "Not found cart", HttpStatus.NOT_FOUND),
	CART_NOT_EXIST(409, "This cart doesn't exist", HttpStatus.BAD_REQUEST),
	CART_NOT_ACCESS(403, "You don't have permission to access this cart",HttpStatus.FORBIDDEN),
	PRODUCT_NOT_ENOUGH(400, "The quantity of this product is not enough", HttpStatus.BAD_REQUEST),
	
	INVOICE_NOT_FOUND(404, "Not found invoice", HttpStatus.NOT_FOUND),
	INVOICE_NOT_EXIST(409, "This invoice doesn't exist", HttpStatus.BAD_REQUEST),
	INVOICE_PAID(406,"Can not pay", HttpStatus.NOT_ACCEPTABLE);
	
    private int code;

    private String message;

    private HttpStatusCode statusCode;

    private ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
