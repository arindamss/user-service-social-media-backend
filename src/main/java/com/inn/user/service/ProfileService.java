package com.inn.user.service;

import org.springframework.http.ResponseEntity;

import com.inn.user.dto.ProfileDTO;
import com.inn.user.pojo.ProfilePOJO;

public interface ProfileService {

	public ResponseEntity<ProfilePOJO> create(ProfileDTO profileDto);

	public ResponseEntity<String> update(ProfileDTO profileDto);
}
