package com.SSPORT.SSPORT.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SSPORT.SSPORT.requests.PromotionRequest;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.SSPORT.SSPORT.responses.PromotionResponse;
import com.SSPORT.SSPORT.services.PromotionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/promotion")
@RequiredArgsConstructor
public class PromotionController {
	
	@Autowired
	private PromotionService promotionService;
	
	@GetMapping("")
	ApiResponse<List<PromotionResponse>> getAllPromotion() {
		return ApiResponse.<List<PromotionResponse>>builder().code(200).message("List all promotion")
				.result(promotionService.getAllPromotion()).build();
	}
	
	
	@GetMapping("/{promotionId}")
	ApiResponse<PromotionResponse> getPromotion(@PathVariable int promotionId) {
		return ApiResponse.<PromotionResponse>builder().code(200).message("Promotion information")
				.result(promotionService.getPromotion(promotionId)).build();
	}
	
	@PostMapping("")
	ApiResponse<PromotionResponse> createPromotion(@Valid @RequestBody PromotionRequest promotionRequest) {
		return ApiResponse.<PromotionResponse>builder()
				.code(201)
				.message("Prmotion created")
				.result(promotionService.createPromotion(promotionRequest))
				.build();
	}
	
	@PutMapping("/{promotionId}")
	ApiResponse<PromotionResponse> updatePromotion(@RequestBody PromotionRequest promotionRequest,
													@PathVariable int promotionId) {
		return ApiResponse.<PromotionResponse>builder()
				.code(200)
				.message("Prmotion updated")
				.result(promotionService.updatePromotion(promotionRequest, promotionId))
				.build();
	}
	
	
	@DeleteMapping("/{promotionId}")
	ApiResponse<PromotionResponse> deletePromotion(@PathVariable int promotionId) {
		return ApiResponse.<PromotionResponse>builder().code(200).message("Promotion deleted")
				.result(promotionService.deletePromotion(promotionId)).build();
	}

}
