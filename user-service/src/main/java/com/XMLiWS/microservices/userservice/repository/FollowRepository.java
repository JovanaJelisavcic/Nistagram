package com.XMLiWS.microservices.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.XMLiWS.microservices.userservice.bean.Followers;

public interface FollowRepository extends JpaRepository<Followers, Long> {


	@Query(value="SELECT * from FOLLOWERS where from_user_fk = ?1 AND to_user_fk = ?2", nativeQuery=true)
	Followers findFollowings(long fromId, long toId);

}
