package com.capgemini.heartbeat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.openqa.selenium.remote.JsonException;

public class JsonFileConverter {

	public static String DEFAULT_PROPERTY_VALUE = "";

	public JsonArray convertFileToJSON(String fileName) {

		JsonArray jsonArray = new JsonArray();

		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(fileName));
			jsonArray = jsonElement.getAsJsonArray();
		} catch (JsonSyntaxException e) {
			HeartbeatFlow.log.error(e.toString());
		} catch (FileNotFoundException e) {
			HeartbeatFlow.log.error(e.toString());
		} catch (JsonException e) {
			HeartbeatFlow.log.error(e.toString());
		}

		return jsonArray;
	}

	public JsonArray getArray(Object jsonProperty, String name) {
		if (!jsonProperty.getClass().equals(JsonNull.class)) {
			if (((JsonObject) jsonProperty).get(name) != null) {
				return ((JsonObject) jsonProperty).get(name).getAsJsonArray();
			}
		}
		return new JsonArray();

	}

	public String getProperty(Object jsonProperty, String name) {

		if (!jsonProperty.getClass().equals(JsonNull.class)) {
			if (((JsonObject) jsonProperty).get(name) != null) {
				return ((JsonObject) jsonProperty).get(name).getAsString();
			}
		}
		return DEFAULT_PROPERTY_VALUE;

	}

}