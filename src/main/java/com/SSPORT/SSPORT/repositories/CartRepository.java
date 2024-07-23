package com.SSPORT.SSPORT.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSPORT.SSPORT.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	
	boolean existsByCartId(int cartId);
	
	List<Cart> findByCustomerCustomerId(int customerId);
	
}
