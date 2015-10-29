package com.capgemini.heartbeat;

import java.util.Date;

public class TaskResult {
	
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

}
