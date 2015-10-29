package com.capgemini.heartbeat;

public class HeartbeatProperties {

	public static final HeartbeatProperties NULL_PROPERTY = new HeartbeatProperties("nullPath", "nullPath", "nullPath");
	private final String jenkinsPropertiesPath;
	private final String gridPrpertiesPath;
	private final String csvReportPath;

	public HeartbeatProperties(String jenkinsPropertiesPath, String gridPrpertiesPath, String csvReportPath) {
		this.jenkinsPropertiesPath = jenkinsPropertiesPath;
		this.gridPrpertiesPath = gridPrpertiesPath;
		this.csvReportPath = csvReportPath;
	}

	public String getJenkinsPropertiesPath() {
		return jenkinsPropertiesPath;
	}

	public String getGridPrpertiesPath() {
		return gridPrpertiesPath;
	}

	public String getCsvReportPath() {
		return csvReportPath;
	}

}
