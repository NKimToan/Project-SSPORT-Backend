package com.SSPORT.SSPORT.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SSPORT.SSPORT.entities.Customer;
import com.SSPORT.SSPORT.requests.InvoiceRequest;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.SSPORT.SSPORT.responses.InvoiceResponse;
import com.SSPORT.SSPORT.services.CustomerService;
import com.SSPORT.SSPORT.services.InvoiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping("")
	ApiResponse<List<InvoiceResponse>> getAllInvoices() {
		return ApiResponse.<List<InvoiceResponse>>builder()
				.code(200)
				.message("List all invoices")
				.result(invoiceService.getAllInvoices())				
				.build();
	}
	
	@GetMapping("/{invoiceId}")
	ApiResponse<InvoiceResponse> getInvoice(@PathVariable int invoiceId) {
		return ApiResponse.<InvoiceResponse>builder()
				.code(200)
				.message("Invoice information")
				.result(invoiceService.getInvoice(invoiceId))				
				.build();
	}
	
	
	@PostMapping("")
	ApiResponse<InvoiceResponse> createInvoice(@Valid @ModelAttribute @RequestParam("customer_id") String customer_id) {
		
		InvoiceRequest invoiceRequest = new InvoiceRequest();
		
		Customer customer = customerService.findById(Integer.parseInt(customer_id));
		
		invoiceRequest.setCustomer(customer);;
		
		return ApiResponse.<InvoiceResponse>builder()
				.code(201)
				.message("Invoice Created")
				.result(invoiceService.createInvoice(invoiceRequest))
				.build();
		
	}
	
	@DeleteMapping("/{invoiceId}")
	ApiResponse<InvoiceResponse> deleteInvoice(@PathVariable int invoiceId) {
		return ApiResponse.<InvoiceResponse>builder()
				.code(200)
				.message("Invoice Deleted")
				.result(invoiceService.deleteInvoice(invoiceId))
				.build();
	}
	

}
