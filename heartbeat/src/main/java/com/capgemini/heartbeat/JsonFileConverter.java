package com.capgemini.heartbeat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileConverter {

public JsonArray convertFileToJSON (String fileName){

        // Read from File to String
        JsonArray jsonArray = new JsonArray();
        
        
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonArray = jsonElement.getAsJsonArray();
        } catch (FileNotFoundException e) {
           
        } catch (IOException ioe){
        
        }
        
        
        return jsonArray;
    }

}