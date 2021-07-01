package com.XMLiWS.microservices.feedservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.XMLiWS.microservices.feedservice.repository.PostRepository;

@RestController
public class SearchController {
	
	@Autowired
	private PostRepository repository;

}
