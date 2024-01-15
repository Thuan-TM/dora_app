package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.videoapp.api.ApiLogin;

public class Login extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView registerNow;
    private Button buttonLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        registerNow = findViewById(R.id.registerNow);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle login logic here
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

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
}