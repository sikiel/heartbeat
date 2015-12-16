package com.capgemini.heartbeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.List;

public class ReportFile extends FileZZZ {

	static final String HEADER = "DATE;JENKINS | GRID;STATUS";
	static final String NEW_LINE = System.getProperty("line.separator");
	private File file;

	public ReportFile(String pathWithCsvFile) {
		creteFile(pathWithCsvFile);
	}

	private void creteFile(String path) {
		this.file = new File(path);
		if (!fileExists()) {
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.append(HEADER);
				writer.append(NEW_LINE);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isUsed() {
		if (fileExists()) {
			try {
				FileWriter writer = new FileWriter(file, true);
				writer.write("");
				writer.close();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return true;
			}
		}
		return false;
	}

	public boolean fileExists() {
		return file.exists();
	}

	public File getFile() {
		return file;
	}

	public void update(BackupFile backup) {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			FileChannel fileChannel = randomAccessFile.getChannel();
			backup.updateTo(fileChannel);
			randomAccessFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	void wirte(List<TaskResult> results) {
		try {
			FileWriter writer = new FileWriter(file, true);
			for (TaskResult result : results) {
				String currentResult = result.toString();
				writer.append(currentResult);
			}
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
