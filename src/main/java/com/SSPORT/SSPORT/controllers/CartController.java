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

import com.SSPORT.SSPORT.entities.Customer;
import com.SSPORT.SSPORT.entities.Product;
import com.SSPORT.SSPORT.requests.CartRequest;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.SSPORT.SSPORT.responses.CartResponse;
import com.SSPORT.SSPORT.services.CartService;
import com.SSPORT.SSPORT.services.CustomerService;
import com.SSPORT.SSPORT.services.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	ApiResponse<List<CartResponse>> getAllCart() {
		return ApiResponse.<List<CartResponse>>builder().code(200).message("List all Cart")
				.result(cartService.getAllCart()).build();
	}

	@GetMapping("/{cartId}")
	ApiResponse<CartResponse> getCart(@PathVariable int cartId) {
		return ApiResponse.<CartResponse>builder().code(200).message("Cart information")
				.result(cartService.getCart(cartId)).build();
	}
	
	
	@PostMapping("")
	ApiResponse<CartResponse> createCart(@Valid @ModelAttribute @RequestParam("quantity") String quantity,
			@RequestParam("customer_id") String customer_id, @RequestParam("product_id") String product_id) {

		CartRequest cartRequest = new CartRequest();

		cartRequest.setQuantity(Integer.parseInt(quantity));

		Customer customer = customerService.findById(Integer.parseInt(customer_id));

		cartRequest.setCustomer(customer);

		Product product = productService.findById(Integer.parseInt(product_id));

		cartRequest.setProduct(product);

		return ApiResponse.<CartResponse>builder().code(201).message("Cart created")
				.result(cartService.createCart(cartRequest)).build();
	}
	
	
	@PutMapping("/{cartId}")
	ApiResponse<CartResponse> updateCart(@Valid @ModelAttribute @RequestParam("quantity") String quantity,
			@RequestParam("customer_id") String customer_id, @RequestParam("product_id") String product_id,
			@PathVariable int cartId) {

		CartRequest cartRequest = new CartRequest();

		cartRequest.setQuantity(Integer.parseInt(quantity));

		Customer customer = customerService.findById(Integer.parseInt(customer_id));

		cartRequest.setCustomer(customer);

		Product product = productService.findById(Integer.parseInt(product_id));

		cartRequest.setProduct(product);

		return ApiResponse.<CartResponse>builder().code(200).message("Cart updated")
				.result(cartService.updateCart(cartRequest, cartId)).build();
	}
	
	
	@DeleteMapping("/{cartId}")
	ApiResponse<CartResponse> deleteResponse(@PathVariable int cartId) {
		return ApiResponse.<CartResponse>builder()
				.code(200)
				.message("Cart deleted")
				.result(cartService.deleteCart(cartId))
				.build();
	}
	
	
}
