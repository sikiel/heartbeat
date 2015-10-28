package com.capgemini.heartbeat;

/**
 * Hello world!
 *
 */
public class HeartbeatFlow 
{
    public static void main( String[] args )
    {
    	TaskProperties jenkinsPrperties = new JenkinsProperties();
    	TaskProperties gridPrperties = new GridProperties();
    	
    	TaskService jenkinsService = new JenkinsTaskService(jenkinsPrperties);
    	TaskService gridService = new GridTaskFactory(gridPrperties);
    	
    	
    	ResultCollector resultCollector = new ResultCollector();
    	
    	resultCollector.addResults(jenkinsService.getTasksResult());
    	resultCollector.addResults(gridService.getTasksResult());
    	
    	CSVReportCreator csvReportCreator = new CSVReportCreator();
    	csvReportCreator.createReport(resultCollector.getResults());
    	
    }
}
