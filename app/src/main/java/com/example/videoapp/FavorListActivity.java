package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoapp.adapter.FavorVideoAdapter;
import com.example.videoapp.adapter.VideoAdapter;
import com.example.videoapp.api.ApiGetListFavorVideo;
import com.example.videoapp.api.ApiGetListVideo;
import com.example.videoapp.interfaces.GetFavorVideo;
import com.example.videoapp.interfaces.GetVideo;
import com.example.videoapp.object.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavorListActivity extends AppCompatActivity implements GetFavorVideo {

    private RecyclerView recyclerView;
    private FavorVideoAdapter adapter;
    private ArrayList<Video> videoArr;
    private int currentPage = 10;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_list);

        init();
        anhXa();
        setUp();
        setClick();

//        preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
//        String userId = preferences.getString("user_id", null);
        new ApiGetListFavorVideo(this).execute();
    }

    private void init() {
        videoArr = new ArrayList<>();
    }

    private void anhXa() {
        recyclerView = findViewById(R.id.recyclerViewVideoList);
    }

    private void setUp() {
        adapter = new FavorVideoAdapter(this, videoArr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setClick() {

        TextView loadMoreText = findViewById(R.id.loadMore);
        loadMoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMore();
            }
        });


        adapter.setOnItemClickListener(new FavorVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Video video = videoArr.get(position);
                Bundle b = new Bundle();
                b.putSerializable("video", video);
                Intent intent = new Intent(FavorListActivity.this, VideoActivity.class);
                intent.putExtra("data", b);
                startActivity(intent);
            }
        });
    }

    private void loadMore() {
//        videoArr.clear();
////        adapter.notifyDataSetChanged();
//        currentPage += 10;
//        new ApiGetListFavorVideo(this).execute(currentPage);
//        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void batDau() {
        Toast.makeText(this, "Đang lấy dữ liệu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        try {
            videoArr.clear();
            JSONArray arr = new JSONArray(data);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                videoArr.add(new Video(o));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void biLoi() {
        Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
    }
}