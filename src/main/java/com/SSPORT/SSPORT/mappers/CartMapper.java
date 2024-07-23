package com.SSPORT.SSPORT.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.SSPORT.SSPORT.entities.Cart;
import com.SSPORT.SSPORT.requests.CartRequest;
import com.SSPORT.SSPORT.responses.CartResponse;

@Mapper(componentModel = "Spring")
public interface CartMapper {
	
	Cart toCart(CartRequest cartRequest);
	
	CartResponse toCartResponse(Cart cart);
	
	void updateCart(@MappingTarget Cart cart, CartRequest cartRequest);
	
}
