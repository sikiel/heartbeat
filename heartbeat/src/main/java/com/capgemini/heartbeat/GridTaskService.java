package com.capgemini.heartbeat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class GridTaskService implements TaskService {
	private static final Logger log = Logger.getLogger( GridTaskService.class.getName());
	List<Property> properties;
	private ResultCollector resultCollector;

	public GridTaskService(TaskProperties gridPrperties) {
		this.properties = gridPrperties.getPropertiesList();
		resultCollector = new ResultCollector();
	}

	public ResultCollector getTasksResult() {
		prepareTasks();
		return resultCollector;
	}

	private void prepareTasks() {
		log.info("Checking Selenium Grid servers connections");
		Iterator<Property> iter = properties.iterator();
		while (iter.hasNext()) {
			Property jp = iter.next();
			boolean status = false;
			try {
				status = checkGrid(jp.getUrl());
			} catch (IOException e) {
				e.printStackTrace();
			}
			String name = "GRID - " + jp.getUrl();
			Long timestamp = new Date().getTime();
			resultCollector.addResult(new TaskResult(timestamp, name, status ? "SUCCESS" : "FAILED"));
		}
	}

	private boolean checkGrid(String adress) throws IOException {
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
