package com.XMLiWS.microservices.mediaservice.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.XMLiWS.microservices.mediaservice.storage.FileStorageService;
import com.XMLiWS.microservices.mediaservice.storage.UploadFileResponse;

@RestController
public class MediaController {
	Logger logger = LoggerFactory.getLogger(MediaController.class);
	
	    @Autowired
	    private FileStorageService fileStorageService;
	    
	    @PostMapping(value = "/uploadFile", consumes = {MediaType.APPLICATION_JSON_VALUE,
	            MediaType.MULTIPART_FORM_DATA_VALUE })
	    public UploadFileResponse uploadFile(@RequestPart("file") MultipartFile file) {
	    	logger.info("hits upload file + " + file.getName());
	        String fileName = fileStorageService.storeFile(file);

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/displayFile/")
	                .path(fileName)
	                .toUriString();

	        return new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
	    }


	    @GetMapping("/displayFile/{fileName:.+}")
	    public ResponseEntity<Resource> displayFile(@PathVariable String fileName, HttpServletRequest request) {
	        // Load file as Resource
	        Resource resource = fileStorageService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	        } catch (IOException ex) {
	            
	        }

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
	    }
	    
	    
}
	
