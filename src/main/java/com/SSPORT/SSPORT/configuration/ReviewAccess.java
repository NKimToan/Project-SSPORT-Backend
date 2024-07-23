package com.SSPORT.SSPORT.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SSPORT.SSPORT.responses.ReviewResponse;

@Service
public class ReviewAccess {
//	Chỉ những khách hàng tạo đánh giá thì mới có quyền sửa(kể cả admin cũng không được sửa)
	public static boolean canUpdateReview(ReviewResponse reviewResponse) {
		
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		if (reviewResponse.getCustomer().getUsername().equals(name)) {
			return true;
		}
		return false;
	}

//	Chỉ có khách hàng đánh giá và admin mới có quyền xóa
	public static boolean canDeleteReview(ReviewResponse reviewResponse) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		if (reviewResponse.getCustomer().getUsername().equals(name) || isAdmin(authentication)) {
			return true;
		}
		return false;
	}

	private static boolean isAdmin(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
	}
}
