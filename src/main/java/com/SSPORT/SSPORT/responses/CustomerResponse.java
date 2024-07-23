package com.SSPORT.SSPORT.responses;

import java.time.LocalDate;

import com.SSPORT.SSPORT.entities.Admin;
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
public class CustomerResponse {
	
	@Id
	@JsonProperty("customer_id")
	int customerId;
	
	@JsonProperty("username")
	String username;
	
	@JsonProperty("password")
	String password;
	
	@JsonProperty("firstname")
	String firstname;
	
	@JsonProperty("lastname")
	String lastname;

	@JsonProperty("dob")
	LocalDate dob;
	
	@JsonProperty("address")
	String address;
	
	@JsonProperty("phone_number")
	String phone_number;
	
	@JsonProperty("gender")
	String gender;
	
	@JsonProperty("role")
	String role;
	
	@JsonProperty("admin")
	Admin admin;
}
