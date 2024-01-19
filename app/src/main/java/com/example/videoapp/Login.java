package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoapp.api.ApiLogin;
import com.example.videoapp.broadcast.NetworkReceiver;

public class Login extends AppCompatActivity {
    private NetworkReceiver network;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView registerNow, buttonLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        registerNow = findViewById(R.id.registerNow);
        buttonLogin = findViewById(R.id.buttonLogin);

        network = new NetworkReceiver(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle login logic here
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (NetworkReceiver.isIs_check_network() == false){
                    Toast.makeText(Login.this, "mạng không khả dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
                new ApiLogin(Login.this).execute(username, password);

            }
        });

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
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