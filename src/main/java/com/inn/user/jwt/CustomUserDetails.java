package com.inn.user.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails {
	
	private Long id;
	
	private String username;
	private String password;
	private Boolean status;
	
	public CustomUserDetails(Long id, String username, String password, Boolean status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.status = status;
	}
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

}
