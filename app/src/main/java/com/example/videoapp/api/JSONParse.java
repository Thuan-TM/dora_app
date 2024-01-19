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
            JSONArray itemsArray = jsonObject.getJSONArray("data");


            // Iterate through the array and process each video item
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject videoObject = itemsArray.getJSONObject(i);

                // Extract individual fields from the video item's snippet
                String id = videoObject.getString("id");
                String publishedAt = videoObject.getString("publishedAt");
                String title = videoObject.getString("title");
                String description = videoObject.getString("description");
                String iframe = videoObject.getString("iframe");
                String thumbnailUrl = videoObject.getString("thumbnails");
                String is_love = videoObject.getString("is_love");

                JSONObject simplifiedJson = new JSONObject();
                simplifiedJson.put("id", id);
                simplifiedJson.put("publishedAt", publishedAt);
                simplifiedJson.put("title", title);
                simplifiedJson.put("description", description);
                simplifiedJson.put("thumbnails", thumbnailUrl);
                simplifiedJson.put("iframe", iframe);
                simplifiedJson.put("is_love", is_love);
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