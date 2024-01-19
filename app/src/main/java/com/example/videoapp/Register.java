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
import com.example.videoapp.api.ApiRegister;
import com.example.videoapp.broadcast.NetworkReceiver;

public class Register extends AppCompatActivity {
    private NetworkReceiver network;
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView loginNow, buttonRegister;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginNow = findViewById(R.id.loginNow);
        buttonRegister = findViewById(R.id.buttonRegister);

        network = new NetworkReceiver(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkReceiver.isIs_check_network() == false){
                    Toast.makeText(Register.this, "mạng không khả dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = editTextName.getText().toString();
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();


                new ApiRegister(Register.this).execute(name, username, password);
            }
        });

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkReceiver.isIs_check_network() == false){
                    Toast.makeText(Register.this, "mạng không khả dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Register.this, Login.class);
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