package com.capgemini.heartbeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReportCreator {

	private static final String separator = ";";
	private static final String endOfLine = "\n";
	private static final String fileHeader = "timestamp; Jenkins|Grid; Status ";
	public String pathWithCsvFile = "C:\\heartbeatReport\\heartbeatReport.csv";
	File file = null;
	FileWriter writeToCsv = null;

	public CSVReportCreator() {

	};

	public CSVReportCreator(String path) {
		this.pathWithCsvFile = path;
	};

	public void createReport(Object results) {
		@SuppressWarnings("unchecked")
		ArrayList<TaskResult> listResults = (ArrayList<TaskResult>) results;
		try {
			file = new File(pathWithCsvFile);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
				writeToCsv = new FileWriter(file.getAbsolutePath(), true);
				writeToCsv.append(fileHeader);
				writeToCsv.append(endOfLine);
			}else{
				writeToCsv = new FileWriter(file.getAbsolutePath(), true);
			}

			for (int i = 0; i < listResults.size(); i++) {
				writeToCsv.append(listResults.get(i).getTimestamp().toString());
				writeToCsv.append(separator);
				writeToCsv.append(listResults.get(i).getName());
				writeToCsv.append(separator);
				writeToCsv.append(listResults.get(i).getStatus());
				writeToCsv.append(endOfLine);
			}
		} catch (IOException e) {
			System.out.println("File open error! ");
			e.printStackTrace();
		} finally {
			try {
				
				if (writeToCsv != null) {
					writeToCsv.flush();
					writeToCsv.close();
					System.out.println("CSV file updated!");
				}
			} catch (IOException e) {
				System.out.println("File flush or close error! ");
				e.printStackTrace();
			}
		}
	}
}
