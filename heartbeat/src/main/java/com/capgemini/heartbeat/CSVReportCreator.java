package com.capgemini.heartbeat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReportCreator {

	private static final String separator = ";";
	private static final String endOfLine = "\n";
	private static final String fileHeader = "timestamp; Jenkins|Grid; Status ";
	public String pathWithCsvFile = "C:\\heartbeatReport\\heartbeatReport.csv";
	private boolean needsHeader = false;

	public CSVReportCreator() {

	};

	public CSVReportCreator(String path) {
		this.pathWithCsvFile = path;
	};

	private void fillTheCsvFile(List<TaskResult> list, FileWriter writeToCsv) throws IOException {
		if (needsHeader) {
			writeToCsv.append(fileHeader);
			writeToCsv.append(endOfLine);
		}
		for (int i = 0; i < list.size(); i++) {
			writeToCsv.append(list.get(i).getTimestamp().toString());
			writeToCsv.append(separator);
			writeToCsv.append(list.get(i).getName());
			writeToCsv.append(separator);
			writeToCsv.append(list.get(i).getStatus());
			writeToCsv.append(endOfLine);
		}
	}

	private FileWriter createCsvFileWriter() throws IOException {
		File file = new File(this.pathWithCsvFile);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
			needsHeader = true;
		}
		return new FileWriter(file.getAbsolutePath(), true);
	}

	private void closeCsvFile(FileWriter writeToCsv) throws IOException{
		if (writeToCsv != null) {
			writeToCsv.flush();
			writeToCsv.close();
			System.out.println("CSV file updated!");
		}
	}

	public void createReport(List<TaskResult> list) {
		try {
			FileWriter csvWriter = createCsvFileWriter();
			fillTheCsvFile(list, csvWriter);
			closeCsvFile(csvWriter);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
