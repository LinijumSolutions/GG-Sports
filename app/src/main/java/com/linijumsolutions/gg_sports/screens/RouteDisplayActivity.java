package com.linijumsolutions.gg_sports.screens;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.linijumsolutions.gg_sports.R;

import java.util.ArrayList;

public class RouteDisplayActivity extends FragmentActivity {

    private ArrayList<LatLng> route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_display);
        Bundle extra = getIntent().getBundleExtra("extra");
        if(extra != null) {
            route = (ArrayList<LatLng>) extra.getSerializable("route");
            Toast.makeText(getApplicationContext(), String.valueOf(route.size()), Toast.LENGTH_SHORT).show();
        }
    }

    public void onEndWorkoutClicked(View v){
        Intent intent = new Intent(this, WorkoutEndActivity.class);
        startActivity(intent);
    }

}
