package com.SSPORT.SSPORT.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@SuppressWarnings("deprecation")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	@Bean
	public ReviewAccess reviewAccessService() {
		return new ReviewAccess();
	}
	
	@Bean 
	public CartAccess cartAccess() {
		return new CartAccess();
	}
}
