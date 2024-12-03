package com.inn.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	@JsonIgnore
	private String password;
	private Boolean status;
//	private String role;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Profile profile;
	
}
