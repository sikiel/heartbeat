package com.capgemini.heartbeat;

import java.util.List;

import org.json.simple.JSONObject;

public class JenkinsProperties extends TaskProperties {

	JenkinsProperties(String fileLocation) {
		super(fileLocation);
	}

	public List<Property> getPropertiesList() {
		if (super.jsonArray != null) {
			for (Object jsonProperty : super.jsonArray) {
				Property prop = new JenkinsProperty();
				prop.setUrl((String) ((JSONObject) jsonProperty).get("url"));
				((JenkinsProperty) prop).setUsername((String) ((JSONObject) jsonProperty).get("username"));
				((JenkinsProperty) prop).setPassword((String) ((JSONObject) jsonProperty).get("password"));
				super.propertiesList.add(prop);
			}
		}
		return super.propertiesList;
	}

}
