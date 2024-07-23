package com.SSPORT.SSPORT.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.SSPORT.SSPORT.entities.Promotion;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.mappers.PromotionMapper;
import com.SSPORT.SSPORT.repositories.PromotionRepository;
import com.SSPORT.SSPORT.requests.PromotionRequest;
import com.SSPORT.SSPORT.responses.PromotionResponse;

@Service
public class PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;

	@Autowired
	private PromotionMapper promotionMapper;

	public List<PromotionResponse> getAllPromotion() {
		return promotionRepository.findAll().stream().map(promotionMapper::toPromotionResponse).toList();
	}

	@PreAuthorize("hasRole('ADMIN')")
	public PromotionResponse getPromotion(int promotionId) {
		return promotionRepository.findById(promotionId).map(promotionMapper::toPromotionResponse)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.PROMOTION_NOT_FOUND));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public PromotionResponse createPromotion(PromotionRequest promotionRequest) {

		if (promotionRequest.getDate_created().after(promotionRequest.getDate_end())) {
			throw new HandleRuntimeException(ErrorCode.PROMOTION_INVALID);
		}
		Promotion promotion = promotionMapper.toPromotion(promotionRequest);

		return promotionMapper.toPromotionResponse(promotionRepository.save(promotion));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public PromotionResponse updatePromotion(PromotionRequest promotionRequest, int promotionId) {
		
		Promotion promotion = promotionRepository.findById(promotionId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.PROMOTION_NOT_EXISTS));

		if (promotionRequest.getDate_created().after(promotionRequest.getDate_end())) {
			throw new HandleRuntimeException(ErrorCode.PROMOTION_INVALID);
		}
		
		promotionMapper.updatePromotion(promotion, promotionRequest);

		return promotionMapper.toPromotionResponse(promotionRepository.save(promotion));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public PromotionResponse deletePromotion(int promotionId) {

		if (!promotionRepository.existsByPromotionId(promotionId)) {
			throw new HandleRuntimeException(ErrorCode.PRODUCT_NOT_EXISTED);
		}

		promotionRepository.deleteById(promotionId);
		return promotionMapper.toPromotionResponse(null);
	}
}
