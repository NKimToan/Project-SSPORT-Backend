package com.SSPORT.SSPORT.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
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
public class CategoryResponse {
	
	@Id
	@JsonProperty("category_id")
	int categoryId;
	
	@JsonProperty("category_name")
	String categoryName;
	
	@JsonProperty("image")
	String image;
	
	@JsonProperty("promotion")
	List<PromotionResponse> promotion;
	
}
