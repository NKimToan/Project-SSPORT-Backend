package com.SSPORT.SSPORT.responses;


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
public class CartResponse {
	
	@Id
	@JsonProperty("cart_id")
	int cartId;
	
	@JsonProperty("price")
	double price;
	
	@JsonProperty("quantity")
	int quantity;
	
	@JsonProperty("total_price")
	double total_price;
	
	@JsonProperty("pay_invoice")
	double pay_invoice;
	
	@JsonProperty("customer_id")
	Customer customer;
	
	@JsonProperty("product_id")
	Product product;
}
