package com.capgemini.heartbeat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.Logger;

import com.capgemini.heartbeat.HeartbeatProperties.ReportProperties;

public class ReportManager {

	Logger LOG = HeartbeatFlow.log;

	private int createReportDelay; // days
	private int deleteReportDelay; // days
	private String pathToOld;
	private Date lastReportCreate;

	public ReportManager(ReportProperties rp) {
		this.createReportDelay = rp.CREATE; // * DAY_MULTIPLIER;
		this.deleteReportDelay = rp.DELETE; // * DAY_MULTIPLIER;
		this.pathToOld = rp.PATH_TO_OLD;
	}

	public void checkReports(ReportFile reportFile) {
		if (reportFile.fileExists() && !reportFile.isUsed()) {

			if (lastReportCreate != null) {
				File file = reportFile.getFile();
				Date fileDate = new Date(file.lastModified());
				int diference = getDayOfTheYear(fileDate) - getDayOfTheYear(lastReportCreate);
				if (diference >= createReportDelay) {
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
			File dirs = new File(pathToOld);
			dirs.mkdirs();
			deleteOldReports(dirs);
		} catch (IOException e) {
			LOG.error("Report manager FAILED with " + e.getClass().getName());
		}
	}

	private void deleteOldReports(final File dir) throws IOException {
		for (final File fileEntry : dir.listFiles()) {
			if (fileEntry.isDirectory()) {
				deleteOldReports(fileEntry);
			} else {
				long difference = getDayOfTheYear(new Date()) - getDayOfTheYear(new Date(fileEntry.lastModified()));
				if (difference >= this.deleteReportDelay) {
					LOG.info("Removing outdated report file: " + fileEntry.getAbsolutePath());
					fileEntry.delete();
				}
			}
		}
	}

	private String dateToString(Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormatter.format(date);
	}

	private int getDayOfTheYear(Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("DD");
		return Integer.parseInt(dateFormatter.format(date));
	}

}
