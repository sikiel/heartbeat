package com.capgemini.heartbeat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HeartbeatPropertiesManager {

	private String pathToProperties;

	public HeartbeatPropertiesManager(String pathToProperties) {
		this.pathToProperties = pathToProperties;
	}

	public HeartbeatProperties getProperties() {

		HeartbeatProperties heartbeatProperties = readPropertiesAndLoad();

		return heartbeatProperties;
	}

	private HeartbeatProperties readPropertiesAndLoad() {
		Properties prop = new Properties();
		InputStream input = null;
		HeartbeatProperties heartbeatProperties = HeartbeatProperties.NULL_PROPERTY;

		try {
			input = new FileInputStream(pathToProperties);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			String pathToJenkinsConfig = prop.getProperty("jenkins.config");
			String pathToGridConfig = prop.getProperty("grid.config");
			String pathToCsvReport = prop.getProperty("csv.report");
			Integer delayHours = Integer.parseInt(prop.getProperty("delay.hours"));
			Integer delayMinutes = Integer.parseInt(prop.getProperty("delay.minutes"));
			Integer delaySeconds = Integer.parseInt(prop.getProperty("delay.seconds"));
			Integer createReport = Integer.parseInt(prop.getProperty("report.create"));
			Integer deleteReport = Integer.parseInt(prop.getProperty("report.delete"));
			String pathToOld = prop.getProperty("report.path");
			heartbeatProperties = new HeartbeatProperties(pathToJenkinsConfig, pathToGridConfig, pathToCsvReport,
					delayHours, delayMinutes, delaySeconds);
			heartbeatProperties.setReportProperties(createReport, deleteReport, pathToOld);

		} catch (FileNotFoundException fex) {

		} catch (NumberFormatException nex) {
			HeartbeatFlow.log.severe("Can't parse properties. Check the heartbeat.properties file.");
		} catch (IOException ex) {
			HeartbeatFlow.log.severe("Can't load a properties file. Check the heartbeat.properties file.");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
		;

		return heartbeatProperties;

	}

}
