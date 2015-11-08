package com.capgemini.heartbeat;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebDriverException;

public class NodeCheck implements Callable<String> {

	private GridProperty hub;
	private GridConnectionTester connection;
	private ResultCollector resultCollector;
	private GridNode node;

	public NodeCheck(GridNode node, GridProperty hub, GridConnectionTester connection,
			ResultCollector resultCollector) {
		this.node = node;
		this.hub = hub;
		this.connection = connection;
		this.resultCollector = resultCollector;
	}

	@Override
	public String call() throws Exception {

		String nodename = "SELENIUM Node - " + hub.getUrl() + " " + node.getBrowser() + " v."
				+ node.getBrowserVersion();
		Long timestamp;
		String result;
		HeartbeatFlow.log.info("Test " + nodename);
		try {

			result = connection.nodeConnectionTest(hub, node);
			timestamp = new Date().getTime();

		} catch (WebDriverException e) {
			timestamp = new Date().getTime();
			result = "TEST FAILED with " + e.getClass().getName();
			HeartbeatFlow.log.severe("Node TEST FAILED with " + e.getClass().getName());

		} catch (MalformedURLException e) {
			timestamp = new Date().getTime();
			result = "TEST FAILED with " + e.getClass().getName();
			HeartbeatFlow.log.severe("Node TEST FAILED with " + e.getClass().getName());
		}
		resultCollector.addResult(new TaskResult(timestamp, nodename, result));
		return "done";
	}

}
