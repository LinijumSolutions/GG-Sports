package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.linijumsolutions.gg_sports.R;


public class StartTrainingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
    }

    ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.sarasasSportoSakos, android.R.layout.simple_spinner_item);

}
