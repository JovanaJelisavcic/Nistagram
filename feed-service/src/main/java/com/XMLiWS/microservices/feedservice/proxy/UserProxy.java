package com.XMLiWS.microservices.feedservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user", url="localhost:8200")
public interface UserProxy {

	
	@GetMapping("public/user/following/{username}")
	public ResponseEntity<List<String>> usersFollowingIds(@PathVariable String username);

	@GetMapping("/public/user/{username}/privacy")
	public ResponseEntity<Boolean> getPrivacy(@PathVariable String username);

	@GetMapping("public/user/followers/{username}")
	public ResponseEntity<List<String>> usersFollowers(@PathVariable String username);
	
}
