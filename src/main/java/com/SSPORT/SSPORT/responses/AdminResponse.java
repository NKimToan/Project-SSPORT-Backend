package com.SSPORT.SSPORT.responses;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data // Khởi tạo dữ liệu
@NoArgsConstructor // Hàm khởi tạo không tham số
@AllArgsConstructor // Hàm khơi tạo đầy ddue tham số
@Builder // Tự xây dựng kết quả trả về được dùng trong controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminResponse {
	
	@Id
	int admin_id;

	String username;
	
	String password;
	
	String role;
}