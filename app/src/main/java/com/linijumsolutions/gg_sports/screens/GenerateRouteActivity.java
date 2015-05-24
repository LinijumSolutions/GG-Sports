package com.linijumsolutions.gg_sports.screens;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.linijumsolutions.gg_sports.R;
import com.linijumsolutions.gg_sports.controllers.RouteInformation;
import com.linijumsolutions.gg_sports.controllers.RouteGenerator;

public class GenerateRouteActivity extends Activity implements LocationListener {

    private GoogleMap googleMap;
    private RouteGenerator routeGenerator;
    private LatLng startPoint;
    private RouteInformation routeInformation;
    private String trainingType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_route);
        initializeMap();
        locationSettings();
        setWorkoutType();
        this.routeInformation = new RouteInformation();
    }

    private void setWorkoutType() {
        if(getIntent().getLongExtra("type", 0) == 1){
            trainingType = "driving";
        } else {
            trainingType = "walking";
        }
    }

    private void initializeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            if(googleMap != null) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.map_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void locationSettings(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            String bestProvider = locationManager.getBestProvider(new Criteria(), true);
            onLocationChanged(locationManager.getLastKnownLocation(bestProvider));
            locationManager.requestLocationUpdates(bestProvider, 10000, 10, this);
        }
    }

    public void onGenerateClicked(View v){
        EditText editField = (EditText) findViewById(R.id.rangeField);
        if(editField.getText().length() != 0) {
            if(routeInformation.generating == false) {
                routeInformation = new RouteInformation();
                googleMap.clear();
                routeGenerator = new RouteGenerator(startPoint, googleMap, trainingType, routeInformation);
                routeGenerator.DrawRoute(Integer.parseInt(editField.getText().toString()));
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.generating), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_distance), Toast.LENGTH_SHORT).show();
        }
    }

    public void onStartClicked(View v){
        if(routeInformation.generating == false && routeInformation.generated == true && routeInformation.getRouteLength() != 0) {
            Intent intent = new Intent(this, RouteDisplayActivity.class);
            Bundle extra = new Bundle();
            extra.putSerializable("route", routeInformation.getRoute());
            intent.putExtra("extra", extra);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.start_warning), Toast.LENGTH_SHORT).show();
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
