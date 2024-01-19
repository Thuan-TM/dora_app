package com.example.videoapp.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.videoapp.MainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApiSendComment extends AsyncTask<String, Void, String> {

    private Context context;

    public ApiSendComment(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        String uid = params[0];
        String yid = params[1];
        String prid = params[2];
        String content = params[3];

        try {
            OkHttpClient client = new OkHttpClient();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("u_id", uid);
            jsonObject.addProperty("y_id", yid);
            jsonObject.addProperty("parent_id", prid);
            jsonObject.addProperty("content", content);

            String jsonString = new Gson().toJson(jsonObject);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            RequestBody requestBody = RequestBody.create(JSON, jsonString);

            System.out.println(requestBody);
            Request request = new Request.Builder()
                    .url("https://dorayaki.webi.vn/api/api_comment/add_comment")
                    .post(requestBody)
                    .build();

            // Execute the request and get the response
            Response response = client.newCall(request).execute();

            // Check if the request was successful
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Error: " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
        try {
            JSONObject parsedJsonObject = new JSONObject(result);

            if (parsedJsonObject != null) {
                String msgValue = parsedJsonObject.getString("msg");
                Toast.makeText(context, msgValue, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "JSON parse error", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "JSON parse error", Toast.LENGTH_SHORT).show();
        }
    }
}
