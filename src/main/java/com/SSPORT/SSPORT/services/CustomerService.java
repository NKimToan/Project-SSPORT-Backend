package com.SSPORT.SSPORT.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SSPORT.SSPORT.entities.Customer;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.mappers.CustomerMapper;
import com.SSPORT.SSPORT.repositories.CustomerRepository;
import com.SSPORT.SSPORT.requests.CustomerRequest;
import com.SSPORT.SSPORT.responses.CustomerResponse;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PreAuthorize("hasRole('ADMIN')")
	public List<CustomerResponse> getAllCustomers() {
		return customerRepository.findAll().stream().map(customerMapper::toCustomerResponse).toList();
	}

	@PostAuthorize("returnObject.username == authentication.name || hasRole('ADMIN')")
	public CustomerResponse getCustomer(int customer_id) {
		return customerRepository.findById(customer_id).map(customerMapper::toCustomerResponse)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CUSTOMER_NOT_EXISTED));
	}

	public CustomerResponse createCustomer(CustomerRequest customerRequest) {

		if (customerRepository.existsByUsername(customerRequest.getUsername())) {
			throw new HandleRuntimeException(ErrorCode.CUSTOMER_EXISTED);
		}

		Customer customer = customerMapper.toCustomer(customerRequest);

		customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));

		return customerMapper.toCustomerResponse(customerRepository.save(customer));
	}

	@PostAuthorize("returnObject.username == authentication.name || hasRole('ADMIN')")
	public CustomerResponse updateCustomer(CustomerRequest customerRequest, int customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CUSTOMER_NOT_EXISTED));
		customerMapper.updateCustomer(customer, customerRequest);

		customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));

		return customerMapper.toCustomerResponse(customerRepository.save(customer));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public CustomerResponse deleteCustomer(int customerId) {
		if (!customerRepository.existsById(customerId)) {
			throw new HandleRuntimeException(ErrorCode.CUSTOMER_NOT_EXISTED);
		}
		customerRepository.deleteById(customerId);
		return customerMapper.toCustomerResponse(null);
	}

	public CustomerResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();

		String name = context.getAuthentication().getName();

		Customer customer = customerRepository.findByUsername(name).orElseThrow(() -> new RuntimeException());

		return customerMapper.toCustomerResponse(customer);
	}

	public Customer findById(int customer_id) {
		return customerRepository.findById(customer_id)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.CUSTOMER_NOT_EXISTED));
	}
}
