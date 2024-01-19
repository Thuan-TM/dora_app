package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoapp.adapter.CommentAdapter;
import com.example.videoapp.api.ApiGetComment;
import com.example.videoapp.api.ApiGetIframe;
import com.example.videoapp.api.ApiGetListVideo;
import com.example.videoapp.api.ApiHandleLove;
import com.example.videoapp.api.ApiSendComment;
import com.example.videoapp.interfaces.GetComment;
import com.example.videoapp.object.Comment;
import com.example.videoapp.object.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity implements GetComment {

    private ImageView videoThumbnails, btnSend,is_love, ic_user, logo;
    private TextView videoTitle;
    private TextView videoDescription;
    private Video video;
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private ArrayList<Comment> commentArr;
    private EditText editTextComment;
    private SharedPreferences preferences;

    private boolean is_love_check = false;


    private String USERAGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
        anhXa();
        setUp();
        setClick();
    }

    private void init() {

        Bundle b = getIntent().getBundleExtra("data");
        video = (Video) b.getSerializable("video");

//        check is_love
        if(video.getIs_love() == 1) {
            is_love_check = true;
        }

        commentArr = new ArrayList<>();
    }

    private void anhXa() {
        videoTitle = findViewById(R.id.videoTitle);
        is_love = findViewById(R.id.is_love);
        videoDescription = findViewById(R.id.videoDescription);
        editTextComment = findViewById(R.id.editTextComment);
        btnSend = findViewById(R.id.btnSend);
        recyclerView = findViewById(R.id.recyclerViewComments);
        logo = findViewById(R.id.logo);
        ic_user = findViewById(R.id.ic_user);


//      settim
        if(is_love_check) {
            is_love.setImageResource(R.drawable.ic_love);
        }


    }

    private void setUp() {
        new ApiGetIframe(this).execute(video.getId());
        videoTitle.setText(video.getTitle());
        videoDescription.setText(video.getDescription());
        processEmbedHtml(video.getIframe());


        adapter = new CommentAdapter(this, commentArr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ApiGetComment(this).execute(video.getId());

//        video.getIs_love();
    }

    private void setClick() {

//        set click add love
        is_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_love_check){
                    new ApiHandleLove(VideoActivity.this).execute(video.getId(), "0");
                    is_love_check = false;
                    is_love.setImageResource(R.drawable.icon_heart);
                }
                else {
                    new ApiHandleLove(VideoActivity.this).execute(video.getId(), "1");
                    is_love_check = true;
                    is_love.setImageResource(R.drawable.ic_love);
                }
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                String userId = preferences.getString("user_id", null);
                String content = String.valueOf(editTextComment.getText());
                String videoId = video.getId();
                String parentId = "0";

                new ApiSendComment(VideoActivity.this).execute(userId, videoId, parentId, content);
                editTextComment.setText("");
                setUp();
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ic_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void processEmbedHtml(String embedHtml) {
        String VideoEmbededAdress = "<iframe width=\"400\" height=\"225\" src=\""+ embedHtml +"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"autoplay;\" allowfullscreen></iframe>";
        WebView webView = findViewById(R.id.videoWebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setUserAgentString(USERAGENT); // Important to auto play video
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadDataWithBaseURL("", VideoEmbededAdress, "text/html", "UTF-8", "");
    }


    @Override
    public void batDau() {
        Toast.makeText(this, "Dang lay ve", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        try {
            commentArr.clear();
            JSONArray arr = new JSONArray(data);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                commentArr.add(new Comment(o));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void biLoi() {
        Toast.makeText(this, "Bi loi", Toast.LENGTH_SHORT).show();
    }

    //    private void getIframeData() {
//        new ApiGetIframe(this) {
//            @Override
//            protected void onPostExecute(String embedHtml) {
//                super.onPostExecute(embedHtml);
//
//                // Process the embedHtml data
////                processEmbedHtml(embedHtml);
//            }
//        }.execute(video.getId());
//    }
}