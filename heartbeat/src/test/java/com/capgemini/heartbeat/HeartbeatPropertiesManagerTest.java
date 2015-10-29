package com.capgemini.heartbeat;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HeartbeatPropertiesManagerTest {

	private static final String CORRECT_PATH = "./src/test/resources/test.heartbeat.properties";
	private static final String INCORRECT_PATH = "fake.heartbeat.properties";
	private static final String PATH_TO_JENKINS = "path/to/jenkins";
	private static final String PATH_TO_GRID = "path/to/grid";
	private static final String PATH_TO_REPORT = "path/to/report";
	private static final String NULL_PATH = "nullPath";

	private HeartbeatPropertiesManager instance;
	private HeartbeatProperties result;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testReadPropertyFromExistingFile() {
		// given
		createIstanceWith(CORRECT_PATH);

		// when
		readPropertiesFromFile();

		// then
		assertJenkinsPathIsCorrect();
		assertGridPathIsCorrect();
		assertCsvReportPathIsCorrect();
	}
	
	@Test
	public void testReadPropertyFromNotExistingFile() {
		//given
		createIstanceWith(INCORRECT_PATH);
		
		//when
		readPropertiesFromFile();
		
		//then
		assertJenkisPathIsNull();
		assertGridPaghIsNull();
		assertCsvReportPathIsNull();
	}


	private void createIstanceWith(String path) {
		instance = new HeartbeatPropertiesManager(path);
	}

	private void readPropertiesFromFile() {
		result = instance.getProperties();
	}
	
	//assertions

	private void assertCsvReportPathIsCorrect() {
		assertEquals(result.getCsvReportPath(), PATH_TO_REPORT);
	}

	private void assertGridPathIsCorrect() {
		assertEquals(result.getGridPrpertiesPath(), PATH_TO_GRID);
	}

	private void assertJenkinsPathIsCorrect() {
		assertEquals(result.getJsonPropertiesPath(), PATH_TO_JENKINS);
	}

	private void assertCsvReportPathIsNull() {
		assertEquals(result.getCsvReportPath(), NULL_PATH);
	}

	private void assertGridPaghIsNull() {
		assertEquals(result.getGridPrpertiesPath(), NULL_PATH);
	}

	private void assertJenkisPathIsNull() {
		assertEquals(result.getJsonPropertiesPath(), NULL_PATH);
	}
}
