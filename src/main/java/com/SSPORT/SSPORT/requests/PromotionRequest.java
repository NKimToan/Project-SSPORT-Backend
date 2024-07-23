package com.SSPORT.SSPORT.requests;

import java.util.Date;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionRequest {
	
	@Size(max = 50)
	String promotionName;
	
	Date date_created;
	
	Date date_end;
	
	Double discount;
	
}
