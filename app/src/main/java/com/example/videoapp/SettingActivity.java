package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.videoapp.adapter.VideoAdapter;
import com.example.videoapp.object.Video;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setClick();
    }

    private void setClick() {
        LinearLayout profileLayout = findViewById(R.id.profileLayout);
        LinearLayout favorLayout = findViewById(R.id.favorListLayout);
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        favorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, FavorListActivity.class);
                startActivity(intent);
            }
        });

    }

}