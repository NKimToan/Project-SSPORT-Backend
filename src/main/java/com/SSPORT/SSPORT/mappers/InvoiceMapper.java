package com.SSPORT.SSPORT.mappers;

import org.mapstruct.Mapper;

import com.SSPORT.SSPORT.entities.Invoice;
import com.SSPORT.SSPORT.requests.InvoiceRequest;
import com.SSPORT.SSPORT.responses.InvoiceResponse;

@Mapper(componentModel = "Spring")
public interface InvoiceMapper {
	
	Invoice toInvoice(InvoiceRequest invoiceRequest);
	
	InvoiceResponse toInvoiceResponse(Invoice invoice);
	
}
