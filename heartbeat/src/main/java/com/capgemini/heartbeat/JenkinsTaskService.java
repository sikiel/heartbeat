package com.capgemini.heartbeat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class JenkinsTaskService implements TaskService {

	List<Property> properties;
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
		Iterator<Property> iter = properties.iterator();
		while (iter.hasNext()) {
			;
			JenkinsProperty jp = (JenkinsProperty) iter.next();
			boolean status = false;
			try {
				status = checkJenkins(jp.getUrl());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String name = "JENKINS - " + jp.getUrl();
			Long timestamp = new Date().getTime();
			resultCollector.addResult(new TaskResult(timestamp, name, status ? "SUCCESS" : "FAILED"));
		}
	}

	private boolean checkJenkins(String adress) throws IOException {
		URL url = new URL("http://" + adress);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			return true;
		} else {
			return false;
		}
	}

}
