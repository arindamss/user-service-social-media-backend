package com.inn.user.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.inn.user.utillity.UserUtil;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> argumentNotValid(MethodArgumentNotValidException ex){
		
		Map<String, String> errors = new HashMap<>();
		
		for(FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> badCredentialsException(BadCredentialsException ex){
		return UserUtil.getResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
