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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GridTaskService implements TaskService {
	private static final Logger log = Logger.getLogger(GridTaskService.class.getName());
	private String nodename = "";
	private static String TEST_SERVER_URL = "http://www.google.pl";
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
			nodename = "SELENIUM Node - " + property.getUrl() + " " + node.getBrowser() + " v."
					+ node.getBrowserVersion();
			try {

				test(property, node);

			} catch (WebDriverException e) {
				e.printStackTrace();
				Long timestamp = new Date().getTime();
				resultCollector.addResult(new TaskResult(timestamp, nodename,
						"TEST FAILED Couldn't find browser with specified capabilities"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Long timestamp = new Date().getTime();
				resultCollector.addResult(new TaskResult(timestamp, nodename, "TEST FAILED Problem with HUB URL"));
			}
		}

	}

	private void test(GridProperty property, GridNode node) throws MalformedURLException, WebDriverException {
		DesiredCapabilities browser = setCapabilities(node);
		// -- test Selenium Node
		// try connecting to google and searching for "selenium"
		WebDriver driver = new RemoteWebDriver(new URL(property.getUrl() + "/wd/hub"), browser);
		driver.get(TEST_SERVER_URL);
		String loadedUrl = driver.getCurrentUrl();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
		driver.findElement(By.name("q")).sendKeys("selenium");
		driver.findElement(By.xpath("//*[@id='sblsbb']/button")).click();
		// verify if the url has changed after search
		Long timestamp = new Date().getTime();
		resultCollector.addResult(new TaskResult(timestamp, nodename, verify(driver, loadedUrl)));
		driver.quit();

	}

	private DesiredCapabilities setCapabilities(GridNode node) {
		DesiredCapabilities browser = new DesiredCapabilities();
		browser.setBrowserName(node.getBrowser());
		browser.setVersion(node.getBrowserVersion());
		browser.setPlatform(node.getPlatform());
		return browser;
	}

	private String verify(WebDriver driver, String loadedUrl) {
		if (driver.getCurrentUrl().equals(loadedUrl + "#q=selenium"))
			return "TEST SUCCESS";
		else
			return "TEST FAILED";
	}

}
