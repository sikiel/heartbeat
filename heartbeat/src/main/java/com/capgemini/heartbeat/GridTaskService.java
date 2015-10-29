package com.capgemini.heartbeat;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GridTaskService implements TaskService {
	List<Property> properties;
	private ResultCollector resultCollector;
	public GridTaskService(TaskProperties gridPrperties) {
		this.properties = gridPrperties.getPropertiesList();
		resultCollector = new ResultCollector();
	}
	public ResultCollector getTasksResult() throws Exception {
		prepareTasks();
		return resultCollector;
	}

	private void prepareTasks() throws Exception {
		Iterator<Property> iter = properties.iterator();
		while (iter.hasNext()) {
			Property jp = iter.next();
			boolean status = checkGrid(jp.getUrl());
			String name = "GRID - " + jp.getUrl();
			Long timestamp = new Date().getTime();
			resultCollector.addResult(new TaskResult(timestamp, name, status ? "SUCCESS" : "FAILED"));
		}
	}

	private boolean checkGrid(String adress) throws Exception {
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
