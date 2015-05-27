package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.linijumsolutions.gg_sports.R;


public class HistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    public void onStatisticsClicked(View v){
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

}
