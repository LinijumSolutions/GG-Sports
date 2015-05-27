package com.linijumsolutions.gg_sports.models;

import com.google.android.gms.maps.model.LatLng;

public class RoutePointCoordinates {
    public LatLng[] point;
    int index = 0;

    public void insertPoint(LatLng p)
    {
        point[index++] = p;
    }

    public LatLng getPoint(int index) {return point[index];}

    public RoutePointCoordinates(int size) {
        point = new LatLng[size];
        index = 0;
    }
}
