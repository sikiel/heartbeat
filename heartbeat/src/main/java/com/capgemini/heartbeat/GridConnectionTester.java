package com.capgemini.heartbeat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

public class GridConnectionTester {

	private static String TEST_SERVER_URL = "http://www.google.pl";
	private static Integer SERVER_OK = 200;

	public boolean hubConnectionTest(Property property) throws IOException {
		HeartbeatFlow.log.info("Ping server " + property.getUrl());
		URL url = new URL(property.getUrl());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == SERVER_OK)
			return true;
		else
			return false;

	}

	public String nodeConnectionTest(GridProperty property, GridNode node) {
		DesiredCapabilities browser = setNodeCapabilities(node);
		String result;
		// -- test Selenium Node
		// try connecting to google and searching for "selenium"
		ExecutorService service = Executors.newFixedThreadPool(1);
		DriverCheck driverCheck = new DriverCheck(TEST_SERVER_URL, property, browser);
		Future<WebDriver> futureResult = service.submit(driverCheck);
		try {
			// set thread timeout
			WebDriver driver = futureResult.get(10, TimeUnit.SECONDS);
			String loadedUrl = driver.getCurrentUrl();

			driver.findElement(By.name("q")).sendKeys("selenium");
			driver.findElement(By.xpath("//*[@id='sblsbb']/button")).click();
			// verify if the url has changed after search
			result = testResult(driver, loadedUrl);
			driver.quit();
		} catch (Exception e) {
			futureResult.cancel(true);
			HeartbeatFlow.log.error("Node TEST FAILED with " + e.getClass().getName() + "message: " + e.getMessage());
			result = "TEST FAILED with " + e.getClass().toString();
		}
		service.shutdown();

		return result;
	}

	private DesiredCapabilities setNodeCapabilities(GridNode node) {
		DesiredCapabilities browser = new DesiredCapabilities();
		browser.setBrowserName(node.getBrowser());
		if (!node.getBrowserVersion().equals(JsonFileConverter.DEFAULT_PROPERTY_VALUE)) {
			browser.setVersion(node.getBrowserVersion());
		}
		browser.setPlatform(node.getPlatform());
		return browser;
	}

	private String testResult(WebDriver driver, String loadedUrl) {
		if (driver.getCurrentUrl().equals(loadedUrl + "#q=selenium"))
			return "TEST SUCCESS";
		else
			return "TEST FAILED";
	}

}
