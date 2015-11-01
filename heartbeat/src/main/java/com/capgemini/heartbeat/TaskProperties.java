package com.capgemini.heartbeat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.JsonObject;


public abstract class TaskProperties {
	protected String fileLocation;
	protected static final Logger log = Logger.getLogger( HeartbeatFlow.class.getName());
	protected List<Property> propertiesList;
	public TaskProperties(String fileLocation) {
		this.propertiesList = new ArrayList<Property>();
		this.fileLocation = fileLocation;

	}
public abstract List<Property> getPropertiesList();
protected String getProperty(JsonObject jsonProperty, String name){
	if(jsonProperty.get(name)!=null){
		return jsonProperty.get(name).getAsString();
	}else{
		return "undefined";
	}
	
}
}
