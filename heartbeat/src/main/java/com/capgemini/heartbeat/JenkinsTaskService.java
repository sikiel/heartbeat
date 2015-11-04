package com.capgemini.heartbeat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.CharacterData;

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
		try {
			checkBuilds();
		} catch (IOException | SAXException | ParserConfigurationException e) {
			LOG.severe("Build check failed");
		}
		prepareTasks();
		return resultCollector;
	}

	private void checkBuilds()throws IOException, SAXException, ParserConfigurationException {
		Iterator<JenkinsBuild> it = JenkinsBuildList.getBuildList().iterator();
		while (it.hasNext()) {

			JenkinsBuild jb = (JenkinsBuild) it.next();
			URL url = new URL(jb.getJenkinsProperty().getUrl() + "/job/" + jb.getJenkinsProperty().getJobName()
					+ "/lastBuild/api/xml?tree=result,building");
			String userpass = jb.getJenkinsProperty().getUsername() + ":" + jb.getJenkinsProperty().getPassword();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
			conn.setRequestProperty("Authorization", basicAuth);
			conn.setRequestMethod("GET");
			LOG.info(conn.getResponseMessage());

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			String xml = "";
			while ((inputLine = in.readLine()) != null) {
				xml += inputLine;
			}

			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			org.w3c.dom.Document doc = db.parse(is);

			NodeList result = doc.getElementsByTagName("result");
			Element elementResult = (Element) result.item(0);
			NodeList building = doc.getElementsByTagName("building");
			Element elementBuilding = (Element) building.item(0);

			if (getCharsFromElement(elementBuilding) == "true") {
				String name = "JENKINS - " + jb.getJenkinsProperty().getUrl();
				Long timestamp = new Date().getTime();
				resultCollector.addResult(new TaskResult(timestamp, name, "IN PROGRESS"));
				Long timeout = timestamp - jb.getTimestamp();
				if (timeout > 900000) {
					buildList.delete(jb);
				}
			} else {
				String name = "JENKINS - " + jb.getJenkinsProperty().getUrl();
				Long timestamp = new Date().getTime();
				resultCollector.addResult(new TaskResult(timestamp, name, getCharsFromElement(elementResult)));
				buildList.delete(jb);
			}

		}
	}
		
	

	private void prepareTasks() {
		Iterator<Property> iter = properties.iterator();
		LOG.info("Checking Jenkins servers connections");
		while (iter.hasNext()) {
			JenkinsProperty jp = (JenkinsProperty) iter.next();
			String name = "JENKINS - " + jp.getUrl();
			Long timestamp = new Date().getTime();

			try {
				if (!buildJenkinsJob(jp)) {
					resultCollector.addResult(new TaskResult(timestamp, name, "FAILED"));
				}
			} catch (IOException e) {
				LOG.severe("The URL: " + jp.getUrl() + " is not valid.");
				resultCollector.addResult(new TaskResult(timestamp, name, "URL NOT VALID"));
			}
		}
	}

	private boolean buildJenkinsJob(JenkinsProperty jp) throws IOException {
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
	
	private static String getCharsFromElement(Element e) {

		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";

	}

	/*
	 * private boolean checkJenkins(String adress) throws IOException { URL url
	 * = new URL(adress); HttpURLConnection conn = (HttpURLConnection)
	 * url.openConnection(); conn.setRequestMethod("GET"); if
	 * (conn.getResponseCode() == 200) { return true; } else { return false; } }
	 */
}
