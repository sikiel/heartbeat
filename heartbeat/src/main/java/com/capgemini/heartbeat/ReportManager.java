package com.capgemini.heartbeat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Date;

import org.apache.logging.log4j.Logger;

import com.capgemini.heartbeat.HeartbeatProperties.ReportProperties;

public class ReportManager {

	Logger LOG = HeartbeatFlow.log;

	public static final int DAY_MULTIPLIER = 86400000;
	private long createReportDelay; // miliseconds
	private int deleteReportDelay; // days
	private String pathToOld;
	private Date lastReportCreate;

	public ReportManager(ReportProperties rp) {
		this.createReportDelay = rp.CREATE * DAY_MULTIPLIER;
		this.deleteReportDelay = rp.DELETE * DAY_MULTIPLIER;
		this.pathToOld = rp.PATH_TO_OLD;
	}

	public void checkReports(ReportFile reportFile) {
		if (reportFile.fileExists() && !reportFile.isUsed()) {

			if (lastReportCreate != null) {
				File file = reportFile.getFile();
				Date fileDate = new Date(file.lastModified());
				long diference = fileDate.getTime() - lastReportCreate.getTime();
				if (diference > createReportDelay) {
					try {
						String fileName = String.format("report_%s.csv", dateToString(fileDate));
						File target = new File(pathToOld + "\\" + fileName);
						LOG.info("Creating of a new report file: " + target.getAbsolutePath());
						RandomAccessFile rafSource = new RandomAccessFile(file, "rw");
						RandomAccessFile rafTarget = new RandomAccessFile(target, "rw");
						FileChannel fcSource = rafSource.getChannel();
						FileChannel fcTarget = rafTarget.getChannel();
						fcSource.transferTo(0, fcSource.size(), fcTarget.position(0));
						target.setReadOnly();
						fcSource.truncate(0L);
						fcSource.close();
						fcTarget.close();
						rafSource.close();
						rafTarget.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lastReportCreate = new Date();
				}
			} else {
				lastReportCreate = new Date();
			}
		}
		try {
			deleteOldReports(new File(pathToOld));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteOldReports(final File dir) throws IOException {
		for (final File fileEntry : dir.listFiles()) {
			if (fileEntry.isDirectory()) {
				deleteOldReports(fileEntry);
			} else {
				long difference = new Date().getTime() - fileEntry.lastModified();
				if (difference > this.deleteReportDelay) {
					LOG.info("Removing outdated report file: " + fileEntry.getAbsolutePath());
					fileEntry.delete();
				}
			}
		}

		/*
		 * String command = String.format(
		 * "forfiles -p \"C:\\OLD\\\" -s -m *.* /D -%d /C \"cmd /c del @path\"",
		 * this.deleteReportDelay); System.out.println(command);
		 * Runtime.getRuntime().exec(command);
		 */
	}

	@SuppressWarnings("deprecation")
	private String dateToString(Date date) {
		String str = date.toLocaleString();
		String[] parts = str.split(" ");
		return parts[0];
	}

}
