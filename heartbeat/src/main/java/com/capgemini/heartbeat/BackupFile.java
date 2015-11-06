package com.capgemini.heartbeat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.util.List;

public class BackupFile extends FileZZZ {

	private FileChannel fileChannel;
	private FileLock lock;
	private RandomAccessFile randomAccessFile;
	private File file;

	public BackupFile(String backupPath) {
		createBackupFile(backupPath);
	}

	@Override
	public void wirte(List<TaskResult> results) {
		try {
			this.lock.release();
			FileWriter writer = new FileWriter(file, true);
			for (TaskResult result : results) {
				String currentResult = result.toString();
				writer.append(currentResult);
			}
			writer.close();
			this.fileChannel = randomAccessFile.getChannel();
			this.lock = fileChannel.lock();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void flush() {
		try {
			lock.release();
			fileChannel.truncate(0L);
			this.lock = fileChannel.lock();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void closeBackupFile() {
		try {
			this.lock.release();
			this.fileChannel.close();
			this.randomAccessFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateTo(FileChannel target) {
		try {
			fileChannel.transferTo(0, fileChannel.size(), target.position(target.size()));
			target.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createBackupFile(String path) {
		Paths.get(path);
		try {
			this.file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			this.randomAccessFile = new RandomAccessFile(file, "rw");
			this.fileChannel = randomAccessFile.getChannel();
			this.lock = fileChannel.lock();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}