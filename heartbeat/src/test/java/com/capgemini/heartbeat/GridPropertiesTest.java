package com.capgemini.heartbeat;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class GridPropertiesTest {

	@Test
	public void test() {
		TaskProperties gp = new GridProperties("./src/test/resources/jenkinsTest.json");
		List<Property>pl = gp.getPropertiesList();
		assertTrue(pl.size()==1);
		assertEquals(pl.get(0).getUrl(),"http://123.456.789.11:8080");
	}
	@Test
	public void testFileNotFoundException() {
		TaskProperties gp = new GridProperties("jenkinsTest.json");
		assertTrue(gp.getPropertiesList().size()==0);
	}
}
