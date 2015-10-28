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
    	
    	TaskFactory jenkinsFactory = new TaskFactory(jenkinsPrperties);
    	TaskFactory gridFactory = new TaskFactory(gridPrperties);
    	
    	ResultCollector resultCollector = new ResultCollector();
    	
    	resultCollector.addResults(jenkinsFactory.getTasksResult());
    	resultCollector.addResults(gridFactory.getTasksResult());
    	
    	CSVReportCreator csvReportCreator = new CSVReportCreator();
    	csvReportCreator.createReport(resultCollector.getResults());
    	
    	
    	
    }
}
