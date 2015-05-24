package com.linijumsolutions.gg_sports.controllers;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.linijumsolutions.gg_sports.models.RoutePointCoordinates;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RouteGenerator implements Cloneable{
    public RoutePointCoordinates PointsList;
    private int _i;
    public static float[] distances = new float[4];
    public static float distance;

    private RouteGenerator _previousRoute;
    private double DegreeLat = 111;
    private double DegreeLng = 67.6;

    private LatLng _startCoordinates;
    private String trainingType;
    public double Distance;
    private RouteInformation routeInformation;

    public GoogleMap Gmap;

    public RouteGenerator(LatLng startPoint, GoogleMap userMapControl, String trainingType, RouteInformation routeInformation)
    {
        this.trainingType = trainingType;
        this.routeInformation = routeInformation;
        this.Gmap = userMapControl;
        this._startCoordinates = startPoint;
        this.PointsList = new RoutePointCoordinates(4);
        this.Gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(_startCoordinates, 12));

    }

    public RouteGenerator()
    {
        PointsList = new RoutePointCoordinates(4);
    }

    public void Add(LatLng point, double distance)
    {
        PointsList.insertPoint(point);
        Distance += distance;
        if (_i == 4) _i = 0;
    }

    public Object Clone()
    {
        RouteGenerator routeClone = new RouteGenerator();
        routeClone.Distance = this.Distance;

        for (int i = 0; i < 4; i++)
            routeClone.PointsList.insertPoint(this.PointsList.getPoint(i));

        return routeClone;
    }

    public boolean Equals(Object obj)
    {
        if (obj == null) return false;

        RouteGenerator route = (RouteGenerator)obj;

        return Distance == route.Distance;
    }

    public void DrawRoute(float distance)
    {
        routeInformation.generating = true;
        while (true)
        {
            DrawRouteLocal(_startCoordinates, distance);
            if (_previousRoute == null || this.Equals(_previousRoute) == false)
            {
                try {
                    _previousRoute = (RouteGenerator) this.clone();
                } catch(CloneNotSupportedException e)
                {}
                break;
            }
        }
    }

    private void DrawRouteLocal(LatLng startPoint, float distance)
    {
        Distance = 0;
        LatLng[] interPoints = new LatLng[4];

        Map<Double,Double> degreeDecimalsLat = new HashMap<>();
        Map<Double,Double> degreeDecimalsLng = new HashMap<>();

        double mp = 1;
        double value = 1;
        for (int i = 1; i < 5; i++)
        {
            degreeDecimalsLat.put(DegreeLat / mp, value);
            degreeDecimalsLng.put(DegreeLng / mp, value);
            mp *= 10;
            value /= 10;
        }

        double tempPoint = startPoint.latitude;

        double decimalDegLat = determineDecimalLat(distance);
        double decimalDegLng = determineDecimalLng(distance);
        int stepLat = determineStep(distance, decimalDegLat);
        int stepLng = determineStep(distance, decimalDegLng);

        int directionLat = 0, directionLng = 0;

        for (int i = 0; i < 4; i++)
        {
            Random rnd = new Random();
            int change = rnd.nextInt(2);

            if (i == 0)
            {
                directionLat = change;
                double rr = degreeDecimalsLat.get(decimalDegLat);
                if (change == 0)
                    tempPoint = startPoint.latitude - stepLat * rr;

                if (change == 1)
                    tempPoint = startPoint.latitude + stepLat * rr;
                interPoints[0] = new LatLng(tempPoint, startPoint.longitude);
            }

            if (i == 1)
            {
                directionLng = change;
                double rr = degreeDecimalsLng.get(decimalDegLng);
                if (change == 0)
                    tempPoint = interPoints[0].longitude + stepLng * rr;
                if (change == 1)
                    tempPoint = interPoints[0].longitude - stepLng * rr;
                interPoints[1] = new LatLng(interPoints[0].latitude, tempPoint);
                startPoint = interPoints[0];
            }

            if (i == 2)
            {
                double rr = degreeDecimalsLat.get(decimalDegLat);
                if (directionLat == 0)
                    tempPoint = interPoints[1].latitude + stepLat * rr;
                if (directionLat == 1)
                    tempPoint = interPoints[1].latitude - stepLat * rr;
                interPoints[2] = new LatLng(tempPoint, interPoints[1].longitude);
                startPoint = interPoints[1];
            }

            if (i == 3)
            {
                double rr = degreeDecimalsLng.get(decimalDegLng);
                if (directionLng == 0)
                    tempPoint = interPoints[2].longitude - stepLng * rr;
                if (directionLng == 1)
                    tempPoint = interPoints[2].longitude + stepLng * rr;
                interPoints[3] = new LatLng(interPoints[2].latitude, tempPoint);
                startPoint = interPoints[2];
            }

            rnd.nextInt();

            if (DrawLine(startPoint, interPoints[i], false, null, i) == false)
                return;
        }

    }

    private int determineStep(float dist, double decimalDeg)
    {
        dist /= 4;

        for (int i = 10; i > 0; i--)
            if (decimalDeg * i > dist)
                continue;
            else
                return i;

        return (int)dist;
    }

    private double determineDecimalLat(float dist)
    {
        if (dist <= 4)
            return DegreeLat / 1000;

        if (dist <= 40)
            return DegreeLat / 100;

        if (dist <= 400)
            return DegreeLat / 10;

        return DegreeLat;
    }

    private double determineDecimalLng(float dist)
    {
        if (dist <= 4)
            return DegreeLng / 1000;

        if (dist <= 40)
            return DegreeLng / 100;

        if (dist <= 400)
            return DegreeLng / 10;

        return DegreeLng;
    }

    private boolean DrawLine(LatLng start, LatLng end, boolean loading, RouteGenerator rr, int index)
    {
        float [] localDistances = new float[3];
        boolean last = false;
        if (index == 3) { last = true; }
        Navigator nav = new Navigator(Gmap, start, end, trainingType, last);
        nav.setOnPathSetListener(routeInformation);
        nav.findDirections(false);

        Location.distanceBetween(start.latitude, start.longitude, end.latitude, end.longitude, localDistances);

        distance += localDistances[0];

        return true;
    }

}
