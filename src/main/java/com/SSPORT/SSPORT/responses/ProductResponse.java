package com.SSPORT.SSPORT.responses;


import com.SSPORT.SSPORT.entities.Category;
import com.SSPORT.SSPORT.entities.Unit;
import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ProductResponse {
	
	@Id
	@JsonProperty("product_id")
	int productId;
	
	@JsonProperty("product_name")
	String productName;
	
	@Enumerated(EnumType.STRING)
	@JsonProperty("unit")
	Unit unit;
	
	@JsonProperty("cost")
	double cost;
	
	@JsonProperty("price")
	double price;
	
	@JsonProperty("quantity")
	int quantity;
	
	@JsonProperty("quantity_sold")
	int quantity_sold;
	
	@JsonProperty("image")
	String image;
	
	@JsonProperty("category_id")
	Category category;
}
