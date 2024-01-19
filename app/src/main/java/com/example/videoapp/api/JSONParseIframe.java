package com.example.videoapp.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParseIframe {

    public String parseJson(String jsonResponse) {
        StringBuilder result = new StringBuilder();

        try {
            // Parse the YouTube video JSON response
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // Access the "items" array
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Iterate through the array and process each video item
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject videoObject = itemsArray.getJSONObject(i);

                // Access the "player" object within the video
                JSONObject playerObject = videoObject.getJSONObject("player");

                // Extract embedHtml from the player
                String embedHtml = playerObject.getString("embedHtml");

                // Append the embedHtml value to the result StringBuilder
                result.append(embedHtml);

                // If there are more items, add a separator
                if (i < itemsArray.length() - 1) {
                    result.append(", ");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Convert StringBuilder to String and return
        return result.toString();
    }
}
