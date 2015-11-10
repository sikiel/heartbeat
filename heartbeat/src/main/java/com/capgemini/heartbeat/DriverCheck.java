package com.capgemini.heartbeat;

import java.net.URL;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverCheck implements Callable<WebDriver>{

	private String testServerUrl;
	private GridProperty property;
	private DesiredCapabilities browser;

	public DriverCheck(String url, GridProperty property, DesiredCapabilities browser) {
		this.testServerUrl = url;
		this.property = property;
		this.browser = browser;
	}
	@Override
	public WebDriver call() throws Exception {
		WebDriver driver = new RemoteWebDriver(new URL(property.getUrl() + "/wd/hub"), browser);
		driver.get(testServerUrl);

		return driver;
	}
}
