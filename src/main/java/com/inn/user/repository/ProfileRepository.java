package com.inn.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.user.entity.Profile;
import com.inn.user.entity.User;


public interface ProfileRepository extends JpaRepository<Profile, Long>{

//	Optional<Profile>  findByUser(User user);
	
}
