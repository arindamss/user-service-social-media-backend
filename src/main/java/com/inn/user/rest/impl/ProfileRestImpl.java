package com.inn.user.rest.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.inn.user.constants.UserConstants;
import com.inn.user.dto.ProfileDTO;
import com.inn.user.pojo.ProfilePOJO;
import com.inn.user.rest.ProfileRest;
import com.inn.user.service.ProfileService;
import com.inn.user.utillity.UserUtil;

@RestController
@CrossOrigin(origins = "*")
public class ProfileRestImpl implements ProfileRest{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ProfileService profileService;

	@Override
	public ResponseEntity<ProfilePOJO> create(ProfilePOJO profilePojo) {
		try {
			return profileService.create(modelMapper.map(profilePojo, ProfileDTO.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ProfilePOJO(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(ProfilePOJO profilePojo) {
		try {
			return profileService.update(modelMapper.map(profilePojo, ProfileDTO.class));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
