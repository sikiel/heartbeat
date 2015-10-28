package com.capgemini.heartbeat;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class JenkinsPropertiesTest {

	@Test
	public void testFileReading() {
		TaskProperties jp = new JenkinsProperties("./src/test/resources/jenkinsTest.json");
		List<Property>pl = jp.getPropertiesList();
		assertTrue(pl.size()==1);
		assertEquals(pl.get(0).getUrl(),"123.456.789.11:8080");
		assertEquals(((JenkinsProperty)pl.get(0)).getUsername(),"asd");
		assertEquals(((JenkinsProperty)pl.get(0)).getPassword(),"123qwe");

	}
	@Test
	public void testFileNotFoundException() {
		TaskProperties jp = new JenkinsProperties("jenkinsTest.json");
		assertTrue(jp.getPropertiesList().size()==0);
	}

}
