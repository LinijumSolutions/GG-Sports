package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.linijumsolutions.gg_sports.R;
import com.linijumsolutions.gg_sports.screens.MainActivity;


public class WorkoutEndActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_end);
    }

    public void onSkipClicked(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onShareClicked(View v){
    }
}
