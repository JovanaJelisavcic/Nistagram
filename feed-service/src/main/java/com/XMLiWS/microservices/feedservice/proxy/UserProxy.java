package com.XMLiWS.microservices.feedservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user", url="localhost:8200")
public interface UserProxy {

	
	@GetMapping("/user/following/{id}")
	public List<Long> usersFollowingIds(@PathVariable long id);

	@GetMapping("/user/{id}/seeable")
	public boolean getSeeable(@PathVariable long id);
}
