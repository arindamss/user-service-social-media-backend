package com.inn.user.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.user.dto.UserCrediential;
import com.inn.user.dto.UserDTO;

import jakarta.validation.Valid;


@RequestMapping(path = "/user")
public interface UserRest {
	
	@PostMapping(path = "/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody UserDTO userDto);
	
	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody UserCrediential userCrediential);
	
	@DeleteMapping(path = "/delete")
	public ResponseEntity<String> delete();
	
}
