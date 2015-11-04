package com.capgemini.heartbeat;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BackupFileTest {

	private static final String BACKUP_PATH = "./backup.csv";
	private static final String TEST_PATH = "./test.csv";
	private static final TaskResult TASK_RESULT = new TaskResult(1111L, "first", "test");
	private static final TaskResult FIRST_TASK_RESULT = new TaskResult(1111L, "first", "test");
	private static final TaskResult SECOND_TASK_RESULT = new TaskResult(1111L, "second", "test");
	private BackupFile instance;
	private String expectedResult;
	private String result;
	private File testFile;

	@Before
	public void setUp() throws Exception {
		tryToDeleteTestingFiles();
		this.instance = new BackupFile(BACKUP_PATH);
	}

	private void tryToDeleteTestingFiles() {
		new File(BACKUP_PATH).delete();
		new File(TEST_PATH).delete();

	}

	@After
	public void tearDown() throws Exception {
		this.instance.closeBackupFile();
	}

	@Test(expected = IOException.class)
	public void testThatFileIsLockedWhenBackupIsCreated() throws IOException {
		// given

		// when
		writeToBackup();

		// then
	}

	@Test
	public void testUpdateDataFromBackup() {
		// given
		createFileToCopy(TEST_PATH);

		// when
		writeStuffToBackup(TASK_RESULT);
		copyBackupTo(TEST_PATH);

		// then
		assertThatStuffHasBeenCopied(TASK_RESULT);
	}

	@Test
	public void testFlushDataFromBackup() {
		// given

		// when
		writeStuffToBackup(TASK_RESULT);
		flushBackup();

		// then
		assertThatBackupIsEmpty();

	}

	@Test
	public void testTwiceUpdateDataFromBackup() {
		// given
		createFileToCopy(TEST_PATH);

		// when
		writeStuffToBackup(FIRST_TASK_RESULT);
		copyBackupTo(TEST_PATH);
		flushBackup();

		writeStuffToBackup(SECOND_TASK_RESULT);
		copyBackupTo(TEST_PATH);

		// then
		assertTestFileHasStuffCount(2);
		assertThatStuffHasBeenCopiedIn(FIRST_TASK_RESULT, 1);
		assertThatStuffHasBeenCopiedIn(SECOND_TASK_RESULT, 2);

	}

	private void flushBackup() {
		instance.flush();
	}

	private void createFileToCopy(String testPath) {
		try {
			this.testFile = new File("./test.csv");
			if (!testFile.exists()) {
				testFile.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void copyBackupTo(String path) {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(testFile, "rw");
			FileChannel fileChannel = randomAccessFile.getChannel();
			instance.updateTo(fileChannel);
			randomAccessFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeStuffToBackup(TaskResult result) {
		List<TaskResult> toWrite = new ArrayList<>();
		toWrite.add(result);
		instance.wirte(toWrite);
	}

	private void writeToBackup() throws IOException {
		File file = new File(BACKUP_PATH);
		FileWriter writer = new FileWriter(file);
		writer.append("test");
		writer.close();
	}

	// ASSERTIONS

	private void assertThatStuffHasBeenCopied(TaskResult expextedResult) {

	}

	private void assertThatStuffHasBeenCopiedIn(TaskResult firstTaskResult, int index) {
		try {
			for (int i = 0; i < index; i++) {
				if (i == index) {
					BufferedReader reader = new BufferedReader(new FileReader(testFile));
					result = reader.readLine();
					reader.close();
					assertEquals(expectedResult.toString(), result + System.getProperty("line.separator"));
					return;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void assertTestFileHasStuffCount(int linesNumber) {
		int linesCount = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(testFile));
			while (reader.readLine() != null) {
				linesCount++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(linesNumber, linesCount);

	}

	private void assertThatBackupIsEmpty() {
		File file = new File(BACKUP_PATH);
		assertEquals(file.length(), 0L);
	}
}
