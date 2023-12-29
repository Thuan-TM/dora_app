package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoapp.adapter.CommentAdapter;
import com.example.videoapp.api.ApiGetIframe;
import com.example.videoapp.interfaces.GetComment;
import com.example.videoapp.object.Comment;
import com.example.videoapp.object.Video;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity implements GetComment {

    ImageView videoThumbnails;
    TextView videoTitle;
    TextView videoDescription;
    Video video;
    RecyclerView recyclerView;
    CommentAdapter adapter;
    ArrayList<Comment> commentArr;
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

        // fix cung data
        commentArr = new ArrayList<>();
        commentArr.add(new Comment(1, 2, "That's great!", "20/10/2023"));
        commentArr.add(new Comment(1, 2, "Nicer!", "20/10/2023"));
        commentArr.add(new Comment(1, 2, "Perfect!", "20/10/2023"));
        commentArr.add(new Comment(1, 2, "Ok!", "20/10/2023"));
        commentArr.add(new Comment(1, 2, "Wowwwwwwwww!", "20/10/2023"));
        adapter = new CommentAdapter(this, commentArr);
    }

    private void anhXa() {
        videoTitle = findViewById(R.id.videoTitle);
        videoDescription = findViewById(R.id.videoDescription);

        // fix cung data
        recyclerView = findViewById(R.id.recyclerViewComments);
    }

    private void setUp() {
        new ApiGetIframe(this).execute(video.getId());
        videoTitle.setText(video.getTitle());
        videoDescription.setText(video.getDescription());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setClick() {
    }

    public void processEmbedHtml(String embedHtml) {
        String VideoEmbededAdress = "<iframe width=\"400\" height=\"225\" src=\"https://www.youtube.com/embed/qQlSSi1gVUQ?&autoplay=1\" title=\"YouTube video player\" frameborder=\"0\" allow=\"autoplay;\" allowfullscreen></iframe>";
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

    public void handleError() {
        // Handle errors as needed
    }

    @Override
    public void batDau() {
        Toast.makeText(this, "Dang lay ve", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
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