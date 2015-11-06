package com.capgemini.heartbeat;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;

public class GridProperties extends TaskProperties {
	public GridProperties(String fileLocation) {
		super(fileLocation);
	}

	public List<Property> getPropertiesList() {
		HeartbeatFlow.log.info("Reading Selenium Grid servers properties...");
		JsonArray array = converter.convertFileToJSON(super.fileLocation);
		if (!array.getClass().equals(JsonNull.class) & array != null) {
			for (Object jsonProperty : array) {
				GridProperty prop = new GridProperty();
				prop.setUrl(converter.getProperty(jsonProperty, "url"));
				prop.setNodesList(nodesArray(jsonProperty, "nodes"));
				super.propertiesList.add(prop);
			}
		}
		return super.propertiesList;
	}

	private ArrayList<GridNode> nodesArray(Object jsonProperty, String name) {
		JsonArray jsonArray = converter.getArray(jsonProperty, name);
		ArrayList<GridNode> nodes = new ArrayList<GridNode>();
		for (Object jsonNodeProperty : jsonArray) {
			GridNode node = new GridNode();
			
				node.setBrowser(converter.getProperty(jsonNodeProperty, "browser"));
				node.setBrowserVersion(converter.getProperty(jsonNodeProperty, "browserVersion"));
				node.setPlatform(converter.getProperty(jsonNodeProperty, "platform"));
				nodes.add(node);
			

		}

		return nodes;
	}

}
