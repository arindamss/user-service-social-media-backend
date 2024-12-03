package com.inn.user.rest.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.user.constants.UserConstants;
import com.inn.user.entity.User;
import com.inn.user.rest.FollowRest;
import com.inn.user.service.FollowService;
import com.inn.user.utillity.UserUtil;

@RestController
public class FollowRestImpl implements FollowRest {
	
	@Autowired
	private FollowService followService;

	@Override
	public ResponseEntity<String> follow(Long followeeId) {
		try {
			return followService.follow(followeeId);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> unfollow(Long unfollowId) {
		try {
			return followService.unfollow(unfollowId);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getAllFollowers() {
		try {
			return followService.getAllFollowers();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getFollowing() {
		try {
			return followService.getAllFolling();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
