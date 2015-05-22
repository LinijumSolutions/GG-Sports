package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.linijumsolutions.gg_sports.MyApplication;
import com.linijumsolutions.gg_sports.R;
import com.linijumsolutions.gg_sports.clients.ParseClient;


public class StarterActivity extends Activity {

    protected MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        FacebookSdk.sdkInitialize(getApplicationContext());

        if(isNetworkAvailable()) {
            int myTimer = 1000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    app = (MyApplication) getApplication();
                    Intent intent = ParseClient.setParseUser(StarterActivity.this);
                    startActivity(intent);
                    finish();
                }
            }, myTimer);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}