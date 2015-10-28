package com.capgemini.heartbeat;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class JenkinsTaskService implements TaskService {

	List<JenkinsProperties> properties;
	private ResultCollector resultCollector;

	public JenkinsTaskService(TaskProperties jenkinsPrperties) {
		this.properties = jenkinsPrperties.getPropertiesList();
		resultCollector = new ResultCollector();
	}

	public ResultCollector getTasksResult() {
		prepareTasks();
		return resultCollector;
	}

	private void prepareTasks() {
		Iterator<JenkinsProperties> iter = properties.iterator();
		while (iter.hasNext()) {
			JenkinsProperties jp = iter.next();
			boolean status = checkJenkins(jp.getUrl());
			String name = "JENKINS - " + jp.getUrl();
			Long timestamp = new Date().getTime();
			resultCollector.addResult(new TaskResult(timestamp, name, status ? "SUCCESS" : "FAILED"));
		}
	}

	private boolean checkJenkins(String adress) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(adress);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			return true;
		} else {
			return false;
		}
	}

}
