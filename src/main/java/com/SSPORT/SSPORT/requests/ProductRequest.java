package com.SSPORT.SSPORT.requests;

import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Category;
import com.SSPORT.SSPORT.entities.Unit;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
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
public class ProductRequest {

	@Size(max = 50)
	String productName;

	double cost;

	int quantity;

	MultipartFile image;

	@Enumerated(EnumType.STRING)
	Unit unit;

	Category category;

}
