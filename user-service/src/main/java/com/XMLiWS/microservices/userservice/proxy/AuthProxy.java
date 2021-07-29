package com.XMLiWS.microservices.userservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.XMLiWS.microservices.userservice.bean.User;


@FeignClient(name= "auth", url="localhost:8300")
public interface AuthProxy {
	
	@PostMapping("/create")
	public String create(@RequestBody User userDTO);
	@GetMapping("/")
	public String user(@RequestHeader("Authorization") String token);

}
