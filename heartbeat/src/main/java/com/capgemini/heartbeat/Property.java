package com.capgemini.heartbeat;



public class Property {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if(!url.equals(JsonFileConverter.DEFAULT_PROPERTY_VALUE) && !url.contains("http://")){
			HeartbeatFlow.log.warning("incorrect url: "+url+" Adding http://");
			this.url="http://"+url;
		}else{
			this.url = url;
		}

	}

}
