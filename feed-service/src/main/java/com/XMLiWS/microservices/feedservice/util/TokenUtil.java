package com.XMLiWS.microservices.feedservice.util;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class TokenUtil {
	
	Logger logger = LoggerFactory.getLogger(TokenUtil.class);

	
	public boolean checkIdentity(String username, String token) {
			if(username.equalsIgnoreCase(extractIdentity(token))) {
				return true;
		}
		return false;
		
	}
	
	public String extractIdentity(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		String[] parts = payload.split(",");
		String[] part1 = parts[0].split(":");
		String sub= part1[1].replaceAll("\"", "").strip();
		logger.info("SUB is "+ sub);
		return sub;
	}
	
	

}
