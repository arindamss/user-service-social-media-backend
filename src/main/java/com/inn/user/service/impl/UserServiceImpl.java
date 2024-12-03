package com.inn.user.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inn.user.constants.UserConstants;
import com.inn.user.dto.UserCrediential;
import com.inn.user.dto.UserDTO;
import com.inn.user.entity.Profile;
import com.inn.user.entity.User;
import com.inn.user.jwt.CustomUserDetails;
import com.inn.user.jwt.JWTHelper;
import com.inn.user.jwt.UserDetailsServiceImpl;
import com.inn.user.repository.ProfileRepository;
import com.inn.user.repository.UserRepository;
import com.inn.user.service.UserService;
import com.inn.user.utillity.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProfileRepository profileRepo;

	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl customUserDetails;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTHelper jwtHelper;	
	
	
	@Override
	public ResponseEntity<String> signup(UserDTO userDto) {
		try {
			System.out.println("=====I am in Service : "+userDto.getUsername());
			Optional<User> usernameIsPresent = userRepo.findByUsername(userDto.getUsername());
			
			if(usernameIsPresent.isPresent()) {
				log.debug("User is already present");
				return UserUtil.getResponseEntity("User is already present", HttpStatus.BAD_REQUEST);
			}
			log.debug("User is-not present");
			User user = modelmapper.map(userDto, User.class);
			user.setStatus(true);
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			System.out.println("-----Final User : "+user);
			
			userRepo.save(user);
			
			return UserUtil.getResponseEntity("Signup Successfull", HttpStatus.CREATED);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<String> login(UserCrediential userCred){
		try {
			System.out.println("####### Login insert");
			Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(userCred.getUsername(), userCred.getPassword()));
			
			if(authentication.isAuthenticated()) {
				
				System.out.println("User is Authenticated");
				
				final CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
				
				log.info("User Details : ", userDetails);
				
				return new ResponseEntity<>("{\"token\":" + jwtHelper.generateToken(userDetails) + "\"}", HttpStatus.OK);
				
			}
			else {
				return UserUtil.getResponseEntity("Bad Crediential", HttpStatus.BAD_REQUEST);
			}
		}
		catch (BadCredentialsException e) {
            log.error("Bad credentials error: {}", e.getMessage());
                return new ResponseEntity<String>("{\"message\":\"Bad Credentials.\"}", HttpStatus.BAD_REQUEST);
        } 
		catch(Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<String> delete(){
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			
			Optional<User> findUser = userRepo.findById(userDetails.getId());
			if(findUser.isEmpty()) {
				return UserUtil.getResponseEntity("User not found", HttpStatus.BAD_REQUEST);
			}
			
			User user = findUser.get();
			
			Profile profile = user.getProfile();
			if(profile == null) {
				userRepo.delete(user);
				return UserUtil.getResponseEntity("User deleted successfully", HttpStatus.OK);
			}
			
			profileRepo.delete(profile);
			userRepo.delete(user);
			
			return UserUtil.getResponseEntity("Profile deleted successfully", HttpStatus.OK);
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
