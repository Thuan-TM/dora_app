package com.example.videoapp.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParse {

    public String parseJson(String jsonResponse) {
        StringBuilder parsedData = new StringBuilder();
        ArrayList<JSONObject> simplifiedArray = new ArrayList<>();

        try {

            // Parse the YouTube video JSON response
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // Access the "items" array
            JSONArray itemsArray = jsonObject.getJSONArray("items");


            // Iterate through the array and process each video item
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject videoObject = itemsArray.getJSONObject(i);

                // Extract individual fields from the video item's snippet
                String id = videoObject.getString("id");
                String publishedAt = videoObject.getJSONObject("snippet").getString("publishedAt");
                String title = videoObject.getJSONObject("snippet").getString("title");
                String description = videoObject.getJSONObject("snippet").getString("description");

                // Access the "thumbnails" object within the snippet
                JSONObject thumbnailsObject = videoObject.getJSONObject("snippet").getJSONObject("thumbnails");

                // Access the "default" object within thumbnails
                JSONObject defaultThumbnail = thumbnailsObject.getJSONObject("maxres");
                String thumbnailUrl = defaultThumbnail.getString("url");

                // Append the extracted data to the StringBuilder
                JSONObject simplifiedJson = new JSONObject();
                simplifiedJson.put("id", id);
                simplifiedJson.put("publishedAt", publishedAt);
                simplifiedJson.put("title", title);
                simplifiedJson.put("description", description);
                simplifiedJson.put("thumbnails", thumbnailUrl);

                simplifiedArray.add(simplifiedJson);
//                System.out.println("json data parse: "+simplifiedJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Convert StringBuilder to String and return
        return simplifiedArray.toString();
    }
}