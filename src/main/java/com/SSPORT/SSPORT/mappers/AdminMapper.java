package com.SSPORT.SSPORT.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.SSPORT.SSPORT.entities.Admin;
import com.SSPORT.SSPORT.requests.AdminRequest;
import com.SSPORT.SSPORT.responses.AdminResponse;

@Mapper(componentModel = "spring")
public interface AdminMapper {
	Admin toAdmin(AdminRequest adminRequest);
	
//	@Mapping(source = "admin_id", target = "admin_id")
	AdminResponse toAdminResponse(Admin admin);
	
	void updateAdmin(@MappingTarget Admin admin,AdminRequest adminRequest);
}
