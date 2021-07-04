package com.example.instaclone_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullnameInput;
    private  EditText usernameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button registerBtn;
    private TextView alreadyReg;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

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

        progressDialog = new ProgressDialog(this);

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
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                }else{
                    //register user
                    registerUser(txtUsername, txtEmail, txtPassword, txtName);

                }
            }
        });
    }

    private void registerUser(String userName, String email, String password, String name){
        //show progress
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDialog.dismiss();
                //if successfully registered then we want to save the user to our database
                //firebase saves data in map form
                HashMap<String,Object> myMap = new HashMap<>();
                myMap.put("userName",userName);
                myMap.put("name",name);
                myMap.put("email",email);
                myMap.put("password",password);
                myMap.put("id",firebaseAuth.getCurrentUser().getUid());

                databaseReference.child("Users").child(firebaseAuth.getCurrentUser().getUid()).setValue(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //when inserting to database completes:
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent );
                            finish(); // so that when user comes to back button he doesn't come back to register activity again
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}