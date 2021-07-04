package com.example.instaclone_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullnameInput;
    private  EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.usernameId);
        fullnameInput = findViewById(R.id.nameId);
        emailInput = findViewById(R.id.emailId);
        passwordInput = findViewById(R.id.passwordId);

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}