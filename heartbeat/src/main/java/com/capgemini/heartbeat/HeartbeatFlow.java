package com.capgemini.heartbeat;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class HeartbeatFlow {
	private static void delayNextCheck(HeartbeatProperties hp){
		try {
		    TimeUnit.SECONDS.sleep(hp.getTime().Seconds);
		    TimeUnit.MINUTES.sleep(hp.getTime().Minutes);
		    TimeUnit.HOURS.sleep(hp.getTime().Hours);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		while(true){
		HeartbeatProperties hp = new HeartbeatPropertiesManager("./heartbeat.properties")
				.getProperties();
		TaskProperties jenkinsPrperties = new JenkinsProperties(hp.getJenkinsPropertiesPath());
		TaskProperties gridPrperties = new GridProperties(hp.getGridPrpertiesPath());

		TaskService jenkinsService = new JenkinsTaskService(jenkinsPrperties);
		TaskService gridService = new GridTaskService(gridPrperties);

		ResultCollector resultCollector = new ResultCollector();

		resultCollector.addResults(jenkinsService.getTasksResult());
		resultCollector.addResults(gridService.getTasksResult());

		CSVReportCreator csvReportCreator = new CSVReportCreator(hp.getCsvReportPath());
		csvReportCreator.createReport(resultCollector.getTasksResult());
		
		delayNextCheck(hp);
		}

	}
}
