package com.SSPORT.SSPORT.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Promotion;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.requests.CategoryCreateRequest;
import com.SSPORT.SSPORT.requests.CategoryUpdateRequest;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.SSPORT.SSPORT.responses.CategoryResponse;
import com.SSPORT.SSPORT.services.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	

	@GetMapping("")
	ApiResponse<List<CategoryResponse>> getAllCategory() {
		return ApiResponse.<List<CategoryResponse>>builder().code(200).message("List all category")
				.result(categoryService.getAllCategory()).build();
	}

	@GetMapping("/{categoryId}")
	ApiResponse<CategoryResponse> getAllCategory(@PathVariable int categoryId) {
		return ApiResponse.<CategoryResponse>builder().code(200).message("Category information")
				.result(categoryService.getCategory(categoryId)).build();
	}

	@PostMapping("")
	ApiResponse<CategoryResponse> createCategory(@Valid @ModelAttribute @RequestBody CategoryCreateRequest request,
			@RequestParam("image") MultipartFile imageFile) throws IOException {

		CategoryCreateRequest categoryRequest = new CategoryCreateRequest();

		categoryRequest.setCategoryName(request.getCategoryName());
		categoryRequest.setImage(imageFile);

		return ApiResponse.<CategoryResponse>builder().code(201).message("Category created")
				.result(categoryService.createCategory(categoryRequest)).build();
	}
	
	@PutMapping("/{categoryId}")
	ApiResponse<CategoryResponse> updateCategory(@Valid @ModelAttribute CategoryUpdateRequest categoryRequest,
													@RequestParam("image") MultipartFile imageFile,
													@RequestParam("promotion") List<Promotion> promotion,
													@PathVariable int categoryId) {
		
		if (promotion.get(0) == null) {
			throw new HandleRuntimeException(ErrorCode.PROMOTION_NOT_EXISTS);
		}
		
		CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
		categoryUpdateRequest.setCategoryName(categoryRequest.getCategoryName());
		categoryUpdateRequest.setImage(imageFile);
		categoryUpdateRequest.setPromotion(promotion);
		return ApiResponse.<CategoryResponse>builder().code(200).message("Category updated")
				.result(categoryService.updateCategory(categoryUpdateRequest, categoryId)).build();
	}
	

	@DeleteMapping("/{categoryId}")
	ApiResponse<CategoryResponse> deleteCategory(@PathVariable int categoryId) {
		return ApiResponse.<CategoryResponse>builder().code(200).message("Category deleted")
				.result(categoryService.deleteCategory(categoryId)).build();
	}

}
