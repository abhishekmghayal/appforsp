package com.rahul.spbcollegeduplicate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Animation faden;
    ImageView img_app_logo,img_subtitle;
    TextView tv_college_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        faden= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.faden);

        img_app_logo = findViewById(R.id.img_splash_app_logo);
        tv_college_name = findViewById(R.id.clg_name);

        img_app_logo.setAnimation(faden);
        tv_college_name.setAnimation(faden);
        Handler h1=new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}