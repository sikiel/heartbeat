package com.capgemini.heartbeat;

import java.sql.Struct;

public class HeartbeatProperties {
	public static final HeartbeatProperties NULL_PROPERTY = new HeartbeatProperties("nullPath", "nullPath", "nullPath", 0,0,0);
	private final String jenkinsPropertiesPath;
	private final String gridPrpertiesPath;
	private final String csvReportPath;

	public class Time {
		public int Hours;
		public int Minutes;
		public int Seconds;

		Time(int hour, int minute, int second) {
			this.Hours = hour;
			this.Minutes = minute;
			this.Seconds = second;
		}
	};

	private final Time time;

	public HeartbeatProperties(String jenkinsPropertiesPath, String gridPrpertiesPath, String csvReportPath,
			Integer delayHours, Integer delayMinutes, Integer delaySeconds) {
		this.jenkinsPropertiesPath = jenkinsPropertiesPath;
		this.gridPrpertiesPath = gridPrpertiesPath;
		this.csvReportPath = csvReportPath;
		this.time = new Time(delayHours, delayMinutes, delayMinutes);

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

	public Time getTime() {
		return time;
	}

}
