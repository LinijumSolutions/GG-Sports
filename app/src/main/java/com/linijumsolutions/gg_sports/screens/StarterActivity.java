package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.linijumsolutions.gg_sports.MyApplication;
import com.linijumsolutions.gg_sports.R;
import com.linijumsolutions.gg_sports.clients.ParseClient;


public class StarterActivity extends Activity {
    protected MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        int myTimer = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                app = (MyApplication)getApplication();
                Intent intent = ParseClient.setParseUser(StarterActivity.this);
                StarterActivity.this.startActivity(intent);
                StarterActivity.this.finish();
            }
        }, myTimer);

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        this.finish();
    }
}