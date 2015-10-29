package com.capgemini.heartbeat;

public class HeartbeatProperties {

	public static final HeartbeatProperties NULL_PROPERTY = new HeartbeatProperties("nullPath", "nullPath", "nullPath");
	private final String jsonPropertiesPath;
	private final String gridPrpertiesPath;
	private final String csvReportPath;

	public HeartbeatProperties(String jsonPropertiesPath, String gridPrpertiesPath, String csvReportPath) {
		this.jsonPropertiesPath = jsonPropertiesPath;
		this.gridPrpertiesPath = gridPrpertiesPath;
		this.csvReportPath = csvReportPath;
	}

	public String getJsonPropertiesPath() {
		return jsonPropertiesPath;
	}

	public String getGridPrpertiesPath() {
		return gridPrpertiesPath;
	}

	public String getCsvReportPath() {
		return csvReportPath;
	}
	
	

}
