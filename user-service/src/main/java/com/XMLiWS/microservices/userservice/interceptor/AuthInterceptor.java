package com.XMLiWS.microservices.userservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.XMLiWS.microservices.userservice.proxy.AuthProxy;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private AuthProxy authProxy;
	@Override
	   public boolean preHandle(
	      HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String permission = authProxy.user();
		System.out.println("THE PERMISION WAS "+ permission);
	      	if(permission.equals("user")) {
	      return true;}
	      	else { return false;}
	   }
	
}
