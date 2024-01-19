package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.videoapp.adapter.VideoAdapter;
import com.example.videoapp.broadcast.NetworkReceiver;
import com.example.videoapp.object.User;
import com.example.videoapp.object.Video;

public class SettingActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private NetworkReceiver network;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setClick();

        preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);

        String userId = preferences.getString("user_id", null);
        String userName = preferences.getString("user_fullname", null);

        String name = User.getCurrent_fullname();
        String des = User.getCurrent_des();

        TextView usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(name);
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