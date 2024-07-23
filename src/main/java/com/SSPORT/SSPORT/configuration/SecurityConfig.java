package com.SSPORT.SSPORT.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	private final String[] PUBLIC_ENDPOINTS = {
			"/admin",
			"/admin/**",
			"/customer",
			"/customer/**",
			"/category",
			"/category/**",
			"/images/**",
			"/product",
			"/product/**",
			"/promotion",
			"/review",
			"/review/**",
			"/admin/auth/login",
			"/admin/auth/verify",
			"/admin/auth/refresh",
			"/admin/auth/logout",
			"/customer/auth/login",
			"/customer/auth/verify",
			"/customer/auth/refresh",
			"/customer/auth/logout",
		};
		
		@Autowired
		private CustomJwtDecoder customJwtDecoder;
		
		@Bean
	    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity.authorizeHttpRequests(request -> request
	        		.requestMatchers(HttpMethod.GET ,PUBLIC_ENDPOINTS)
	        		.permitAll()
	        		.requestMatchers(HttpMethod.POST ,PUBLIC_ENDPOINTS)
	                .permitAll()
	                .anyRequest()
	                .authenticated());
	        
	        httpSecurity.oauth2ResourceServer(
	        		oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
	        				.decoder(customJwtDecoder)
	        				.jwtAuthenticationConverter(jwtAuthenticationConverter()))
	        		.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
	        					
	        		);
	        
	        httpSecurity.csrf(AbstractHttpConfigurer::disable);
	        return httpSecurity.build();
	    }
		
	
//		Đổi tên mặc định từ SCOPE sang ROLE
		@Bean
	    JwtAuthenticationConverter jwtAuthenticationConverter() {

	        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
	        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

	        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
	        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
	        return jwtAuthenticationConverter;
	    }

	
//	Ghi đè Bean để xác định lại loại mã hash băm
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	
//	Tạo CORS để cho phép FE kết nối tới API trả về từ BE
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
