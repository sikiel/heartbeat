package com.capgemini.heartbeat;

import java.util.ArrayList;
import java.util.List;

public class ResultCollector {

	private List<TaskResult> results;

	public ResultCollector() {
		this.results = new ArrayList<>();
	}

	public List<TaskResult> getTasksResult() {
		return this.results;
	}

	public void addResults(ResultCollector tasksResult) {
		for (TaskResult result : tasksResult.getTasksResult()) {
			this.results.add(result);
		}
	}

	public void addResult(TaskResult tasksResult) {
		results.add(tasksResult);
	}

	public void flush() {
		if (!this.results.isEmpty())
			this.results = new ArrayList<>();
	}
}
