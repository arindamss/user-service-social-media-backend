package com.inn.user.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.user.entity.User;

@RequestMapping(path = "/user/following")
public interface FollowRest {

	@PostMapping(path = "/follow")
	public ResponseEntity<String> follow(@RequestParam("followeeId") Long followeeId);
	
	@DeleteMapping(path = "/unfollow")
	public ResponseEntity<String> unfollow(@RequestParam("unfollowId") Long unfollowId);
	
	@GetMapping(path = "/followers")
	public ResponseEntity<List<User>> getAllFollowers();
	
	@GetMapping(path = "/following")
	public ResponseEntity<List<User>> getFollowing(); 
	
}
