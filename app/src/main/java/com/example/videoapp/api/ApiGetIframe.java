package com.example.videoapp.api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.videoapp.VideoActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import android.widget.Toast;

import java.io.IOException;

public class ApiGetIframe extends AsyncTask<String, Void, String> {
    String data;
    private Context context;  // Add this line

    public ApiGetIframe(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... videoId) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.googleapis.com/youtube/v3/videos?part=player&id="+ videoId[0] + "&key=AIzaSyDyJD9CIGcztFVOFb8ecWvaPLtgFBa-EeM")
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
    protected void onPostExecute(String jsonResponse) {
        JSONParseIframe jsonParser = new JSONParseIframe();

        String parsedData = jsonParser.parseJson(data);

        System.out.println("Parsed JSON Data iframe:\n" + parsedData);

//        if (data != null) {
//            ((VideoActivity) context).processEmbedHtml(parsedData);
//        } else {
//            ((VideoActivity) context).handleError();
//        }
    }
}
