package com.SSPORT.SSPORT.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSPORT.SSPORT.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	boolean existsByUsername(String username);
	boolean existsById(int id);
	Optional<Customer> findByUsername(String username);

}
