package com.SSPORT.SSPORT.responses;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
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
public class PromotionResponse {

	@Id
	@JsonProperty("promotion_id")
	int promotionId;
	
	@JsonProperty("promotion_name")
	String promotionName;
	
	@JsonProperty("date_created")
	Date date_created;
	
	@JsonProperty("date_end")
	Date date_end;
	
	@JsonProperty("discount")
	Double discount;
}
