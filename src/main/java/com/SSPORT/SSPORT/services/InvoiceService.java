package com.SSPORT.SSPORT.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.SSPORT.SSPORT.entities.Cart;
import com.SSPORT.SSPORT.entities.Invoice;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.mappers.InvoiceMapper;
import com.SSPORT.SSPORT.repositories.CartRepository;
import com.SSPORT.SSPORT.repositories.InvoiceRepository;
import com.SSPORT.SSPORT.requests.InvoiceRequest;
import com.SSPORT.SSPORT.responses.InvoiceResponse;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private InvoiceMapper invoiceMapper;
	
	@Autowired
	private CartRepository cartRepository;

	@PreAuthorize("hasRole('ADMIN')")
	public List<InvoiceResponse> getAllInvoices() {
		return invoiceRepository.findAll().stream().map(invoiceMapper::toInvoiceResponse).toList();
	}

	@PreAuthorize("hasRole('ADMIN')")
	public InvoiceResponse getInvoice(int invoiceId) {
		return invoiceRepository.findById(invoiceId).map(invoiceMapper::toInvoiceResponse)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.INVOICE_NOT_FOUND));
	}
	
	
	public InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {
		
		Invoice invoice = invoiceMapper.toInvoice(invoiceRequest);
		
		List<Cart> cartInvoice = cartRepository.findByCustomerCustomerId(invoiceRequest.getCustomer().getCustomerId());
		
		int finalQuantity = 0;
		double finalPrice = 0.0;
		
		boolean isCartPaid = false;
		
		List<Cart> unpaidCarts = new ArrayList<>();
		
		for (Cart cart: cartInvoice) {
			if (cart.getPay_invoice() == 0) {
				if ((cart.getProduct().getQuantity()) < cart.getQuantity()) {
					throw new HandleRuntimeException(ErrorCode.PRODUCT_NOT_ENOUGH);
				}
				cart.getProduct().setQuantity(cart.getProduct().getQuantity() - cart.getQuantity());
				cart.getProduct().setQuantity_sold(cart.getProduct().getQuantity_sold() + cart.getQuantity());
				cart.setPay_invoice(1);
				finalQuantity += cart.getQuantity();
				finalPrice += cart.getTotal_price();
				isCartPaid = true;
				unpaidCarts.add(cart);
			} 
		}
		invoice.setCart(unpaidCarts);
		invoice.setQuantity(finalQuantity);
		invoice.setFinal_price(finalPrice);
		
		Date currentDate = new Date();
		invoice.setDate_created(currentDate);
		
		if (isCartPaid) {			
			return invoiceMapper.toInvoiceResponse(invoiceRepository.save(invoice));
		} else {
			if (!isCartPaid) {				
				throw new HandleRuntimeException(ErrorCode.INVOICE_PAID);
			}
			return invoiceMapper.toInvoiceResponse(null);
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public InvoiceResponse deleteInvoice(int invoiceId) {
		
		if (!invoiceRepository.existsByInvoiceId(invoiceId)) {
			throw new HandleRuntimeException(ErrorCode.INVOICE_NOT_EXIST);
		}
		
		invoiceRepository.deleteById(invoiceId);
		return invoiceMapper.toInvoiceResponse(null);
	}

}
