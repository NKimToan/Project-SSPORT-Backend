package com.SSPORT.SSPORT.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	int reviewId;

	@Min(0)
	@Max(5)
	int rate;

	String content;

	String image;

	Date date_created;

	Date date_updated;

	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
	Customer customer;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	Product product;

}
