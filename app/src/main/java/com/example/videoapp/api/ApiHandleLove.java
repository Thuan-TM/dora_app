package com.example.videoapp.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.videoapp.MainActivity;
import com.example.videoapp.object.User;
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

public class ApiHandleLove extends AsyncTask<String, Void, String> {

    private Context context;

    public ApiHandleLove(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        int u_id = User.getCurrent_id();
        String y_id = params[0];
        String status = params[1];

        try {
            OkHttpClient client = new OkHttpClient();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("u_id", u_id);
            jsonObject.addProperty("y_id", y_id);
            jsonObject.addProperty("status", status);

            String jsonString = new Gson().toJson(jsonObject);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            RequestBody requestBody = RequestBody.create(JSON, jsonString);

            System.out.println(requestBody);
            Request request = new Request.Builder()
                    .url("https://dorayaki.webi.vn/api/api_user/handle_video_love")
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
        try {
            JSONObject parsedJsonObject = new JSONObject(result);

            if (parsedJsonObject != null) {
                String resValue = parsedJsonObject.getString("res");
                if ("done".equals(resValue)) {
                    String msgValue = parsedJsonObject.getString("msg");
                    Toast.makeText(context, msgValue, Toast.LENGTH_SHORT).show();


                } else {
                    String errorMessage = parsedJsonObject.getString("msg");
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle the case where parsing failed
                Toast.makeText(context, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle the case where parsing the result string to JSONObject failed
            Toast.makeText(context, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }
    }
}
