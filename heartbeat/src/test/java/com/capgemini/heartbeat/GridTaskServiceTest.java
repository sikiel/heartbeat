package com.capgemini.heartbeat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class GridTaskServiceTest {
	TaskProperties gridProperties;
	GridTaskService gridTaskService;

	@Test
	public void testJenkinsTaskService() {
		gridProperties = new GridProperties("./src/test/resources/gridTaskTest.json");
		gridTaskService = new GridTaskService(gridProperties);
		assertTrue(gridTaskService != null);
	}

	@Test
	public void testGetTasksResult() throws Exception {
		gridProperties = new GridProperties("./src/test/resources/gridTaskTest.json");
		gridTaskService = new GridTaskService(gridProperties);
		ResultCollector rc = new ResultCollector();
		rc.addResults(gridTaskService.getTasksResult());
		List<TaskResult> results = rc.getTasksResult();
		assertTrue(results.size() == 2);
	}

	@Test
	public void testWithMockedConnection() {
		gridProperties = new GridProperties("./src/test/resources/gridTaskTest.json");
		GridConnectionTesterMock connectionMock = new GridConnectionTesterMock();
		connectionMock.hubStatus = true;
		connectionMock.nodeTestResult = "TEST PASS";
		GridTaskService mockedGridTaskService = new GridTaskServiceMock(gridProperties, connectionMock);
		ResultCollector rc = new ResultCollector();
		rc.addResults(mockedGridTaskService.getTasksResult());
		//Compare produced tasks with equivalent properties
		assertEquals(4, rc.getTasksResult().size());
		// check if the names in tasks are consistent with names in properties
		assertEquals(rc.getTasksResult().get(0).getName(),
				"SELENIUM HUB - " + gridProperties.getPropertiesList().get(0).getUrl());
		List<GridNode> nodesList = ((GridProperty) gridProperties.getPropertiesList().get(0)).getNodesList();
		assertEquals(2, nodesList.size());
		for(int i=0; i<nodesList.size(); i++){
		assertEquals(rc.getTasksResult().get(i+1).getName(),
				"SELENIUM Node - " + gridProperties.getPropertiesList().get(0).getUrl() + " "
						+ nodesList.get(i).getBrowser() + " v." + nodesList.get(i).getBrowserVersion());
		}

	}
}
