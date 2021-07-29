package com.XMLiWS.microservices.authservice.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.XMLiWS.microservices.authservice.model.UserDTO;
import com.XMLiWS.microservices.authservice.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDetails registerNewUserAccount(UserDTO userDto) throws Exception {
		
	    if (userRepo.findByUsername(userDto.getUsername())!= null) {
	        throw new Exception(
	          "There is an account with that username:" + userDto.getUsername());
	    }
	    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
	    userRepo.save(userDto);
	   return new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),new ArrayList<>());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO user = userRepo.findByUsername(username);
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}


}
