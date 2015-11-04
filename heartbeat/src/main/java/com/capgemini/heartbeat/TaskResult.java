package com.capgemini.heartbeat;

import java.util.Date;

public class TaskResult {
	
	private static final String SEPARATOR = ";";
	private static final String NEW_LINE = System.getProperty("line.separator");
	private Date timestamp;
	private String name;
	private String status;

	public TaskResult(Long timestamp, String name, String status) {
		this.timestamp = new Date(timestamp);
		this.name = name;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(timestamp);
		builder.append(SEPARATOR);
		builder.append(name);
		builder.append(SEPARATOR);
		builder.append(status);
		builder.append(NEW_LINE);
		return builder.toString();
	}

}
