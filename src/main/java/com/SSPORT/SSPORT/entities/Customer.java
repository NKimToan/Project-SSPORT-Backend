package com.SSPORT.SSPORT.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	int customerId;

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

	@ManyToOne
	@JoinColumn(name = "admin", referencedColumnName = "admin_id")
	Admin admin;
}
