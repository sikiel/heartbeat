package com.capgemini.heartbeat;

public class HeartbeatProperties {
	public static final HeartbeatProperties NULL_PROPERTY = new HeartbeatProperties("nullPath", "nullPath", "nullPath",
			0, 0, 0);
	private final String jenkinsPropertiesPath;
	private final String gridPrpertiesPath;
	private final String csvReportPath;
	private ReportProperties reportProperties;

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

	public class ReportProperties {
		public final int CREATE;
		public final int DELETE;
		public final String PATH_TO_OLD;

		public ReportProperties(int create, int delete, String pathToOld) {
			this.CREATE = create;
			this.DELETE = delete;
			this.PATH_TO_OLD = pathToOld;
		}
	};

	private final Time time;

	public HeartbeatProperties(String jenkinsPropertiesPath, String gridPrpertiesPath, String csvReportPath,
			Integer delayHours, Integer delayMinutes, Integer delaySeconds) {
		this.jenkinsPropertiesPath = jenkinsPropertiesPath;
		this.gridPrpertiesPath = gridPrpertiesPath;
		this.csvReportPath = csvReportPath;
		this.time = new Time(delayHours, delayMinutes, delaySeconds);
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

	public ReportProperties getReportProperties() {
		return reportProperties;
	}

	public void setReportProperties(int create, int delete, String pathToOld) {
		this.reportProperties = new ReportProperties(create, delete, pathToOld);
	}

}
