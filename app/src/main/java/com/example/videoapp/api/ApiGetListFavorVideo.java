package com.example.videoapp.api;

import android.os.AsyncTask;

import com.example.videoapp.interfaces.GetFavorVideo;
import com.example.videoapp.interfaces.GetVideo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class ApiGetListFavorVideo extends AsyncTask<Integer, Void, Void> {
    String data;
    GetFavorVideo getFavorVideo;
    public ApiGetListFavorVideo(GetFavorVideo getFavorVideo){
        this.getFavorVideo = getFavorVideo;
        this.getFavorVideo.batDau();
    }
    @Override
    protected Void doInBackground(Integer... integers) {
        int maxResultsValue = (integers.length > 0) ? integers[0] : 10;
        System.out.println("result number: " );
        System.out.println(maxResultsValue);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&maxResults="+ maxResultsValue +"&key=AIzaSyDyJD9CIGcztFVOFb8ecWvaPLtgFBa-EeM")
                .build();
        data = null;
        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string();

        } catch (IOException e){
            data = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        if(data == null){
            this.getFavorVideo.biLoi();
        }
        else {

            JSONParse jsonParser = new JSONParse();

            // Call parseJson and get the parsed data
            String parsedData = jsonParser.parseJson(data);

            // Print the parsed data to the console
            System.out.println("Parsed JSON Data:\n" + parsedData);

            this.getFavorVideo.ketThuc(parsedData);
        }
    }
}
