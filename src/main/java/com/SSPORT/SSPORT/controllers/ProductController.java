package com.SSPORT.SSPORT.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Category;
import com.SSPORT.SSPORT.entities.Unit;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.requests.ProductRequest;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.SSPORT.SSPORT.responses.ProductResponse;
import com.SSPORT.SSPORT.services.CategoryService;
import com.SSPORT.SSPORT.services.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	ApiResponse<List<ProductResponse>> getAllProduct() {
		return ApiResponse.<List<ProductResponse>>builder().code(200).message("List all product")
				.result(productService.getAllProduct()).build();
	}

	@GetMapping("/{productId}")
	ApiResponse<ProductResponse> getProduct(@PathVariable int productId) {
		return ApiResponse.<ProductResponse>builder().code(200).message("Product information")
				.result(productService.getProduct(productId)).build();
	}

	@PostMapping("")
	ApiResponse<ProductResponse> createProduct(@Valid @ModelAttribute @RequestParam("image") MultipartFile imageFile,
			@RequestParam("unit") Unit unit, @RequestParam("productName") String productName,
			@RequestParam("quantity") String quantity, @RequestParam("cost") String cost,
			@RequestParam("category_id") String category_id) throws Exception {

		ProductRequest request = new ProductRequest();
		request.setProductName(productName);
		request.setUnit(unit);
		request.setQuantity(Integer.parseInt(quantity));
		request.setCost(Double.parseDouble(cost));
		request.setImage(imageFile);

		Category category = categoryService.findById(Integer.parseInt(category_id));

		if (category == null) {
			throw new HandleRuntimeException(ErrorCode.CATEGORY_NOT_EXISTED);
		}

		request.setCategory(category);

		return ApiResponse.<ProductResponse>builder().code(201).message("Product created")
				.result(productService.createProduct(request)).build();
	}
	
	@PutMapping("/{productId}")
	ApiResponse<ProductResponse> updateProduct(@Valid @ModelAttribute @RequestParam("image") MultipartFile imageFile,
																	  @RequestParam("unit") Unit unit,
																	  @RequestParam("productName") String productName,
																	  @RequestParam("quantity") String quantity,
																	  @RequestParam("cost") String cost,
																	  @RequestParam("category_id") String category_id, 
																	  @PathVariable int productId) throws Exception {
		
		ProductRequest request = new ProductRequest();
		request.setProductName(productName);
		request.setUnit(unit);
		request.setQuantity(Integer.parseInt(quantity));
		request.setCost(Double.parseDouble(cost));
		request.setImage(imageFile);
		
		Category category = categoryService.findById(Integer.parseInt(category_id));
		
		if (category==null) {
			throw new HandleRuntimeException(ErrorCode.CATEGORY_NOT_EXISTED);
		}
		request.setCategory(category);
		
		return ApiResponse.<ProductResponse>builder()
				.code(200)
				.message("Product updated")
				.result(productService.updateProduct(request, productId))
				.build();
	}
	
	@DeleteMapping("/{productId}")
	ApiResponse<ProductResponse> deleteProduct(@PathVariable int productId) {
		return ApiResponse.<ProductResponse>builder()
				.code(200)
				.message("Product deleted")
				.result(productService.deleteProduct(productId))
				.build();
	}
}
