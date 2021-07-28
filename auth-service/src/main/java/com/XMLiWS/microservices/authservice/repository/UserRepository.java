package com.XMLiWS.microservices.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.XMLiWS.microservices.authservice.model.UserDTO;



public interface UserRepository extends JpaRepository<UserDTO, Long>{
	
	UserDTO findByUsername(String username);

}
