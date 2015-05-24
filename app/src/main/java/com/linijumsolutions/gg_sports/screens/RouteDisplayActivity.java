package com.linijumsolutions.gg_sports.screens;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_display);
        Bundle extra = getIntent().getBundleExtra("extra");
        if(extra != null) {
            route = (ArrayList<LatLng>) extra.getSerializable("route");
        }

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

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

            currentPoints.add(loc);

            if(currentPoints.size() > 1) {
                updateRouteInMap();
            }


            if(map != null){
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }
    };

    private void updateRouteInMap () {
        // should paint lines on roads however, on my emulator paints straight lines, you can try bitches ;(
        PolylineOptions options = new PolylineOptions().width(6).color(Color.BLUE);
        options.geodesic(true);

        for ( LatLng latlng : currentPoints )
        {
            options.add( latlng );
        }

        map.addPolyline( options );
    }


    public void onEndWorkoutClicked(View v){
        Intent intent = new Intent(this, WorkoutEndActivity.class);
        startActivity(intent);
    }

}
