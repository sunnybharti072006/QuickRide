package com.QuickRide.entity;

import java.io.Serializable;

public class GpsLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    private double lat;
    private double lng;

    public GpsLocation() {}

    public GpsLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}