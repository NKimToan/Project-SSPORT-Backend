package com.SSPORT.SSPORT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSPORT.SSPORT.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	boolean existsByReviewId(int reviewId);
}
