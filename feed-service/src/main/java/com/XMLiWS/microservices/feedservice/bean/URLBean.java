package com.XMLiWS.microservices.feedservice.bean;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Embeddable
public class URLBean {

	String url;
	@Enumerated(EnumType.STRING)
	MediaTypeEnum mediaType;
	
	public URLBean() {}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MediaTypeEnum getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaTypeEnum mediaType) {
		this.mediaType = mediaType;
	}

	public URLBean(String url, MediaTypeEnum mediaType) {
		super();
		this.url = url;
		this.mediaType = mediaType;
	}
	
	
}
