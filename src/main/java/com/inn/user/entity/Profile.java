package com.inn.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
//	@OneToOne
//	@JoinColumn(name = "user_id")
//	private User user;
	
	private String name;
	private String gender;
	private int age;
	
	@Lob
	private byte[] image;
	
}
