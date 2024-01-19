package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.videoapp.api.ApiProfile;
import com.example.videoapp.broadcast.NetworkReceiver;
import com.example.videoapp.object.User;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity  {
    private NetworkReceiver network;
    private TextInputEditText txt_name,txt_des;
    private Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        setClick();

    }

    private void init(){
        network = new NetworkReceiver(this);

        txt_name = findViewById(R.id.editUsername);
        txt_des = findViewById(R.id.editUserBio);

        btn_update = findViewById(R.id.saveButton);

//       set giá trị cho nam và des
        String name = User.getCurrent_fullname();
        String des = User.getCurrent_des();

        txt_name.setText(name);
        txt_des.setText(des);
    }


    private void setClick()
    {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkReceiver.isIs_check_network() == false){
                    Toast.makeText(ProfileActivity.this, "mạng không khả dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
//                lấy giá trị mới thay đổi
                String n_name= txt_name.getText().toString();
                String n_des= txt_des.getText().toString();

//                call api save
                new ApiProfile(ProfileActivity.this).execute(n_name,n_des);

            }
        });
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