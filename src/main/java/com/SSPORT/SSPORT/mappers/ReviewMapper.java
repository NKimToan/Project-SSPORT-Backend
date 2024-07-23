package com.SSPORT.SSPORT.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Review;
import com.SSPORT.SSPORT.requests.ReviewRequest;
import com.SSPORT.SSPORT.responses.ReviewResponse;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

	@Mapping(source = "image", target = "image", qualifiedByName = "multipartFileToString")
	Review toReview(ReviewRequest reviewRequest);
	

	ReviewResponse toReviewResponse(Review review);
	
	@Mapping(source = "image", target = "image", qualifiedByName = "multipartFileToString")
	void updateReview(@MappingTarget Review review, ReviewRequest reviewRequest);
	
	
	@Named("multipartFileToString")
	default String multipartFileToString(MultipartFile file) {
		return file != null ? file.getOriginalFilename() : null;
	}
}
