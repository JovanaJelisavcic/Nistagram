package com.XMLiWS.microservices.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.XMLiWS.microservices.userservice.bean.Followers;

public interface FollowRepository extends JpaRepository<Followers, Long> {

}
