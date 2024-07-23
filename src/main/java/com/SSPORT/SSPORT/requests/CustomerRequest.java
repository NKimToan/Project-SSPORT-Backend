package com.SSPORT.SSPORT.requests;

import java.time.LocalDate;

import com.SSPORT.SSPORT.entities.Admin;

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
public class CustomerRequest {

	@Size(max = 50)
	String username;
	
	String password;
	
	@Size(max = 20)
	String firstname;
	
	@Size(max = 30)
	String lastname;

	LocalDate dob;
	
	@Size(max = 100)
	String address;
	
	@Size(max = 10)
	String phone_number;
	
	@Size(max = 3)
	String gender;
	
	@Builder.Default
	String role = "CUSTOMER";
	
	Admin admin;
}
