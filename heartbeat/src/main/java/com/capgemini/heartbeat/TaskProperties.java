package com.capgemini.heartbeat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class TaskProperties {
	protected String fileLocation;
	protected JsonFileConverter converter;
	protected static final Logger log = Logger.getLogger(HeartbeatFlow.class.getName());
	protected List<Property> propertiesList;

	public TaskProperties(String fileLocation) {
		this.propertiesList = new ArrayList<Property>();
		this.fileLocation = fileLocation;
		this.converter = new JsonFileConverter();

	}

	public abstract List<Property> getPropertiesList();

}
