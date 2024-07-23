package com.SSPORT.SSPORT.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Category;
import com.SSPORT.SSPORT.entities.Product;
import com.SSPORT.SSPORT.entities.Promotion;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.mappers.CategoryMapper;
import com.SSPORT.SSPORT.repositories.CategoryRepository;
import com.SSPORT.SSPORT.repositories.ProductRepository;
import com.SSPORT.SSPORT.requests.CategoryCreateRequest;
import com.SSPORT.SSPORT.requests.CategoryUpdateRequest;
import com.SSPORT.SSPORT.responses.CategoryResponse;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductRepository productRepository;

	public List<CategoryResponse> getAllCategory() {
		return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
	}

	public CategoryResponse getCategory(int categoryId) {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		if (optionalCategory.isPresent()) {
			Category category = optionalCategory.get();
			updateProductsInCategory(category);
		}
		return categoryRepository.findById(categoryId).map(categoryMapper::toCategoryResponse)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CATEGORY_NOT_FOUND));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public CategoryResponse createCategory(CategoryCreateRequest categoryRequest) throws IOException {

		if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
			throw new HandleRuntimeException(ErrorCode.CATEGORY_EXISTED);
		}

		Category category = categoryMapper.toCategory(categoryRequest);

		MultipartFile image = categoryRequest.getImage();

		String storageFileName = image.getOriginalFilename();

		try {

			String uploadDir = "public/images/categories/";
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
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		category.setImage(storageFileName);

		return categoryMapper.toCategoryResponse(categoryRepository.save(category));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public CategoryResponse updateCategory(CategoryUpdateRequest categoryRequest, int categoryId) {

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CATEGORY_NOT_FOUND));
		categoryMapper.updateCategory(category, categoryRequest);

		if (!categoryRequest.getImage().isEmpty()) {
			String uploadDir = "public/images/categories/";
			java.nio.file.Path oldImagePath = Paths.get(uploadDir + category.getImage());

			try {

				Files.delete(oldImagePath);

			} catch (Exception ex) {
				System.out.println("Exception: " + ex.getMessage());
			}

			MultipartFile image = categoryRequest.getImage();
			String storageFileName = image.getOriginalFilename();

			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			category.setImage(storageFileName);

			updateProductsInCategory(category);
		}

		return categoryMapper.toCategoryResponse(categoryRepository.save(category));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public CategoryResponse deleteCategory(int categoryId) {

		if (!categoryRepository.existsByCategoryId(categoryId)) {
			throw new HandleRuntimeException(ErrorCode.CATEGORY_NOT_FOUND);
		}

		categoryRepository.deleteById(categoryId);
		return categoryMapper.toCategoryResponse(null);
	}

	public Category findById(int category_id) {
		return categoryRepository.findById(category_id)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CATEGORY_NOT_EXISTED));
	}

	public void updateProductsInCategory(Category category) {
		List<Product> productsList = productRepository.findByCategory(category);

		Date currentDate = new Date();

		for (Product product : productsList) {
			Double maxDiscount = 0.0;
			for (Promotion promotion : category.getPromotion()) {
				if (currentDate.after(promotion.getDate_created()) && currentDate.before(promotion.getDate_end())
						&& promotion.getDiscount() > maxDiscount) {
					maxDiscount = promotion.getDiscount();
				}
			}
			product.setPrice(product.getCost() * (1 - maxDiscount));
			productRepository.save(product);
		}
	}
}
