package com.inn.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.inn.user.constants.UserConstants;
import com.inn.user.entity.Following;
import com.inn.user.entity.FollowingId;
import com.inn.user.entity.User;
import com.inn.user.jwt.CustomUserDetails;
import com.inn.user.repository.FollowRepository;
import com.inn.user.repository.UserRepository;
import com.inn.user.service.FollowService;
import com.inn.user.utillity.UserUtil;

import jakarta.transaction.Transactional;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowRepository followRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public ResponseEntity<String> follow(Long followeeId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		Optional<User> findFollowUser = userRepo.findById(userDetails.getId());
		if (findFollowUser.isEmpty()) {
			return UserUtil.getResponseEntity("Follower user does't exist.", HttpStatus.BAD_REQUEST);
		}

		Optional<User> findFolloweeUser = userRepo.findById(followeeId);
		if (findFolloweeUser.isEmpty()) {
			return UserUtil.getResponseEntity("Followee user does't exist.", HttpStatus.BAD_REQUEST);
		}

		User followerUser = findFollowUser.get();
		User followeeUser = findFolloweeUser.get();

		FollowingId followingId = new FollowingId(followerUser.getId(), followeeUser.getId());
		Following following = new Following();
		following.setId(followingId);
		following.setFollower(followerUser);
		following.setFollowee(followeeUser);

		followRepo.save(following);

		return UserUtil.getResponseEntity("Follow request sent.", HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<String> unfollow(Long unfollowId) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			Optional<User> findCurrentUser = userRepo.findById(userDetails.getId());
			if (findCurrentUser.isEmpty()) {
				return UserUtil.getResponseEntity("Current user does't exist.", HttpStatus.BAD_REQUEST);
			}

			Optional<User> findFolloweeUser = userRepo.findById(unfollowId);
			if (findFolloweeUser.isEmpty()) {
				return UserUtil.getResponseEntity("Followee user does't exist.", HttpStatus.BAD_REQUEST);
			}

			User currentUser = findCurrentUser.get();
			User followeeUser = findFolloweeUser.get();

			followRepo.deleteFollow(currentUser.getId(), followeeUser.getId());

			return UserUtil.getResponseEntity("Follow deleted.", HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return UserUtil.getResponseEntity(UserConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getAllFollowers() {
		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			Optional<User> findCurrentUser = userRepo.findById(userDetails.getId());
			if (findCurrentUser.isEmpty()) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
			}

			User currentUser = findCurrentUser.get();
			List<User> allFollower = followRepo.findByFollowee(currentUser);

			return new ResponseEntity<>(allFollower, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getAllFolling() {
		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			Optional<User> findCurrentUser = userRepo.findById(userDetails.getId());
			if (findCurrentUser.isEmpty()) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
			}

			User currentUser = findCurrentUser.get();
			List<User> allFollower = followRepo.findByFollower(currentUser);

			return new ResponseEntity<>(allFollower, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
