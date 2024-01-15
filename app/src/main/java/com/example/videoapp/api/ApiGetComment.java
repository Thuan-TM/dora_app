package com.example.videoapp.api;
import android.util.Log;
import android.os.AsyncTask;

import com.example.videoapp.interfaces.GetComment;
import com.example.videoapp.interfaces.GetVideo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class ApiGetComment extends AsyncTask<String, Void, Void>{
    String data;
    GetComment getComment;
    public ApiGetComment(GetComment getComment){
        this.getComment = getComment;
        this.getComment.batDau();
    }


    @Override
    protected Void doInBackground(String... id) {

        data = null;
        try {
            OkHttpClient client = new OkHttpClient();

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("y_id", id[0]);

            String jsonString = new Gson().toJson(jsonObject);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            RequestBody requestBody = RequestBody.create(JSON, jsonString);

            Request request = new Request.Builder()
                    .url("https://dorayaki.webi.vn/api/api_comment/comment_list")
                    .post(requestBody)
                    .build();

            // Execute the request and get the response
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string();
        }
        catch (IOException e){
            data = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        if(data == null){
            this.getComment.biLoi();
        }
        else {
            CommentJSONParse jsonParser = new CommentJSONParse();

            // Call parseJson and get the parsed data
            String parsedData = jsonParser.parseJson(data);

            // Print the parsed data to the console
            System.out.println("Parsed JSON Data:\n" + parsedData);

            this.getComment.ketThuc(parsedData);
        }
    }
}
