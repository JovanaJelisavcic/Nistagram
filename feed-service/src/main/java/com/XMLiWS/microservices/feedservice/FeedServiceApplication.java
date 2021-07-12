package com.XMLiWS.microservices.feedservice;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeedServiceApplication {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "UK"));
		SpringApplication.run(FeedServiceApplication.class, args);
	}

}
