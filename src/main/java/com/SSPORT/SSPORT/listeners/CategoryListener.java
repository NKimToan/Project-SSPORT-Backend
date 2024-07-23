package com.SSPORT.SSPORT.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SSPORT.SSPORT.entities.Category;
import com.SSPORT.SSPORT.services.CategoryService;

import jakarta.persistence.PostUpdate;

@Component
public class CategoryListener {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostUpdate
	public void onPostUpdate(Category category) {
		categoryService.updateProductsInCategory(category);
	}
}
