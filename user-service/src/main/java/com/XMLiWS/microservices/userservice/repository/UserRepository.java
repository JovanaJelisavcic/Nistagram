package com.XMLiWS.microservices.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.XMLiWS.microservices.userservice.bean.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
		User findByUserId(Long userId);

}
