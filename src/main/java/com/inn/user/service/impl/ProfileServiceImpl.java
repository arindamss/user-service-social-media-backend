package com.inn.user.service.impl;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inn.user.constants.UserConstants;
import com.inn.user.dto.ProfileDTO;
import com.inn.user.entity.Profile;
import com.inn.user.entity.User;
import com.inn.user.jwt.CustomUserDetails;
import com.inn.user.pojo.ProfilePOJO;
import com.inn.user.repository.ProfileRepository;
import com.inn.user.repository.UserRepository;
import com.inn.user.service.ProfileService;
import com.inn.user.utillity.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProfileRepository profileRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ResponseEntity<ProfilePOJO> create(ProfileDTO profileDto) {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			
			Optional<User> currentUser = userRepo.findById(userDetails.getId());
			
			if(currentUser.isPresent()) {
				User user = currentUser.get();
				Profile profile = modelMapper.map(profileDto, Profile.class);
				
				Profile saveProfile = profileRepo.save(profile);
				user.setProfile(saveProfile);
				userRepo.save(user);
				
				
				return new ResponseEntity<>(modelMapper.map(profile, ProfilePOJO.class), HttpStatus.CREATED);
			}
			else {
				return new ResponseEntity<>(new ProfilePOJO(), HttpStatus.BAD_GATEWAY);
			}
			

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ProfilePOJO(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(ProfileDTO profileDto) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			
			Optional<User> currentUser = userRepo.findById(userDetails.getId());
			
			if(currentUser.isEmpty()) {
				return UserUtil.getResponseEntity("User not found", HttpStatus.NOT_FOUND);
			}
			
			User user = currentUser.get();
			
			Profile profile = user.getProfile();
			if(profile == null) {
				return UserUtil.getResponseEntity("Profile does not exist", HttpStatus.BAD_REQUEST);
			}
//			Profile profile = findProfile.get();  // currentProfile.get();
			if(profileDto.getName() != null && !profileDto.getName().isBlank()) profile.setName(profileDto.getName());
			if(profileDto.getAge() > 0) profile.setAge(profileDto.getAge());
			if(profileDto.getImage() != null && !profileDto.getGender().isBlank()) profile.setImage(profileDto.getImage());
				
			profileRepo.save(profile);
				
			return UserUtil.getResponseEntity("Profile updated successfully", HttpStatus.OK);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			return UserUtil.getResponseEntity("Profile does't exist", HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}






