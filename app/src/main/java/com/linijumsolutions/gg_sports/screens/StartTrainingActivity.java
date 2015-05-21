package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.linijumsolutions.gg_sports.R;


public class StartTrainingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        Spinner spinner = (Spinner) findViewById(R.id.pasirinkimasSporto);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sarasasSportoSakos, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onStartWithRouteClicked(View v){
        Intent intent = new Intent(this, RouteSelectionActivity.class);
        startActivity(intent);
    }

    public void onStartWithoutRouteClicked(View v){
        Intent intent = new Intent(this, StartTrainingActivity.class);
        startActivity(intent);
    }

}
