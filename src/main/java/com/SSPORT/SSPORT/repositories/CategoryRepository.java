package com.SSPORT.SSPORT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSPORT.SSPORT.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	boolean existsByCategoryName(String categoryName);
	
	boolean existsByCategoryId(int categoryId);
	
}
