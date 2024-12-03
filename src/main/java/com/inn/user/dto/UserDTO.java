package com.inn.user.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	
	@NotBlank(message = "Username is required")
//	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	private String username;
	
	@NotBlank(message = "Password is required")
//	@Size(min = 8, message = "Password must be at least 8 characters" )
	private String password;
}
