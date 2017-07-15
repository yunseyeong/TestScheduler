package com.example.seyeong.testscheduler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
        //network connect checking
        if(!isNetworkConnected(this))
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Network was missing").setMessage("Please check networking connected")
                    .setPositiveButton("OK", (dialog, which) -> finish()).show();
        }
        //
        else if(isNetworkConnected(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            SystemClock.sleep(1000);//showing splashImage during 1 second;
            finish();//if MainActivity's Task was finished, show MainActivity;
        }
    }

    public boolean isNetworkConnected(Context context)
    {
        boolean isConnected = false;
    /*
    ConnectivityManager manager =
            (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    */
        //getNetworkInfo is deprecated!
        ConnectivityManager cm  = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null)
        {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI && activeNetwork.isConnectedOrConnecting())
            {
                //wifi is connected
                isConnected=true;
            }
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE && activeNetwork.isConnectedOrConnecting())
            {
                //mobile network is connected
                isConnected=true;
            }
            else
            {
                //network isn't available
                isConnected=false;
            }
        }
        return isConnected;
    }
}

