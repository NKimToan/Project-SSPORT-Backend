package com.SSPORT.SSPORT.requests;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Promotion;

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
public class CategoryUpdateRequest {
	
	@Size(max = 50)
	String categoryName;
	
	MultipartFile image;
	
	List<Promotion> promotion;
}
