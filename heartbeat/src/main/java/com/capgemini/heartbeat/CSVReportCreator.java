package com.capgemini.heartbeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

public class CSVReportCreator {

	private static final String BACKUP_PATH = "./.$private";
	public String pathWithCsvFile = "C:\\heartbeatReport\\heartbeatReport.csv";
	private BackupFile backup;

	public CSVReportCreator() {

	};

	public CSVReportCreator(String path) {
		this.pathWithCsvFile = path;
	};

	public void createBackupFile() {
		this.backup = new BackupFile(BACKUP_PATH);
	}

	public void closeBackupFile() {
		this.backup.closeBackupFile();
	}

	public void updateReportWith(List<TaskResult> results) {

		System.out.println("Results size: " + results.size());
		ReportFile report = getReportFile();

		checkIfBackupExists();

		if (isUsed(report)) {
			write(results, backup);
			return;
		}

		update(report, backup);
		write(results, report);
		return;

	}

	private void checkIfBackupExists() {
		if (backup == null) {
			throw new BackupFileNotExists();
		}
	}

	private void flush(BackupFile file) {
		file.flush();
	}

	private void update(ReportFile report, BackupFile backup) {
		if (isNotEmpty(backup)) {
			report.update(backup);
		}
		flush(backup);
		return;
	}

	private boolean isNotEmpty(FileZZZ toCheck) {
		return toCheck.isEmpty();
	}

	private void write(List<TaskResult> results, FileZZZ destinantionFile) {
		destinantionFile.wirte(results);
	}

	private boolean isUsed(ReportFile file) {
		return file.isUsed();
	}

	private ReportFile getReportFile() {
		return new ReportFile(pathWithCsvFile);
	}

}
