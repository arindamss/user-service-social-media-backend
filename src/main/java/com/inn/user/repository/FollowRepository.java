package com.inn.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inn.user.entity.Following;
import com.inn.user.entity.FollowingId;
import com.inn.user.entity.User;


@Repository
public interface FollowRepository extends JpaRepository<Following, FollowingId> {

	@Modifying
	@Query("DELETE FROM Following f WHERE f.follower.id = :currentUserId AND f.followee.id = :followeeUserId")
	public void deleteFollow(@Param("currentUserId") Long currentUserId, @Param("followeeUserId") Long followeeUserId);
	
	@Query("SELECT f.followee FROM Following f WHERE f.follower = :currentUser")
	public List<User> findByFollower(@Param("currentUser")User follower);
	
	@Query("SELECT f.follower FROM Following f WHERE f.followee = :currentUser")
	public List<User> findByFollowee(@Param("currentUser")User followee);
	
}
 