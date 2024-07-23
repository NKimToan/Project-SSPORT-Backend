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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Customer;
import com.SSPORT.SSPORT.entities.Product;
import com.SSPORT.SSPORT.requests.ReviewRequest;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.SSPORT.SSPORT.responses.ReviewResponse;
import com.SSPORT.SSPORT.services.CustomerService;
import com.SSPORT.SSPORT.services.ProductService;
import com.SSPORT.SSPORT.services.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping("")
	ApiResponse<List<ReviewResponse>> getAllReview() {
		return ApiResponse.<List<ReviewResponse>>builder()
				.code(200)
				.message("List all review")
				.result(reviewService.getAllReview())
				.build();
	}
	
	
	@GetMapping("/{reviewId}")
	ApiResponse<ReviewResponse> getReview(@PathVariable int reviewId) {
		return ApiResponse.<ReviewResponse>builder()
				.code(200)
				.message("Review information")
				.result(reviewService.getReview(reviewId))
				.build();
	}
	
	
	@PostMapping("")
	ApiResponse<ReviewResponse> createReview(@Valid @ModelAttribute @RequestParam("rate") String rate,
																	@RequestParam("image") MultipartFile image,
																	@RequestParam("content") String content,
																	@RequestParam("customer_id") String customer_id,
																	@RequestParam("product_id") String product_id) throws IOException {
		
		
		ReviewRequest reviewRequest = new ReviewRequest();
		
		reviewRequest.setRate(Integer.parseInt(rate));
		reviewRequest.setImage(image);
		reviewRequest.setContent(content);
		
		Product product = productService.findById(Integer.parseInt(product_id));
		
		reviewRequest.setProduct(product);
		
		Customer customer = customerService.findById(Integer.parseInt(customer_id));
		
		reviewRequest.setCustomer(customer);
		
		return ApiResponse.<ReviewResponse>builder()
				.code(201)
				.message("Review created")
				.result(reviewService.createReview(reviewRequest))
				.build();
		
	}
	
	
	@PutMapping("/{reviewId}")
	ApiResponse<ReviewResponse> updateReview(@Valid @ModelAttribute @RequestParam("rate") String rate,
																	@RequestParam("image") MultipartFile image,
																	@RequestParam("content") String content,
																	@RequestParam("customer_id") String customer_id,
																	@RequestParam("product_id") String product_id,
																	@PathVariable int reviewId) throws IOException {
		

		ReviewRequest reviewRequest = new ReviewRequest();
		
		reviewRequest.setRate(Integer.parseInt(rate));
		reviewRequest.setImage(image);
		reviewRequest.setContent(content);
		
		Product product = productService.findById(Integer.parseInt(product_id));
		
		reviewRequest.setProduct(product);
		
		Customer customer = customerService.findById(Integer.parseInt(customer_id));
		
		reviewRequest.setCustomer(customer);
		
		return ApiResponse.<ReviewResponse>builder()
				.code(200)
				.message("Review updated")
				.result(reviewService.updateReview(reviewRequest, reviewId))
				.build();
	}
	
	
	@DeleteMapping("/{reviewId}")
	ApiResponse<ReviewResponse> deleteReview(@PathVariable int reviewId) {
		return ApiResponse.<ReviewResponse>builder()
				.code(200)
				.message("Review deteted")
				.result(reviewService.deleteReview(reviewId))
				.build();
	}

}
