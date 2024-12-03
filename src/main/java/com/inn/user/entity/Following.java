package com.inn.user.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Following {

	@EmbeddedId
	private FollowingId id;
	
	@ManyToOne
	@MapsId("followerId")
	@JoinColumn(name = "follower_id")
	private User follower;
	
	@ManyToOne
	@MapsId("followeeId")
	@JoinColumn(name = "followee_id")
	private User followee;
}


