package com.example.instaclone_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StartActivity extends AppCompatActivity {

    private ImageView iconImage;
    private LinearLayout loginLinear;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        iconImage = findViewById(R.id.id_icon);
        loginLinear = findViewById(R.id.id_loginLInear);
        loginBtn = findViewById(R.id.id_login);
        registerBtn = findViewById(R.id.id_Signup);

        loginLinear.animate().alpha(1f).setDuration(1);

        TranslateAnimation animation = new TranslateAnimation(0,0,0,-1000);
        animation.setDuration(1500);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());

        iconImage.setAnimation(animation);


        //go to register page
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        //go to login page
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

    }

    private class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            iconImage.clearAnimation();
            iconImage.setVisibility(View.INVISIBLE);
            loginLinear.setVisibility(View.VISIBLE);
            loginLinear.animate().alpha(1f).setDuration(3000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}