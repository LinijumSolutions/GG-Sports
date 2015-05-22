package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;
import com.linijumsolutions.gg_sports.R;
import com.linijumsolutions.gg_sports.screens.LoginActivity;
import com.linijumsolutions.gg_sports.screens.StartTrainingActivity;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartTrainingClicked(View v){
        Intent intent = new Intent(this, StartTrainingActivity.class);
        startActivity(intent);
    }

    public void onHistoryClicked(View v){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void onAllRoutesClicked(View v){
        Intent intent = new Intent(this, AllRoutesActivity.class);
        startActivity(intent);
    }

}
