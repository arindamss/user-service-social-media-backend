package com.inn.user.utillity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
	
	public static ResponseEntity<String> getResponseEntity(String msg, HttpStatus status){
		return new ResponseEntity<String>("{\"message\":"+"\""+msg+"\"}", status);
	}
}
