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
public class ApiProfile extends AsyncTask<String, Void, String> {
    private Context context;

    public ApiProfile(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String description = params[1];
        Integer id = User.getCurrent_id();
        System.out.println(username);
        System.out.println(description);
        try {
            OkHttpClient client = new OkHttpClient();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("u_id", id);
            jsonObject.addProperty("fullname", username);
            jsonObject.addProperty("description", description);

            String jsonString = new Gson().toJson(jsonObject);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            RequestBody requestBody = RequestBody.create(JSON, jsonString);

            System.out.println(requestBody);
            Request request = new Request.Builder()
                    .url("https://dorayaki.webi.vn/api/api_user/user_update")
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
                    JSONObject data = parsedJsonObject.optJSONObject("data");

                    Toast.makeText(context, msgValue, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);


                    SharedPreferences preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("user_id", data.optString("id"));
                    editor.putString("user_avatar", data.optString("avatar"));
                    editor.putString("user_fullname", data.optString("fullname"));
                    editor.apply();
                    System.out.println(editor);
                    User.setCurrent_id(Integer.parseInt(data.optString("id")));
                    User.setCurrent_fullname((data.optString("fullname")));
                    User.setCurrent_des((data.optString("description")));

                } else {
                    String errorMessage = parsedJsonObject.getString("msg");
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle the case where parsing failed
                Toast.makeText(context, "Bị lỗi rồi", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle the case where parsing the result string to JSONObject failed
            Toast.makeText(context, "Bị lỗi rồi", Toast.LENGTH_SHORT).show();
        }
    }


}
