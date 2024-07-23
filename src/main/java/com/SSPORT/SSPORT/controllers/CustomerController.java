package com.SSPORT.SSPORT.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SSPORT.SSPORT.entities.Admin;
import com.SSPORT.SSPORT.requests.CustomerRequest;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.SSPORT.SSPORT.responses.CustomerResponse;
import com.SSPORT.SSPORT.services.AdminService;
import com.SSPORT.SSPORT.services.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AdminService adminService;

	@GetMapping("")
	ApiResponse<List<CustomerResponse>> getAllCustomer() {
		return ApiResponse.<List<CustomerResponse>>builder().code(200).message("List all customers")
				.result(customerService.getAllCustomers()).build();
	}

	@GetMapping("/{customerId}")
	ApiResponse<CustomerResponse> getcustomer(@Valid @PathVariable int customerId) {
		return ApiResponse.<CustomerResponse>builder().code(200).message("Admin information")
				.result(customerService.getCustomer(customerId)).build();
	}

	@PostMapping("")
	ApiResponse<CustomerResponse> createCustomer(@Valid @ModelAttribute @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname, @RequestParam("dob") String dob,
			@RequestParam("address") String address, @RequestParam("gender") String gender,
			@RequestParam("admin_id") String admin_id, @RequestParam("phone_number") String phone_number)
			throws Exception {
		CustomerRequest customerRequest = new CustomerRequest();

		Admin admin = adminService.findById(Integer.parseInt(admin_id));
		if (admin == null) {
			throw new Exception("This admin not found");
		}

		customerRequest.setAddress(address);
		customerRequest.setDob(LocalDate.parse(dob));
		customerRequest.setAdmin(admin);
		customerRequest.setFirstname(firstname);
		customerRequest.setLastname(lastname);
		customerRequest.setGender(gender);
		customerRequest.setUsername(username);
		customerRequest.setPassword(password);
		customerRequest.setPhone_number(phone_number);

		return ApiResponse.<CustomerResponse>builder().code(201).message("Create customer successfull")
				.result(customerService.createCustomer(customerRequest)).build();
	}

	@PutMapping("/{customerId}")
	ApiResponse<CustomerResponse> updateCustomer(@Valid @ModelAttribute @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname, @RequestParam("dob") String dob,
			@RequestParam("address") String address, @RequestParam("gender") String gender,
			@RequestParam("admin_id") String admin_id, @RequestParam("phone_number") String phone_number,
			@PathVariable int customerId) throws Exception {

		CustomerRequest customerRequest = new CustomerRequest();

		Admin admin = adminService.findById(Integer.parseInt(admin_id));
		if (admin == null) {
			throw new Exception("This admin not found");
		}

		customerRequest.setAddress(address);
		customerRequest.setDob(LocalDate.parse(dob));
		customerRequest.setAdmin(admin);
		customerRequest.setFirstname(firstname);
		customerRequest.setLastname(lastname);
		customerRequest.setGender(gender);
		customerRequest.setUsername(username);
		customerRequest.setPassword(password);
		customerRequest.setPhone_number(phone_number);

		return ApiResponse.<CustomerResponse>builder().code(200).message("Update customer successfull")
				.result(customerService.updateCustomer(customerRequest, customerId)).build();
	}

	@DeleteMapping("/{customerId}")
	ApiResponse<CustomerResponse> deleteCustomer(@Valid @PathVariable int customerId) {
		return ApiResponse.<CustomerResponse>builder().code(200).message("Delete customer successfull")
				.result(customerService.deleteCustomer(customerId)).build();
	}

	@GetMapping("/my-infor")
	ApiResponse<CustomerResponse> getMyInfo() {
		return ApiResponse.<CustomerResponse>builder().code(200).message("My infor").result(customerService.getMyInfo())
				.build();
	}

}
