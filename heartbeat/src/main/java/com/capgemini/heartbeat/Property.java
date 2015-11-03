package com.capgemini.heartbeat;

import java.util.logging.Logger;

public class Property {
	private static final Logger log = Logger.getLogger(Property.class.getName());
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if(!url.equals(JsonFileConverter.DEFAULT_PROPERTY_VALUE) && !url.contains("http://")){
			log.warning("incorrect url: "+url+" Adding http://");
			this.url="http://"+url;
		}else{
			this.url = url;
		}

	}

}
