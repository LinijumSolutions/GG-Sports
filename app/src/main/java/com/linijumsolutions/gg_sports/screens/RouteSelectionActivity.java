package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.linijumsolutions.gg_sports.R;


public class RouteSelectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);
    }

    public void onGenerateNewClicked(View v){
        Intent intent = new Intent(this, GenerateRouteActivity.class);
        intent.putExtra("type", getIntent().getLongExtra("type", 0));
        startActivity(intent);
    }
}
