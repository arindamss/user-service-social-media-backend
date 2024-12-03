package com.inn.user.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.user.pojo.ProfilePOJO;

@RequestMapping(path = "/user/profile")
public interface ProfileRest {

	@PostMapping(path = "/v1/create")
	public ResponseEntity<ProfilePOJO> create(@RequestBody ProfilePOJO profilePojo);
	
	@PutMapping(path = "/v1/update")
	public ResponseEntity<String> update(@RequestBody ProfilePOJO profilePojo);
	
}
