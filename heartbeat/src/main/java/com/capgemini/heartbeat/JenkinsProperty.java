package com.capgemini.heartbeat;

public class JenkinsProperty extends Property {
	private String username, password, jobName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public boolean equals(Object arg0) {
		JenkinsProperty jp = (JenkinsProperty) arg0;
		if (this.getUrl().equals(jp.getUrl()))
			return true;
		else
			return false;
	}

}
