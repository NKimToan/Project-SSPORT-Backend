package com.SSPORT.SSPORT.responses;

import java.util.Date;

import com.SSPORT.SSPORT.entities.Customer;
import com.SSPORT.SSPORT.entities.Product;
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
public class ReviewResponse {

	@Id
	@JsonProperty("review_id")
	int reviewId;
	
	int rate;
	
	String content;
	
	String image;
	
	Date date_created;
	
	Date date_updated;
	
	Customer customer;
	
	Product product;
	
}
