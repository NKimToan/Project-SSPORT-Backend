package com.SSPORT.SSPORT.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import com.SSPORT.SSPORT.entities.Product;
import com.SSPORT.SSPORT.requests.ProductRequest;
import com.SSPORT.SSPORT.responses.ProductResponse;


@Mapper(componentModel = "spring")
public interface ProductMapper {
	
	@Mapping(source = "image", target = "image", qualifiedByName = "multipartFileToString")
	Product toProduct(ProductRequest productRequest);
	
	ProductResponse toProductResponse(Product product);
	
	@Mapping(source = "image", target = "image", qualifiedByName = "multipartFileToString")
	void updateProduct(@MappingTarget Product products, ProductRequest productRequest);
	
	
	@Named("multipartFileToString")
	default String multipartFileToString(MultipartFile file) {
		return file != null ? file.getOriginalFilename() : null;
	}
}
