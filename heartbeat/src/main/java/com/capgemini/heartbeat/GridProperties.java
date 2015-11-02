package com.capgemini.heartbeat;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GridProperties extends TaskProperties {
	public GridProperties(String fileLocation) {
		super(fileLocation);
	}

	public List<Property> getPropertiesList() {
		log.info("Reading Selenium Grid servers properties...");
		JsonArray array = converter.convertFileToJSON(super.fileLocation);
		if (array != null) {
			for (Object jsonProperty : array) {
				GridProperty prop = new GridProperty();
				prop.setUrl(converter.getProperty((JsonObject) jsonProperty, "url"));
				prop.setNodesList(nodesArray((JsonObject) jsonProperty, "nodes"));
				super.propertiesList.add(prop);
			}
		}
		return super.propertiesList;
	}

	private ArrayList<GridNode> nodesArray(JsonObject jsonProperty, String name) {
		JsonArray jsonArray = converter.getArray(jsonProperty, name);
		ArrayList<GridNode> nodes = new ArrayList<GridNode>();
		for (Object jsonNodeProperty : jsonArray) {
			GridNode node = new GridNode();
			node.setBrowser(converter.getProperty((JsonObject) jsonNodeProperty, "browser"));
			node.setBrowserVersion(converter.getProperty((JsonObject) jsonNodeProperty, "browserVersion"));
			node.setPlatform(converter.getProperty((JsonObject) jsonNodeProperty, "platform"));
			nodes.add(node);

		}

		return nodes;
	}

}
