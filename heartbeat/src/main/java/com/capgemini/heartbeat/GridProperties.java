package com.capgemini.heartbeat;

import java.util.List;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GridProperties extends TaskProperties {
	JsonFileConverter converter;
	public GridProperties(String fileLocation) {
		super(fileLocation);
		converter = new JsonFileConverter();
	}

	public List<Property> getPropertiesList() {
		JsonArray array = converter.convertFileToJSON(super.fileLocation);
		if (array != null) {
			for (Object jsonProperty : array) {
				Property prop = new JenkinsProperty();
				prop.setUrl(((JsonObject) jsonProperty).get("url").getAsString());
				super.propertiesList.add(prop);
			}
		}
		return super.propertiesList;
	}

}
