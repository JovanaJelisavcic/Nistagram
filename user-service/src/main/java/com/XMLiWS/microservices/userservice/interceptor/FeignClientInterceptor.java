package com.XMLiWS.microservices.userservice.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

  private static final String AUTHORIZATION_HEADER = "Authorization";

  public static String getBearerTokenHeader() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
  }

  @Override
  public void apply(RequestTemplate requestTemplate) {

	  if (requestTemplate.url().equals("/create")) {
          //don't add global header for the specific url
          return;
      }
	  
      requestTemplate.header(AUTHORIZATION_HEADER, getBearerTokenHeader());
   
  }
}
