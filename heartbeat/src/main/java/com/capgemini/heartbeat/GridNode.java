package com.capgemini.heartbeat;

import org.openqa.selenium.Platform;

public class GridNode {
private String browser, browserVersion, platform;

public String getBrowser() {
	return browser;
}

public void setBrowser(String browser) {
	this.browser = browser;
}

public String getBrowserVersion() {
	return browserVersion;
}

public void setBrowserVersion(String browserVersion) {
	this.browserVersion = browserVersion;
}

public Platform getPlatform() {
	if(platform.equalsIgnoreCase("WINDOWS")){
		return Platform.WINDOWS;
	}else if(platform.equalsIgnoreCase("LINUX")){
		return Platform.LINUX;
	}else if(platform.equalsIgnoreCase("UNIX")){
		return Platform.UNIX;
	}else if(platform.equalsIgnoreCase("MAC")){
		return Platform.MAC;
	}else{
		return Platform.ANY;
	}
}

public void setPlatform(String platform) {
	this.platform = platform;
}

}
