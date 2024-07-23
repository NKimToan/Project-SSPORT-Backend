package com.SSPORT.SSPORT.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Category;
import com.SSPORT.SSPORT.requests.CategoryCreateRequest;
import com.SSPORT.SSPORT.requests.CategoryUpdateRequest;
import com.SSPORT.SSPORT.responses.CategoryResponse;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
	
	@Mapping(source = "image", target = "image", qualifiedByName = "multipartFileToString")
	Category toCategory(CategoryCreateRequest categoryRequest);
	
	
	CategoryResponse toCategoryResponse(Category category);
	
	@Mapping(source = "image", target = "image", qualifiedByName = "multipartFileToString")
	void updateCategory(@MappingTarget Category category, CategoryUpdateRequest categoryRequest);
	
	
	@Named("multipartFileToString")
	default String multipartFileToString(MultipartFile file) {
		System.out.println("multipartFile => string");
		return file != null ? file.getOriginalFilename() : null;
	}

}
