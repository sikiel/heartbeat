package com.capgemini.heartbeat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	public String nodeConnectionTest(GridProperty property, GridNode node) throws MalformedURLException, WebDriverException {
		DesiredCapabilities browser = setNodeCapabilities(node);
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
		String result = testResult(driver, loadedUrl);
		driver.quit();
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
