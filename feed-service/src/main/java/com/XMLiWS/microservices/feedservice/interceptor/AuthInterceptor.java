package com.XMLiWS.microservices.feedservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.XMLiWS.microservices.feedservice.proxy.AuthProxy;


@Component
public class AuthInterceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	@Autowired
	private AuthProxy authProxy;
	@Override
	   public boolean preHandle(
			   
	      HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
				
	logger.info(request.getHeader("Access-Control-Allow-Methods")+ "u auth interceptor");
	        	String permission = authProxy.user(request.getHeader("Authorization"));
	        	if(permission.equals("user")) {
	        		return true;}
	        	else { return false;}
	        }
	   
	
}
