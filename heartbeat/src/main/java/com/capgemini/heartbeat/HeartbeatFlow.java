package com.capgemini.heartbeat;

/**
 * Hello world!
 *
 */
public class HeartbeatFlow {
	public static void main(String[] args) throws Exception {
		HeartbeatProperties hp = new HeartbeatPropertiesManager("./src/main/resources/heartbeat.properties")
				.getProperties();
		TaskProperties jenkinsPrperties = new JenkinsProperties(hp.getJenkinsPropertiesPath());
		TaskProperties gridPrperties = new GridProperties(hp.getGridPrpertiesPath());

		TaskService jenkinsService = new JenkinsTaskService(jenkinsPrperties);
		TaskService gridService = new GridTaskService(gridPrperties);

		ResultCollector resultCollector = new ResultCollector();

		resultCollector.addResults(jenkinsService.getTasksResult());
		resultCollector.addResults(gridService.getTasksResult());

		CSVReportCreator csvReportCreator = new CSVReportCreator();
		csvReportCreator.createReport(resultCollector.getTasksResult());

	}
}
