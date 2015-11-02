package com.capgemini.heartbeat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GridTaskService implements TaskService {
	private static final Logger log = Logger.getLogger(GridTaskService.class.getName());
	private static String TARGET_SERVER_URL = "http://www.google.pl";
	private static Integer SERVER_OK = 200;
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
			Property property = iter.next();
			boolean status = false;
			try {
				status = checkGrid(property);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String name = "SELENIUM HUB - " + property.getUrl();
			Long timestamp = new Date().getTime();
			resultCollector.addResult(new TaskResult(timestamp, name, status ? "PING SUCCESS" : "PING FAILED"));
		}
	}

	private boolean checkGrid(Property property) throws IOException {
		URL url = new URL(property.getUrl());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == SERVER_OK) {
			checkNodes((GridProperty) property);
			return true;
		} else {
			return false;
		}
	}

	private void checkNodes(GridProperty property) {

		for (GridNode node : property.getNodesList()) {
			try{
			DesiredCapabilities browser = new DesiredCapabilities();
			browser.setBrowserName(node.getBrowser());
			//browser.setCapability("version",9);
			browser.setPlatform(node.getPlatform());
			testCodesCrud(browser, property);
			}catch(WebDriverException e){
				e.printStackTrace();
			}
		}

	}

	private void testCodesCrud(Capabilities browser, GridProperty property) {
		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(new URL(property.getUrl() + "/wd/hub"), browser);
			// // test starts in Codes entity list page
			driver.get(TARGET_SERVER_URL);
			driver.findElement(By.name("q")).sendKeys("selenium");
			driver.findElement(By.xpath("//*[@id='tsf']/div[2]/div[3]/center/input[1]")).click();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				String result;
				if(driver.getCurrentUrl().equals(TARGET_SERVER_URL+"/#q=selenium")) result="TEST SUCCESS";
				else result="TEST FAILED";
				String name = "SELENIUM Node ";
				Long timestamp = new Date().getTime();
				resultCollector.addResult(new TaskResult(timestamp, name, result));
				driver.quit();
			}
		}
	}

}
