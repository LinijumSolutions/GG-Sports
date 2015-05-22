package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.linijumsolutions.gg_sports.R;
import com.linijumsolutions.gg_sports.controllers.OnPathSet;
import com.linijumsolutions.gg_sports.controllers.RouteGenerator;

public class GenerateRouteActivity extends Activity implements LocationListener {

    private GoogleMap googleMap;
    private RouteGenerator routeGenerator;
    private LatLng startPoint;
    private OnPathSet onPathSet = new OnPathSet();
    private String trainingType = "walking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_route);
        initializeMap();
        locationSettings();
        if(getIntent().getLongExtra("type", 0) == 1){
            trainingType = "driving";
        }
    }

    private void initializeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            if(googleMap != null) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(getApplicationContext(), "Unable to create map!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void locationSettings(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            String bestProvider = locationManager.getBestProvider(new Criteria(), true);
            onLocationChanged(locationManager.getLastKnownLocation(bestProvider));
            locationManager.requestLocationUpdates(bestProvider, 5000, 10, this);
        }
    }

    public void onGenerateClicked(View v){
        EditText editField = (EditText) findViewById(R.id.rangeField);
        if(editField.getText().length() != 0) {
            if(onPathSet.isDone == true) {
                onPathSet = new OnPathSet();
                onPathSet.isDone = false;
                routeGenerator = new RouteGenerator(startPoint.latitude, startPoint.longitude, googleMap, trainingType, onPathSet);
                googleMap.clear();
                routeGenerator.DrawRoute(startPoint, Integer.parseInt(editField.getText().toString()));
            } else {
                Toast.makeText(getApplicationContext(), "Marsrutas vis dar generuojamas!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Nenurodytas atstumas!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            startPoint = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(startPoint));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
