package com.SSPORT.SSPORT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSPORT.SSPORT.entities.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
	
//	Optional<Promotion> findByPromotionName(String promotionName);
	boolean existsByPromotionId(int promotionId);
}
