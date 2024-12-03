package com.inn.user.service;

import org.springframework.http.ResponseEntity;

import com.inn.user.dto.UserCrediential;
import com.inn.user.dto.UserDTO;

public interface UserService {

	public ResponseEntity<String> signup(UserDTO userDto);

	public ResponseEntity<String> login(UserCrediential userCrediential);

	public ResponseEntity<String> delete();
}
