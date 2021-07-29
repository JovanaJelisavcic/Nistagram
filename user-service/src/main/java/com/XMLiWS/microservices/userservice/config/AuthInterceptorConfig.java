package com.XMLiWS.microservices.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.XMLiWS.microservices.userservice.interceptor.AuthInterceptor;

@Configuration
public class AuthInterceptorConfig implements WebMvcConfigurer {
	   @Autowired
	   AuthInterceptor authInterceptor;

	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(authInterceptor).excludePathPatterns("/public/**");
	   }
	}
