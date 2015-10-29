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

			heartbeatProperties = new HeartbeatProperties(pathToJenkinsConfig, pathToGridConfig, pathToCsvReport);

		} catch (FileNotFoundException fex) {

		} catch (IOException ex) {
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
