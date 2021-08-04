package com.XMLiWS.microservices.apigateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.XMLiWS.microservices.apigateway.config.FeignSupportConfig;


@FeignClient(name = "media", url = "http://localhost:8100", configuration = FeignSupportConfig.class)
public interface MediaClient {
	
	 @PostMapping(value = "/uploadFile", consumes = {
	             MediaType.MULTIPART_FORM_DATA_VALUE })
	    public String uploadFile(@RequestHeader("Authorization") String bearerToken,@RequestPart("file") MultipartFile file);
}
