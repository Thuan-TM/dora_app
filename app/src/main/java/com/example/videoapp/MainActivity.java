package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoapp.adapter.VideoAdapter;
import com.example.videoapp.api.ApiGetListVideo;
import com.example.videoapp.broadcast.NetworkReceiver;
import com.example.videoapp.interfaces.GetVideo;
import com.example.videoapp.object.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetVideo {
    private NetworkReceiver network;
    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private ArrayList<Video> videoArr;
    private int currentPage = 0;
    private ImageView btnSearch,btnSearch1,logo;
    private EditText formSearch;
    private RelativeLayout formSearchWrap;

    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        anhXa();
        setUp();
        setClick();
        new ApiGetListVideo(this).execute("0","");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    formSearchWrap.setVisibility(View.VISIBLE);
                } else {
                    formSearchWrap.setVisibility(View.GONE);
                }
                check = !check;
            }
        });

    }

    private void init() {
//      khoi tao broadcast
        network = new NetworkReceiver(MainActivity.this);

        videoArr = new ArrayList<>();
        btnSearch = findViewById(R.id.ic_search);
        btnSearch1 = findViewById(R.id.btnSearch1);
        logo = findViewById(R.id.logo);
        formSearch = findViewById(R.id.formSearch);
        formSearchWrap = findViewById(R.id.formSearchWrap);
        formSearchWrap.setVisibility(View.GONE);
    }

    private void anhXa() {
        recyclerView = findViewById(R.id.recyclerViewVideoList);
    }

    private void setUp() {
        adapter = new VideoAdapter(this, videoArr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setClick() {
//      action search
        btnSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s =  formSearch.getText().toString();
                currentPage = 0;
                if (NetworkReceiver.isIs_check_network() == false){
                    Toast.makeText(MainActivity.this, "mạng không khả dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
                new ApiGetListVideo(MainActivity.this).execute(String.valueOf(currentPage),s);

                formSearchWrap.setVisibility(View.GONE);

            }
        });

//    go back home
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s =  "";
                currentPage = 0;

                if (NetworkReceiver.isIs_check_network() == false){
                    Toast.makeText(MainActivity.this, "mạng không khả dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
                new ApiGetListVideo(MainActivity.this).execute(String.valueOf(currentPage),"");
            }
        });


//        ImageView prevButton = findViewById(R.id.prevButton);
//        ImageView nextButton = findViewById(R.id.nextButton);
//
//        prevButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                currentPage -= 10;
//                if (currentPage < 0) {
//                    currentPage = 0;
//                }
//                loadMore();
//            }
//        });
//
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                currentPage += 10;
//                loadMore();
//            }
//        });

        TextView loadMoreText = findViewById(R.id.loadMore);
        ImageView userIcon = findViewById(R.id.ic_user);
        loadMoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMore();
            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (NetworkReceiver.isIs_check_network() == false){
                    Toast.makeText(MainActivity.this, "mạng không khả dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
                Video video = videoArr.get(position);
                Bundle b = new Bundle();
                b.putSerializable("video", video);
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                intent.putExtra("data", b);
                startActivity(intent);
            }
        });
    }

    private void loadMore() {
        videoArr.clear();
//        adapter.notifyDataSetChanged();
        currentPage += 10;
        if (NetworkReceiver.isIs_check_network() == false){
            Toast.makeText(MainActivity.this, "mạng không khả dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        new ApiGetListVideo(this).execute(String.valueOf(currentPage),"");
//        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void batDau() {
        if (NetworkReceiver.isIs_check_network() == false){
            Toast.makeText(MainActivity.this, "mạng không khả dụng", Toast.LENGTH_SHORT).show();
            return;
        }
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


    @Override
    protected void onStart() {
        super.onStart();
        network.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        network.stop();
    }
}