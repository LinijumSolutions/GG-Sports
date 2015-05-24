package com.linijumsolutions.gg_sports.screens;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.linijumsolutions.gg_sports.R;

import java.util.ArrayList;

public class RouteDisplayActivity extends FragmentActivity {
    private GoogleMap map;
    private ArrayList<LatLng> route;
    private ArrayList<LatLng> currentPoints = new ArrayList<LatLng>();
    private Boolean isPaused = false;


    protected Handler customHandler = new Handler();

    // time values
    private long startTime = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    // Nagliui reikiamas visas laikas užtruktas
    long timeInMilliseconds = 0L;
    long lastPausedTimeInMilliseconds = 0L;
    long lastAddedToTotalPausedTimeInMilliseconds = 0;
    long totalPausedTimeInMilliseconds = 0L;
    // Nagliui reikiamas visas atstumas metrais
    float distanceInMeters = 0;
    float lastDistanceIntervalInMeters = 0;
    long lastFixedTimeOnUpdateLocationInMilliseconds = 0L;
    long secondLastFixedTimeOnUpdateLocationInMilliseconds = 0L;

    // views
    private TextView timerValue;
    private TextView speedValue;
    private TextView currentSpeedValue;
    private TextView distanceValue;
    private Button pauseButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_display);
        Bundle extra = getIntent().getBundleExtra("extra");
        if(extra != null) {
            route = (ArrayList<LatLng>) extra.getSerializable("route");
        }

        // Set views
        timerValue = (TextView)findViewById(R.id.textView5);
        speedValue = (TextView)findViewById(R.id.textView11);
        currentSpeedValue = (TextView)findViewById(R.id.textView7);
        distanceValue = (TextView)findViewById(R.id.textView3);
        pauseButton = (Button)findViewById(R.id.button5);

        setupMap();
        startTimer();
    }

    private void setupMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();

        if(route != null){ map.addPolyline(new PolylineOptions().addAll(route).color(Color.rgb(204,0,0)).width(6)); }

        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.animateCamera(CameraUpdateFactory.zoomTo(16));

        map.setOnMyLocationChangeListener(myLocationChangeListener);
    }

    private void startTimer() {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            if(!isPaused) {

                if(lastPausedTimeInMilliseconds != lastAddedToTotalPausedTimeInMilliseconds) {
                    totalPausedTimeInMilliseconds += lastPausedTimeInMilliseconds;
                    lastAddedToTotalPausedTimeInMilliseconds = lastPausedTimeInMilliseconds;
                }

                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timeSwapBuff + timeInMilliseconds - totalPausedTimeInMilliseconds;
                float lastUpdatedTime = timeSwapBuff + lastFixedTimeOnUpdateLocationInMilliseconds;
                int totalsecs = (int) (updatedTime / 1000);
                int lastsecs = (int) (lastUpdatedTime / 1000);
                int mins = totalsecs / 60;
                int secs = totalsecs % 60;
                int milliseconds = (int) (updatedTime % 1000);
                timerValue.setText("" + mins + ":"
                        + String.format("%02d", secs) + ":"
                        + String.format("%03d", milliseconds));

                // Nagliui reikia vidutinio greicio formule apskaiciavimui
                speedValue.setText((double) Math.round((distanceInMeters / totalsecs * (18 / 5)) * 10) / 10 + " km/h");
                currentSpeedValue.setText((double) Math.round((lastDistanceIntervalInMeters / (totalsecs - lastsecs) * (18 / 5)) * 10) / 10 + " km/h");
            }
            else {
                lastPausedTimeInMilliseconds = SystemClock.uptimeMillis() - startTime - timeInMilliseconds;
            }
            customHandler.postDelayed(this, 0);
        }
    };


    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            if(!isPaused) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

                currentPoints.add(loc);

                if(currentPoints.size() > 1) {
                    updateRouteInMap();
                    updateStatistics();
                }

                if(map != null){
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                }
            }
        }
    };

    private void updateStatistics () {
        if(!isPaused) {
            for (int i = 0; i < currentPoints.size() - 1; i++) {
                Location location1 = new Location("");
                location1.setLatitude(currentPoints.get(i).latitude);
                location1.setLongitude(currentPoints.get(i).longitude);

                Location location2 = new Location("");
                location2.setLatitude(currentPoints.get(i + 1).latitude);
                location2.setLongitude(currentPoints.get(i + 1).longitude);

                distanceInMeters += location1.distanceTo(location2);
                lastDistanceIntervalInMeters = location1.distanceTo(location2);
            }
            if (currentPoints.size() > 2) {
                secondLastFixedTimeOnUpdateLocationInMilliseconds = lastFixedTimeOnUpdateLocationInMilliseconds;
                lastFixedTimeOnUpdateLocationInMilliseconds = timeInMilliseconds;
            }

            distanceValue.setText((double) Math.round((distanceInMeters / 1000) * 10) / 10 + " km");
        }

    }

    private void updateRouteInMap () {
        if(!isPaused) {
            // should paint lines on roads however, on my emulator paints straight lines, you can try bitches ;(
            PolylineOptions options = new PolylineOptions().width(6).color(Color.BLUE);
            options.geodesic(true);

            for (LatLng latlng : currentPoints) {
                options.add(latlng);
            }

            map.addPolyline(options);
        }
    }


    public void onEndWorkoutClicked(View v){
        Intent intent = new Intent(this, WorkoutEndActivity.class);
        startActivity(intent);
    }

    public void onPauseClicked(View v) {
        if(isPaused) {
            isPaused = false;
            pauseButton.setText(getString(R.string.pause));
        } else {
            isPaused = true;
            pauseButton.setText(getString(R.string.mygtukasPradeti));
        }
    }

}
