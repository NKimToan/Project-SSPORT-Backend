package com.SSPORT.SSPORT.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SSPORT.SSPORT.requests.AdminRequest;
import com.SSPORT.SSPORT.responses.AdminResponse;
import com.SSPORT.SSPORT.responses.ApiResponse;
import com.SSPORT.SSPORT.services.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@GetMapping("")
	ApiResponse<List<AdminResponse>> getAllAdmins() {
		return ApiResponse.<List<AdminResponse>>builder()
				.code(200)
				.message("List all Admin")
				.result(adminService.getAllAdmins())
				.build();
	}
	
	@GetMapping("/{adminId}")
	ApiResponse<AdminResponse> getAdmin( @Valid @PathVariable int adminId) {
		return ApiResponse.<AdminResponse>builder()
				.code(200)
				.message("Admin information")
				.result(adminService.getAdmin(adminId))
				.build();
	}
	
	@PostMapping("")
	ApiResponse<AdminResponse> createAdmin(@Valid @RequestBody AdminRequest adminRequest) {
		return ApiResponse.<AdminResponse>builder()
				.code(201)
				.message("Create admin successfull")
				.result(adminService.createAdmin(adminRequest))
				.build();
	}
	
	@PutMapping("/{adminId}")
	ApiResponse<AdminResponse> updateAdmin(@Valid @RequestBody AdminRequest adminRequest, @PathVariable int adminId) {
		return ApiResponse.<AdminResponse>builder()
				.code(200)
				.message("Update admin successfull")
				.result(adminService.updateAdmin(adminRequest, adminId))
				.build();
	}
	
	@DeleteMapping("/{adminId}")
	ApiResponse<AdminResponse> deleteAdmin(@Valid @PathVariable int adminId) {
		return ApiResponse.<AdminResponse>builder()
				.code(200)
				.message("Delete admin successfull")
				.result(adminService.deleteAdmin(adminId))
				.build();
	}
	
	@GetMapping("/my-infor")
	ApiResponse<AdminResponse> getMyInfo() {
		return ApiResponse.<AdminResponse>builder()
				.code(200)
				.message("My infor")
				.result(adminService.getMyInfo())
				.build();
	}
}
