package com.capgemini.heartbeat;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JenkinsProperties extends TaskProperties {

	JsonFileConverter converter;
	JenkinsProperties(String fileLocation) {
		super(fileLocation);
		converter = new JsonFileConverter();
		
	}

	public List<Property> getPropertiesList() {
		log.info("Reading Jenkins servers properties...");
		JsonArray array = converter.convertFileToJSON(super.fileLocation);
		if (array != null) {
			for (Object jsonProperty : array) {
				Property prop = new JenkinsProperty();
				prop.setUrl(getProperty((JsonObject) jsonProperty,"url"));
				((JenkinsProperty) prop).setUsername(getProperty((JsonObject) jsonProperty,"username"));
				((JenkinsProperty) prop).setPassword(getProperty((JsonObject) jsonProperty,"password"));
				super.propertiesList.add(prop);
			}
		}
		return super.propertiesList;
	}

}
