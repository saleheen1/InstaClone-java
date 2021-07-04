package com.example.instaclone_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullnameInput;
    private  EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button registerBtn;
    private TextView alreadyReg;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.usernameId);
        fullnameInput = findViewById(R.id.nameId);
        emailInput = findViewById(R.id.emailId);
        passwordInput = findViewById(R.id.passwordId);
        registerBtn = findViewById(R.id.registerBtnId);
        alreadyReg = findViewById(R.id.alreadyregId);

        //getting instance of Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        //already register button clicked
        alreadyReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        //clicking on register button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = usernameInput.getText().toString();
                String txtName = fullnameInput.getText().toString();
                String txtEmail = emailInput.getText().toString();
                String txtPassword = passwordInput.getText().toString();



                if(TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(RegisterActivity.this,"Make sure no field is empty",Toast.LENGTH_SHORT).show();
                } else if(txtPassword.length() <6){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 character long", Toast.LENGTH_SHORT).show();
                }else{
                    //register user
                    registerUser(String txtUsername,String txtEmail,String txtPassword,String txtName);
                }
            }
        });
    }


}