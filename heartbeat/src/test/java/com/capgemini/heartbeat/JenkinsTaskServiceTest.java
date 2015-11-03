package com.capgemini.heartbeat;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JenkinsTaskServiceTest {

	TaskProperties jp;
	JenkinsTaskService jts;

	@Before
	public void before() {
		jp = new JenkinsProperties("./src/test/resources/jenkinsTest.json");
		jts = new JenkinsTaskService(jp);
	}

	@Test
	public void testJenkinsTaskService() {
		assertEquals(jts != null, true);
	}

	@Test
	public void testGetTasksResult() throws Exception {
//		ResultCollector rc = new ResultCollector();
//		rc.addResults(jts.getTasksResult());
//		List<TaskResult> results = rc.getTasksResult();
//		assertTrue(results.size() == 1);
	}
}
