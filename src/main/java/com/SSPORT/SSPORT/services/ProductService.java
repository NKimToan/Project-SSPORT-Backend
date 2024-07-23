package com.SSPORT.SSPORT.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Product;
import com.SSPORT.SSPORT.entities.Promotion;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.mappers.ProductMapper;
import com.SSPORT.SSPORT.repositories.ProductRepository;
import com.SSPORT.SSPORT.requests.ProductRequest;
import com.SSPORT.SSPORT.responses.ProductResponse;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;

	public List<ProductResponse> getAllProduct() {
		return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
	}

	public ProductResponse getProduct(int productId) {
		return productRepository.findById(productId).map(productMapper::toProductResponse)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.PRODUCT_NOT_FOUND));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public ProductResponse createProduct(ProductRequest productRequest) throws IOException {

		if (productRepository.existsByProductName(productRequest.getProductName())) {
			throw new HandleRuntimeException(ErrorCode.PRODUCT_EXISTED);
		}

		Product product = productMapper.toProduct(productRequest);

		MultipartFile image = productRequest.getImage();
		String storageFileName = image.getOriginalFilename();

		String uploadDir = "public/images/products/";
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

		if (product.getCategory() == null || product.getCategory().getPromotion() == null
				|| product.getCategory().getPromotion().isEmpty()) {
			product.setPrice(product.getCost());

		} else {
			Double maxDiscount = 0.0;
			Date currentDate = new Date();
			for (Promotion promotion : product.getCategory().getPromotion()) {
				if (currentDate.after(promotion.getDate_created()) && currentDate.before(promotion.getDate_end())
						&& promotion.getDiscount() > maxDiscount) {
					maxDiscount = promotion.getDiscount();
				}
			}
			product.setPrice(product.getCost() * (1 - maxDiscount));
		}

		return productMapper.toProductResponse(productRepository.save(product));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public ProductResponse updateProduct(ProductRequest productRequest, int productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.PRODUCT_NOT_EXISTED));

		productMapper.updateProduct(product, productRequest);

		if (!productRequest.getImage().isEmpty()) {
			String uploadDir = "public/images/products/";
			java.nio.file.Path oldImagePath = Paths.get(uploadDir + product.getImage());

			try {

				Files.delete(oldImagePath);

			} catch (Exception ex) {
				System.out.println("Exception: " + ex.getMessage());
			}

			MultipartFile image = productRequest.getImage();
			String storageFileName = image.getOriginalFilename();

			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			product.setImage(storageFileName);
		}

		if (product.getCategory() == null || product.getCategory().getPromotion() == null
				|| product.getCategory().getPromotion().isEmpty()) {
			product.setPrice(product.getCost());

		} else {
			Double maxDiscount = 0.0;
			Date currentDate = new Date();
			for (Promotion promotion : product.getCategory().getPromotion()) {
				if (currentDate.after(promotion.getDate_created()) && currentDate.before(promotion.getDate_end())
						&& promotion.getDiscount() > maxDiscount) {
					maxDiscount = promotion.getDiscount();
				}
			}
			product.setPrice(product.getCost() * (1 - maxDiscount));
		}

		return productMapper.toProductResponse(productRepository.save(product));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public ProductResponse deleteProduct(int productId) {

		if (!productRepository.existsByProductId(productId)) {
			throw new HandleRuntimeException(ErrorCode.PRODUCT_NOT_EXISTED);
		}

		productRepository.deleteById(productId);
		return productMapper.toProductResponse(null);
	}

	public Product findById(int product_id) {
		return productRepository.findById(product_id)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.PRODUCT_NOT_EXISTED));
	}
}
