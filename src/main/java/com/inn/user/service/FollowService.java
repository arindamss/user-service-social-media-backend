package com.inn.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.inn.user.entity.User;

public interface FollowService {

	public ResponseEntity<String> follow(Long followeeId);

	public ResponseEntity<String> unfollow(Long unfollowId);

	public ResponseEntity<List<User>> getAllFollowers();

	public ResponseEntity<List<User>> getAllFolling(); 
	
}
