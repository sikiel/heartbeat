package com.capgemini.heartbeat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class JsonFileConverter {
	private static final Logger log = Logger.getLogger(JsonFileConverter.class.getName());

	public JsonArray convertFileToJSON(String fileName) {

		JsonArray jsonArray = new JsonArray();

		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(fileName));
			jsonArray = jsonElement.getAsJsonArray();
		} catch (FileNotFoundException e) {
			log.severe(e.toString());
		}

		return jsonArray;
	}

}