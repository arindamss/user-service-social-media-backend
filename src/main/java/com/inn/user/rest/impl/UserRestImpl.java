package com.inn.user.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.user.constants.UserConstants;
import com.inn.user.dto.UserCrediential;
import com.inn.user.dto.UserDTO;
import com.inn.user.rest.UserRest;
import com.inn.user.service.UserService;
import com.inn.user.utillity.UserUtil;

@RestController
public class UserRestImpl implements UserRest{
	
	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<String> signup(UserDTO userDto) {
		try {
			return userService.signup(userDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> login(UserCrediential userCrediential) {
		try {
			return userService.login(userCrediential);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> delete() {
		try {
			return userService.delete();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
