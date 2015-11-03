package com.capgemini.heartbeat;

import org.junit.Test;
import org.junit.Assert;
import org.junit.*;

public class ResultCollectorTest {

	private static final String TWO = "two";
	private static final String ONE = "one";
	private static final String THREE = "three";
	private ResultCollector results = new ResultCollector();
	private ResultCollector instance;

	@Before
	public void setUp() {
		this.instance = new ResultCollector();
	}

	@Test
	public void testCollectingStandAloneTaskResults() {
		// given

		// when
		addNewTaskResultToInstance(createTaskResultWithName(ONE));
		addNewTaskResultToInstance(createTaskResultWithName(TWO));

		// then
		assertTaskResultIsAdded(ONE);
		assertTaskResultIsAdded(TWO);
	}

	@Test
	public void testCollectingTaskAsAnotherResultCollector() {
		// given
		createAnotherResultCollectorInstance();
		addTaskToResults(createTaskResultWithName(ONE));
		addTaskToResults(createTaskResultWithName(TWO));

		// when
		addNewResultsToInstance(results);

		// then
		assertTaskResultIsAdded(ONE);
		assertTaskResultIsAdded(TWO);
	}

	@Test
	public void testCollectingStandAloneTaskResultAndResultCollector() {
		// given
		createAnotherResultCollectorInstance();
		addTaskToResults(createTaskResultWithName(ONE));
		addTaskToResults(createTaskResultWithName(TWO));

		// when
		addTaskToResults(createTaskResultWithName(THREE));
		addNewResultsToInstance(results);

		// then
		assertTaskResultIsAdded(ONE);
		assertTaskResultIsAdded(TWO);
		assertTaskResultIsAdded(THREE);

	}

	private void createAnotherResultCollectorInstance() {
		results = new ResultCollector();
	}

	private void addNewResultsToInstance(ResultCollector results) {
		instance.addResults(results);
	}

	private void addTaskToResults(TaskResult taskResult) {
		results.addResult(taskResult);

	}

	private TaskResult createTaskResultWithName(String name) {
		return new MockTask(1L, name, "success");
	}

	private void addNewTaskResultToInstance(TaskResult task) {
		instance.addResult(task);
	}

	// assertions

	private void assertTaskResultIsAdded(String taskName) {
		boolean isPresent = false;
		for (TaskResult result : instance.getTasksResult()) {
			if (result.getName().equals(taskName)) {
				isPresent = true;
				break;
			}
		}
		Assert.assertTrue(isPresent);
	}

	private class MockTask extends TaskResult {

		public MockTask(Long timestamp, String name, String status) {
			super(timestamp, name, status);
		}

	}

}
