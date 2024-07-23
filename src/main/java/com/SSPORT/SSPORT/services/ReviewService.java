package com.SSPORT.SSPORT.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.configuration.ReviewAccess;
import com.SSPORT.SSPORT.entities.Cart;
import com.SSPORT.SSPORT.entities.Review;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.mappers.ReviewMapper;
import com.SSPORT.SSPORT.repositories.CartRepository;
import com.SSPORT.SSPORT.repositories.ReviewRepository;
import com.SSPORT.SSPORT.requests.ReviewRequest;
import com.SSPORT.SSPORT.responses.ReviewResponse;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ReviewMapper reviewMapper;

	@Autowired
	private CartRepository cartRepository;

	public List<ReviewResponse> getAllReview() {
		return reviewRepository.findAll().stream().map(reviewMapper::toReviewResponse).toList();
	}

	public ReviewResponse getReview(int reviewId) {
		return reviewRepository.findById(reviewId).map(reviewMapper::toReviewResponse)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.REVIEW_NOT_FOUND));
	}

	public ReviewResponse createReview(ReviewRequest reviewRequest) throws IOException {

		List<Cart> carts = cartRepository.findByCustomerCustomerId(reviewRequest.getCustomer().getCustomerId());

		boolean hasPaidInvoice = carts.stream().anyMatch(cart -> cart.getPay_invoice() == 1
				&& cart.getProduct().getProductId() == reviewRequest.getProduct().getProductId());

		if (!hasPaidInvoice) {
			throw new HandleRuntimeException(ErrorCode.CANNOT_REVIEW);
		}

		Review review = reviewMapper.toReview(reviewRequest);

		MultipartFile image = reviewRequest.getImage();
		String storageFileName = image.getOriginalFilename();

		String uploadDir = "public/images/reviews/";
		java.nio.file.Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		InputStream inputStream;
		try {
			inputStream = image.getInputStream();
			Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Date currentDate = new Date();
		review.setDate_created(currentDate);
		review.setDate_updated(null);

		return reviewMapper.toReviewResponse(reviewRepository.save(review));
	}

	public ReviewResponse updateReview(ReviewRequest reviewRequest, int reviewId) throws IOException {

		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.REVIEW_NOT_EXIST));

		ReviewResponse reviewResponse = reviewMapper.toReviewResponse(review);
		if (!ReviewAccess.canUpdateReview(reviewResponse)) {
			throw new HandleRuntimeException(ErrorCode.REVIEW_NOT_ACCESS);
		}

		reviewMapper.updateReview(review, reviewRequest);

		MultipartFile image = reviewRequest.getImage();
		String storageFileName = image.getOriginalFilename();

		String uploadDir = "public/images/reviews/";
		java.nio.file.Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		InputStream inputStream;
		try {
			inputStream = image.getInputStream();
			Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Date currentDate = new Date();
		review.setDate_created(review.getDate_created());
		review.setDate_updated(currentDate);

		return reviewMapper.toReviewResponse(reviewRepository.save(review));
	}

	public ReviewResponse deleteReview(int reviewId) {
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.REVIEW_NOT_EXIST));

		ReviewResponse reviewResponse = reviewMapper.toReviewResponse(review);
		if (!ReviewAccess.canDeleteReview(reviewResponse)) {
			throw new HandleRuntimeException(ErrorCode.REVIEW_NOT_ACCESS);
		}

		reviewRepository.deleteById(reviewId);
		return reviewMapper.toReviewResponse(null);
	}
}
