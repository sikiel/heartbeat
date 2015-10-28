package com.capgemini.heartbeat;

import java.util.List;

import org.json.simple.JSONObject;

public class GridProperties extends TaskProperties {

	public GridProperties(String fileLocation) {
		super(fileLocation);
	}

	public List<Property> getPropertiesList() {
		if (super.jsonArray != null) {
			for (Object jsonProperty : super.jsonArray) {
				Property prop = new JenkinsProperty();
				prop.setUrl((String) ((JSONObject) jsonProperty).get("url"));
				super.propertiesList.add(prop);
			}
		}
		return super.propertiesList;
	}

}
