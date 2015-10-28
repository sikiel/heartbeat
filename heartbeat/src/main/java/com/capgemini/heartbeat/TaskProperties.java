package com.capgemini.heartbeat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class TaskProperties {
	private String fileLocation;
	private FileReader reader;
	private JSONParser jsonParser;
	protected JSONArray jsonArray;
	protected List<Property> propertiesList;
	public TaskProperties(String fileLocation) {
		this.propertiesList = new ArrayList<Property>();
		this.fileLocation = fileLocation;
		this.jsonParser = new JSONParser();
		this.reader = null;
		try{
		try {
			this.reader = new FileReader(this.fileLocation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.jsonArray = null;
			return;
		}
		this.jsonArray = null;
		try {
			jsonArray = (JSONArray) this.jsonParser.parse(this.reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}
public abstract List<Property> getPropertiesList();
}
