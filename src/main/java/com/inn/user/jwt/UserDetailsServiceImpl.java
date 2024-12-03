package com.inn.user.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inn.user.entity.User;
import com.inn.user.repository.UserRepository;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	System.out.println("##################");
    	// Fetch the user from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        if(user == null) {
        	throw new UsernameNotFoundException("User is not found");
        }
        System.out.println("##################");
        

        // Check if the user's status is false (inactive user)
        if (!user.getStatus()) {
            throw new RuntimeException("User is inactive. Please contact support.");
        }

        // Return the user details if active
        // return new org.springframework.security.core.userdetails.User(user.getId(),  user.getUsername(), user.getPassword(), new ArrayList<>());
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getStatus());
    }
}
