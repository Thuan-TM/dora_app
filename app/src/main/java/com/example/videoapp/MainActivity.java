package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.videoapp.adapter.VideoAdapter;
import com.example.videoapp.api.ApiGetListVideo;
import com.example.videoapp.interfaces.GetVideo;
import com.example.videoapp.object.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetVideo {

    GridView gridView;
    VideoAdapter adapter;
    ArrayList<Video> videoArr;
    int currentPage = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        anhXa();
        setUp();
        setClick();
        new ApiGetListVideo(this).execute();
    }

    private void init(){
        videoArr = new ArrayList<>();
    }
    private void anhXa(){
        gridView = findViewById(R.id.gridViewVideoList);
    }
    private void setUp(){
        gridView.setAdapter(adapter);
    }
    private void setClick(){
        ImageView prevButton = findViewById(R.id.prevButton);
        ImageView nextButton = findViewById(R.id.nextButton);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage -= 10;
                if (currentPage < 0) {
                    currentPage = 0;
                }
                loadMore();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage += 10;
                loadMore();
            }
        });
    }

    private void loadMore() {
        currentPage += 10;
        new ApiGetListVideo(this).execute(currentPage);
        gridView.smoothScrollToPosition(0);
    }

    @Override
    public void batDau() {
        Toast.makeText(this, "Dang lay ve", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        try {
            videoArr.clear();
            JSONArray arr = new JSONArray(data);
            for(int i = 0; i < arr.length(); i++){
                JSONObject o = arr.getJSONObject(i);
                videoArr.add(new Video(o));
            }
            adapter = new VideoAdapter(this, 0, videoArr);
            gridView.setAdapter(adapter);
        }
        catch (JSONException e){
        }
    }

    @Override
    public void biLoi() {
        Toast.makeText(this, "Bi loi", Toast.LENGTH_SHORT).show();
    }
}