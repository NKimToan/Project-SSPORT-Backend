package com.SSPORT.SSPORT.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SSPORT.SSPORT.configuration.CartAccess;
import com.SSPORT.SSPORT.entities.Cart;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.mappers.CartMapper;
import com.SSPORT.SSPORT.repositories.CartRepository;
import com.SSPORT.SSPORT.requests.CartRequest;
import com.SSPORT.SSPORT.responses.CartResponse;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartMapper cartMapper;

	public List<CartResponse> getAllCart() {
		return cartRepository.findAll().stream().map(cartMapper::toCartResponse).toList();
	}

	public CartResponse getCart(int cartId) {

		CartResponse cartResponse = cartRepository.findById(cartId).map(cartMapper::toCartResponse)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CART_NOT_FOUND));

		if (!CartAccess.canAccessCart(cartResponse)) {
			throw new HandleRuntimeException(ErrorCode.CART_NOT_ACCESS);
		}

		return cartResponse;
	}

	public CartResponse createCart(CartRequest cartRequest) {
		Cart cart = cartMapper.toCart(cartRequest);
		if (cartRequest.getQuantity() > cartRequest.getProduct().getQuantity()) {
			throw new HandleRuntimeException(ErrorCode.PRODUCT_NOT_ENOUGH);
		}
		cart.setPrice(cartRequest.getProduct().getPrice());
		cart.setTotal_price(cartRequest.getQuantity() * cart.getPrice());

		return cartMapper.toCartResponse(cartRepository.save(cart));
	}

	public CartResponse updateCart(CartRequest cartRequest, int cartId) {

		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CART_NOT_EXIST));
		if (cartRequest.getQuantity() > cartRequest.getProduct().getQuantity()) {
			throw new HandleRuntimeException(ErrorCode.PRODUCT_NOT_ENOUGH);
		}

		cart.setPrice(cartRequest.getProduct().getPrice());
		cart.setTotal_price(cartRequest.getQuantity() * cart.getPrice());

		CartResponse cartResponse = cartMapper.toCartResponse(cart);
		if (!CartAccess.canAccessCart(cartResponse)) {
			throw new HandleRuntimeException(ErrorCode.CART_NOT_ACCESS);
		}

		cartMapper.updateCart(cart, cartRequest);

		return cartMapper.toCartResponse(cartRepository.save(cart));
	}

	public CartResponse deleteCart(int cartId) {
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CART_NOT_EXIST));

		CartResponse cartResponse = cartMapper.toCartResponse(cart);
		if (!CartAccess.canAccessCart(cartResponse)) {
			throw new HandleRuntimeException(ErrorCode.CART_NOT_ACCESS);
		}

		cartRepository.deleteById(cartId);
		return cartMapper.toCartResponse(null);
	}
	
	public List<CartResponse> getCartByUserId(int customerId) {
		return cartRepository.findByCustomerCustomerId(customerId).stream().map(cartMapper::toCartResponse).toList();
	}

}
