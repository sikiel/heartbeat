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
		assertEquals("http://123.456.789.11:8080", pl.get(0).getUrl());
		assertEquals("asd",((JenkinsProperty)pl.get(0)).getUsername());
		assertEquals("123qwe",((JenkinsProperty)pl.get(0)).getPassword());

	}
	@Test
	public void testFileReadingEmptyProperties() {
		TaskProperties jp = new JenkinsProperties("./src/test/resources/jenkinsTestEmpty.json");
		List<Property>pl = jp.getPropertiesList();
		assertTrue(pl.size()==6);
		//-----------------------
		assertEquals("http://123.456.789.11:8080", pl.get(0).getUrl());
		assertEquals("asd",((JenkinsProperty)pl.get(0)).getUsername());
		assertEquals("123qwe",((JenkinsProperty)pl.get(0)).getPassword());
		//-----------------------
		assertEquals("http://123.456.789.11:8080", pl.get(1).getUrl());
		assertEquals("undefined",((JenkinsProperty)pl.get(1)).getUsername());
		assertEquals("undefined",((JenkinsProperty)pl.get(1)).getPassword());
		//-----------------------
		assertEquals("http://123.456.789.11:8080", pl.get(2).getUrl());
		assertEquals("undefined",((JenkinsProperty)pl.get(2)).getUsername());
		assertEquals("123qwe",((JenkinsProperty)pl.get(2)).getPassword());
		//-----------------------
		assertEquals("http://123.456.789.11:8080", pl.get(3).getUrl());
		assertEquals("asd",((JenkinsProperty)pl.get(3)).getUsername());
		assertEquals("undefined",((JenkinsProperty)pl.get(3)).getPassword());
		//-----------------------
		assertEquals("undefined", pl.get(4).getUrl());
		assertEquals("undefined",((JenkinsProperty)pl.get(4)).getUsername());
		assertEquals("undefined",((JenkinsProperty)pl.get(4)).getPassword());
		//-----------------------
		assertEquals("http://123.456.789.11:8080", pl.get(5).getUrl());
		assertEquals("",((JenkinsProperty)pl.get(5)).getUsername());
		assertEquals("undefined",((JenkinsProperty)pl.get(5)).getPassword());


	}
	@Test
	public void testFileNotFoundException() {
		TaskProperties jp = new JenkinsProperties("jenkinsTest.json");
		assertTrue(jp.getPropertiesList().size()==0);
	}

}
