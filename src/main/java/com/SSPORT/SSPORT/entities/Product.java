package com.SSPORT.SSPORT.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	int productId;

	@Size(max = 50)
	@Column(name = "product_name")
	String productName;

	@Enumerated(EnumType.STRING)
	Unit unit;

	double cost;
	
	double price;

	int quantity;
	
	@Builder.Default()
	int quantity_sold = 0;

	String image;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	Category category;
}
