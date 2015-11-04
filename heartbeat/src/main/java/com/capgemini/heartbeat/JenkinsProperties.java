package com.capgemini.heartbeat;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JenkinsProperties extends TaskProperties {

	
	JenkinsProperties(String fileLocation) {
		super(fileLocation);
	}

	public List<Property> getPropertiesList() {
		log.info("Reading Jenkins servers properties...");
		JsonArray array = converter.convertFileToJSON(super.fileLocation);
		if (array != null) {
			for (Object jsonProperty : array) {
				Property prop = new JenkinsProperty();
				prop.setUrl(converter.getProperty((JsonObject) jsonProperty,"url"));
				((JenkinsProperty) prop).setUsername(converter.getProperty((JsonObject) jsonProperty,"username"));
				((JenkinsProperty) prop).setPassword(converter.getProperty((JsonObject) jsonProperty,"password"));
				((JenkinsProperty) prop).setJobName(converter.getProperty((JsonObject) jsonProperty,"jobname"));
				super.propertiesList.add(prop);
			}
		}
		return super.propertiesList;
	}

}
