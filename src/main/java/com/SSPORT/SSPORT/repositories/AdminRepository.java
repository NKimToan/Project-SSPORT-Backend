package com.SSPORT.SSPORT.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSPORT.SSPORT.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	boolean existsByUsername(String username);
	boolean existsById(int id);
	Optional<Admin> findByUsername(String username);
}
