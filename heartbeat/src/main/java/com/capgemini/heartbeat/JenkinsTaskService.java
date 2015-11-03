package com.capgemini.heartbeat;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.xml.sax.SAXException;

public class JenkinsTaskService implements TaskService {
	private static final Logger LOG = Logger.getLogger(JenkinsTaskService.class.getName());
	List<Property> properties;
	private ResultCollector resultCollector;
	private JenkinsBuildList buildList;

	public JenkinsTaskService(TaskProperties jenkinsPrperties) {
		this.properties = jenkinsPrperties.getPropertiesList();
		resultCollector = new ResultCollector();
		buildList = new JenkinsBuildList();
	}

	public ResultCollector getTasksResult() {
		prepareTasks();
		return resultCollector;
	}

	private void prepareTasks() {
		Iterator<Property> iter = properties.iterator();
		LOG.info("Checking Jenkins servers connections");
		while (iter.hasNext()) {
			JenkinsProperty jp = (JenkinsProperty) iter.next();

			try {
				if (!buildJenkinsJob(jp)) {
					String name = "JENKINS - " + jp.getUrl();
					Long timestamp = new Date().getTime();
					resultCollector.addResult(new TaskResult(timestamp, name, "FAILED"));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean buildJenkinsJob(JenkinsProperty jp) throws IOException, SAXException {
		URL url = new URL(jp.getUrl() + "/job/" + jp.getJobName() + "/build?token=build");
		String userpass = jp.getUsername() + ":" + jp.getPassword();

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
		conn.setRequestProperty("Authorization", basicAuth);
		conn.setRequestMethod("POST");

		if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
			buildList.add(new JenkinsBuild(jp, new Date().getTime()));
			return true;
		} else {
			return false;
		}
	}

	/*
	 * private boolean checkJenkins(String adress) throws IOException { URL url
	 * = new URL(adress); HttpURLConnection conn = (HttpURLConnection)
	 * url.openConnection(); conn.setRequestMethod("GET"); if
	 * (conn.getResponseCode() == 200) { return true; } else { return false; } }
	 */
}
