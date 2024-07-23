package com.SSPORT.SSPORT.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SSPORT.SSPORT.responses.CartResponse;

@Service
public class CartAccess {

	public static boolean canAccessCart(CartResponse cartResponse) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		
		if (cartResponse.getCustomer().getUsername().equals(name) || isAdmin(authentication)) {
			return true;
		}
		return false;
	}

	private static boolean isAdmin(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
	}
}
