package com.capgemini.heartbeat;

import java.util.ArrayList;
import java.util.List;


public abstract class TaskProperties {
	protected String fileLocation;

	protected List<Property> propertiesList;
	public TaskProperties(String fileLocation) {
		this.propertiesList = new ArrayList<Property>();
		this.fileLocation = fileLocation;

	}
public abstract List<Property> getPropertiesList();
}
