package com.example.videoapp.api;
import android.util.Log;
import android.os.AsyncTask;

import com.example.videoapp.interfaces.GetVideo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class ApiGetListVideo extends AsyncTask<Integer, Void, Void>{
    String data;
    GetVideo getVideo;
    public ApiGetListVideo(GetVideo getVideo){
        this.getVideo = getVideo;
        this.getVideo.batDau();
    }


    @Override
    protected Void doInBackground(Integer... maxResults) {
        int maxResultsValue = (maxResults.length > 0) ? maxResults[0] : 10;
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
    protected void onPostExecute(Void aVoid) {
        if(data == null){
            this.getVideo.biLoi();
        }
        else {

            JSONParse jsonParser = new JSONParse();

            // Call parseJson and get the parsed data
            String parsedData = jsonParser.parseJson(data);

            // Print the parsed data to the console
            System.out.println("Parsed JSON Data:\n" + parsedData);

            this.getVideo.ketThuc(parsedData);
        }
    }
}
