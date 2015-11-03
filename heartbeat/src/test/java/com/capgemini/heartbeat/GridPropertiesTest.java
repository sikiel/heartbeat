package com.capgemini.heartbeat;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.Platform;

public class GridPropertiesTest {

	String DEFAULT_VALUE = JsonFileConverter.DEFAULT_PROPERTY_VALUE;
	
	@Test
	public void testReadingFullEntry() {
		GridProperties gp = new GridProperties("./src/test/resources/gridTest.json");
		List<Property>pl = gp.getPropertiesList();
		assertTrue(pl.size()==3);
		assertEquals(pl.get(0).getUrl(),"http://11.40.168.105:4444");
		
		assertEquals(2, ((GridProperty)pl.get(0)).getNodesList().size());
		List<GridNode> nodesList = ((GridProperty)pl.get(0)).getNodesList();
		assertEquals("internet explorer", nodesList.get(0).getBrowser());
		assertEquals("9", nodesList.get(0).getBrowserVersion());
		assertEquals(Platform.WINDOWS, nodesList.get(0).getPlatform());
		assertEquals("chrome", nodesList.get(1).getBrowser());
		assertEquals("46", nodesList.get(1).getBrowserVersion());
		assertEquals(Platform.LINUX, nodesList.get(1).getPlatform());
	}
	
	@Test
	public void testReadingWithMissingProperties() {
		GridProperties gp = new GridProperties("./src/test/resources/gridTest.json");
		List<Property>pl = gp.getPropertiesList();
		assertTrue(pl.size()==3);
		assertEquals(pl.get(1).getUrl(),"http://11.40.170.54:4444");
		
		assertEquals(3, ((GridProperty)pl.get(1)).getNodesList().size());
		List<GridNode> nodesList = ((GridProperty)pl.get(1)).getNodesList();
		assertEquals("internet explorer", nodesList.get(0).getBrowser());
		assertEquals(DEFAULT_VALUE, nodesList.get(0).getBrowserVersion());
		assertEquals(Platform.WINDOWS, nodesList.get(0).getPlatform());
		assertEquals("chrome", nodesList.get(1).getBrowser());
		assertEquals(DEFAULT_VALUE, nodesList.get(1).getBrowserVersion());
		assertEquals(Platform.ANY, nodesList.get(1).getPlatform());
		assertEquals(DEFAULT_VALUE, nodesList.get(2).getBrowser());
		assertEquals(DEFAULT_VALUE, nodesList.get(2).getBrowserVersion());
		assertEquals(Platform.ANY, nodesList.get(2).getPlatform());

	}
	
	@Test
	public void testReadingNoNodesSpecified() {
		GridProperties gp = new GridProperties("./src/test/resources/gridTest.json");
		List<Property>pl = gp.getPropertiesList();
		assertTrue(pl.size()==3);
		assertEquals(pl.get(2).getUrl(),"http://11.40.156.187:4444");
		
		assertEquals(0, ((GridProperty)pl.get(2)).getNodesList().size());

	}
	
	@Test
	public void testFileNotFoundException() {
		TaskProperties gp = new GridProperties("jenkinsTest.json");
		assertTrue(gp.getPropertiesList().size()==0);
	}
}
