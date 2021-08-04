package com.XMLiWS.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})  
public class ApiGatewayApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	

	
	 @Bean(name = "multipartResolver")  
	    public MultipartResolver multipartResolver()  
	    {  
	        CommonsMultipartResolver resolver = new CommonsMultipartResolver();  
	 resolver.setMaxUploadSize(50 * 1024 * 1024);// Upload file size 5M 5*1024*1024
	        return resolver;  
	    } 

}
