package com.capgemini.heartbeat;

public class JenkinsBuild {

	private JenkinsProperty jenkinsProperty;
	private Long timestamp;

	public JenkinsBuild(JenkinsProperty jenkinsProperty, Long timestamp) {
		this.jenkinsProperty = jenkinsProperty;
		this.timestamp = timestamp;
	}

	public JenkinsProperty getJenkinsProperty() {
		return jenkinsProperty;
	}

	public Long getTimestamp() {
		return timestamp;
	}

}
