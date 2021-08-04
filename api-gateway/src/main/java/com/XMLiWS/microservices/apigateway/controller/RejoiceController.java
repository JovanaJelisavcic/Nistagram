package com.XMLiWS.microservices.apigateway.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.XMLiWS.microservices.apigateway.client.MediaClient;



@RestController
public class RejoiceController {

	@Autowired
	MediaClient mediaClient;
	
	
	
	@PostMapping(value = "/post", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String post(@RequestHeader("Authorization") String bearerToken,@RequestParam(value = "file",required = false) MultipartFile file
    		/*@RequestPart("userID") String userID,@RequestPart("url") String url,
    		@RequestPart("location") String location,@RequestPart("description") String description,
    		@RequestHeader("Authorization") String token*/) throws IOException {
		
		String response = mediaClient.uploadFile(bearerToken,file);
		
		return response;
		

		
		
    }

}
