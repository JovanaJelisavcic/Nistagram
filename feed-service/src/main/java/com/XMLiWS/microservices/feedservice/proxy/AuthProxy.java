package com.XMLiWS.microservices.feedservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name= "auth", url="localhost:8300")
public interface AuthProxy {
	@GetMapping("/")
	public String user(@RequestHeader("Authorization") String token);
	
}
