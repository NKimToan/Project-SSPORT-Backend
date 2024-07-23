package com.SSPORT.SSPORT.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.SSPORT.SSPORT.entities.Customer;
import com.SSPORT.SSPORT.requests.CustomerRequest;
import com.SSPORT.SSPORT.responses.CustomerResponse;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	Customer toCustomer(CustomerRequest customerRequest);
	
	CustomerResponse toCustomerResponse(Customer customer);
	
	void updateCustomer(@MappingTarget Customer customer, CustomerRequest customerRequest);
}
