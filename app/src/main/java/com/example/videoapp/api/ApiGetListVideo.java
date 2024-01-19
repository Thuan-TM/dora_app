package com.example.videoapp.api;
import android.util.Log;
import android.os.AsyncTask;

import com.example.videoapp.interfaces.GetVideo;
import com.example.videoapp.object.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class ApiGetListVideo extends AsyncTask<String, Void, Void>{
    String data;
    GetVideo getVideo;
    public ApiGetListVideo(GetVideo getVideo){
        this.getVideo = getVideo;
        this.getVideo.batDau();
    }


    @Override
    protected Void doInBackground(String... maxResults) {
        String maxResultsValue =maxResults[0];
        String search = maxResults[1];
        Integer u_id = User.current_id;

        System.out.println(u_id);
//        System.out.println("result number: " );
//        System.out.println(maxResultsValue);

        data = null;
        try {
            OkHttpClient client = new OkHttpClient();

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("offset", maxResultsValue);

            jsonObject.addProperty("search", search);

            jsonObject.addProperty("u_id", u_id);

            String jsonString = new Gson().toJson(jsonObject);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            RequestBody requestBody = RequestBody.create(JSON, jsonString);

            Request request = new Request.Builder()
                    .url("https://dorayaki.webi.vn/api/api_video/get_popular_list")
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

//        try {
//            Response response = client.newCall(request).execute();
//            ResponseBody body = response.body();
//            data = body.string();
//
//        } catch (IOException e){
//            data = null;
//        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(data == null){
            this.getVideo.biLoi();
        }
        else {
            System.out.println(data);

            JSONParse jsonParser = new JSONParse();

            // Call parseJson and get the parsed data
            String parsedData = jsonParser.parseJson(data);

            // Print the parsed data to the console
            System.out.println("Parsed JSON Data:\n" + parsedData);

            this.getVideo.ketThuc(parsedData);
        }
    }
}
