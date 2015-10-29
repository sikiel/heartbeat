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
		JsonArray array = converter.convertFileToJSON(super.fileLocation);
		if (array != null) {
			for (Object jsonProperty : array) {
				Property prop = new JenkinsProperty();
				prop.setUrl(((JsonObject) jsonProperty).get("url").getAsString());
				((JenkinsProperty) prop).setUsername(((JsonObject) jsonProperty).get("username").getAsString());
				((JenkinsProperty) prop).setPassword(((JsonObject) jsonProperty).get("password").getAsString());
				super.propertiesList.add(prop);
			}
		}
		return super.propertiesList;
	}

}
