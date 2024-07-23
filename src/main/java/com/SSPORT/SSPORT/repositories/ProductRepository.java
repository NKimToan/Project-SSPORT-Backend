package com.SSPORT.SSPORT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSPORT.SSPORT.entities.Category;
import com.SSPORT.SSPORT.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	boolean existsByProductName(String productName);
	
	boolean existsByProductId(int productId);
	
	List<Product> findByCategory (Category category);
}
