package com.linijumsolutions.gg_sports.controllers;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class RouteInformation implements Navigator.OnPathSetListener {
    private ArrayList<LatLng> route;
    public boolean generating;
    public boolean generated;

    public RouteInformation() {
        this.generating = false;
        this.generated = false;
        this.route = new ArrayList<LatLng>();
    }

    public ArrayList<LatLng> getRoute() {
        return route;
    }

    public int getRouteLength() { return route.size(); }

    @Override
    public void onPathSetListener(Directions directions, boolean generating) {
        if(directions.getRoutes().size() > 0){
            this.route.addAll(directions.getRoutes().get(0).getPath());
        }
        this.generating = generating;
        if(generating == false){ generated = true; }
    }

}
