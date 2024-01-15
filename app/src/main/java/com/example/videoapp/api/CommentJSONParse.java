package com.example.videoapp.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommentJSONParse {

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
                JSONObject commentObj = itemsArray.getJSONObject(i);

                String content = commentObj.getJSONObject("Comment").getString("content");
                String created = commentObj.getJSONObject("Comment").getString("created");
                String uid = commentObj.getJSONObject("Comment").getString("user_id");
                String id = commentObj.getJSONObject("Comment").getString("id");
                String username = commentObj.getJSONObject("User").getString("fullname");
                String avatarUser = commentObj.getJSONObject("User").getString("avatar");

                long timestamp = Integer.parseInt(created) * 1000;
                Date date = new Date(timestamp);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = sdf.format(date);



                JSONObject simplifiedJson = new JSONObject();
                simplifiedJson.put("id", Integer.parseInt(id));
                simplifiedJson.put("uid", Integer.parseInt(uid));
                simplifiedJson.put("content", content);
                simplifiedJson.put("created", formattedDate);
                simplifiedJson.put("username", username);
                simplifiedJson.put("avatarUser", avatarUser);

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