package com.inn.user.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class FollowingId implements Serializable{
	
	private Long followerId;
	private Long followeeId;
	
	public FollowingId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FollowingId(Long followerId, Long followeeId) {
		super();
		this.followerId = followerId;
		this.followeeId = followeeId;
	}
	
	
	
	
}