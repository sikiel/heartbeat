package com.capgemini.heartbeat;

public class TaskResult {
	
	private Long timestamp;
	private String name;
	private String status;

	public TaskResult(Long timestamp, String name, String status) {
		this.timestamp = timestamp;
		this.name = name;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public String getStatus() {
		return status;
	}

}
