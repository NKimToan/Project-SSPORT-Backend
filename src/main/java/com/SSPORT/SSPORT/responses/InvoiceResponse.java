package com.SSPORT.SSPORT.responses;

import java.util.Date;
import java.util.List;

import com.SSPORT.SSPORT.entities.Cart;
import com.SSPORT.SSPORT.entities.Customer;
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
public class InvoiceResponse {

	@Id
	@JsonProperty("invoice_id")
	int invoiceId;
	
	@JsonProperty("date_created")
	Date date_created;
	
	@JsonProperty("quantity")
	int quantity;
	
	@JsonProperty("final_price")
	double final_price;
	
	@JsonProperty("customer_id")
	Customer customer;
	
	@JsonProperty("cart_id")
	List<Cart> cart;
}
