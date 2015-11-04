package com.capgemini.heartbeat;

public class BackupFileNotExists extends RuntimeException {

	public BackupFileNotExists() {
		super("Backup file is not created!");
	}

}
