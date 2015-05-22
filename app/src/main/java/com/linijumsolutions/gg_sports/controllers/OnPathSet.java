package com.linijumsolutions.gg_sports.controllers;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class OnPathSet implements Navigator.OnPathSetListener {
    private ArrayList<LatLng> route = new ArrayList<>();
    public boolean isDone = true;

    @Override
    public void onPathSetListener(Directions directions, boolean isDone) {
        this.route.addAll(directions.getRoutes().get(0).getPath());
        this.isDone = isDone;
    }

    public ArrayList<LatLng> getRoute() {
        return route;
    }

}
