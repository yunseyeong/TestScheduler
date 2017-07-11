package com.example.seyeong.testscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Seyeong on 2017-06-26.
 */

/*
show splash activity
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        SystemClock.sleep(1000);//showing splashImage during 1 second;
        finish();//if MainActivity's Task was finished, show MainActivity;
    }
}
