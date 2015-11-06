package com.capgemini.heartbeat;

import java.util.List;

import com.google.gson.JsonArray;

public class JenkinsProperties extends TaskProperties {

	
	JenkinsProperties(String fileLocation) {
		super(fileLocation);
	}

	public List<Property> getPropertiesList() {
		HeartbeatFlow.log.info("Reading Jenkins servers properties...");
		JsonArray array = converter.convertFileToJSON(super.fileLocation);
		if (array != null) {
			for (Object jsonProperty : array) {
				Property prop = new JenkinsProperty();
				prop.setUrl(converter.getProperty(jsonProperty,"url"));
				((JenkinsProperty) prop).setUsername(converter.getProperty(jsonProperty,"username"));
				((JenkinsProperty) prop).setPassword(converter.getProperty(jsonProperty,"password"));
				((JenkinsProperty) prop).setJobName(converter.getProperty(jsonProperty,"jobname"));
				super.propertiesList.add(prop);
			}
		}
		return super.propertiesList;
	}

}
