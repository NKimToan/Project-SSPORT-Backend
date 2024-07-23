package com.SSPORT.SSPORT.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SSPORT.SSPORT.entities.Admin;
import com.SSPORT.SSPORT.exceptions.ErrorCode;
import com.SSPORT.SSPORT.exceptions.HandleRuntimeException;
import com.SSPORT.SSPORT.mappers.AdminMapper;
import com.SSPORT.SSPORT.repositories.AdminRepository;
import com.SSPORT.SSPORT.requests.AdminRequest;
import com.SSPORT.SSPORT.responses.AdminResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PreAuthorize("hasRole('ADMIN')")
	public List<AdminResponse> getAllAdmins() {
		return adminRepository.findAll().stream().map(adminMapper::toAdminResponse).toList();
	}

	@PostAuthorize("hasRole('ADMIN')")
	public AdminResponse getAdmin(int admin_id) {
		return adminRepository.findById(admin_id).map(adminMapper::toAdminResponse)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.ADMIN_NOT_EXISTED));
	}

	@PostAuthorize("hasRole('ADMIN')")
	public AdminResponse createAdmin(AdminRequest adminRequest) {
//		Kiểm tra sự tồn tại của username
		if (adminRepository.existsByUsername(adminRequest.getUsername())) {
			throw new HandleRuntimeException(ErrorCode.ADMIN_EXISTED);
		}

		Admin admin = adminMapper.toAdmin(adminRequest);

//		Băm password 
		admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));

		return adminMapper.toAdminResponse(adminRepository.save(admin));
	}

	@PostAuthorize("hasRole('ADMIN')")
	public AdminResponse updateAdmin(AdminRequest adminRequest, int adminId) {

		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.ADMIN_NOT_EXISTED));
		
		adminMapper.updateAdmin(admin, adminRequest);

//		Băm password
		admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));

		return adminMapper.toAdminResponse(adminRepository.save(admin));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public AdminResponse deleteAdmin(int adminId) {
		if (!adminRepository.existsById(adminId)) {
			throw new HandleRuntimeException(ErrorCode.ADMIN_NOT_EXISTED);
		}
		adminRepository.deleteById(adminId);
		return adminMapper.toAdminResponse(null);
	}

	public AdminResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();

		String name = context.getAuthentication().getName();

		Admin admin = adminRepository.findByUsername(name).orElseThrow(() -> new RuntimeException());

		return adminMapper.toAdminResponse(admin);
	}
	
	
	public Admin findById(int adminId) {
		return adminRepository.findById(adminId)
				.orElseThrow(() -> new HandleRuntimeException(ErrorCode.ADMIN_NOT_EXISTED));
	}
}
