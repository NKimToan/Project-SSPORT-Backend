package com.SSPORT.SSPORT.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.SSPORT.SSPORT.entities.Promotion;
import com.SSPORT.SSPORT.requests.PromotionRequest;
import com.SSPORT.SSPORT.responses.PromotionResponse;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
	
	Promotion toPromotion(PromotionRequest promotionRequest);
	
	PromotionResponse toPromotionResponse(Promotion promotion);
	
	void updatePromotion(@MappingTarget Promotion promotion, PromotionRequest promotionRequest);
}
