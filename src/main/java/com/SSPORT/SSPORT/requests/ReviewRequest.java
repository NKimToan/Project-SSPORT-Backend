package com.SSPORT.SSPORT.requests;

import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Customer;
import com.SSPORT.SSPORT.entities.Product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {

	@Min(0)
	@Max(5)
	int rate;
	
	String content;
	
	MultipartFile image;
	
	Customer customer;
	
	Product product;
}
