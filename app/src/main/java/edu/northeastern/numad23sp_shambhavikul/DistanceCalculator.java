package edu.northeastern.numad23sp_shambhavikul;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DistanceCalculator {
    private double distance;
    private double latitude;
    private double longitude;
    private static DistanceCalculator instance = null;

    private DistanceCalculator() {
        this.distance = 0;
        this.longitude = 0;
        this.latitude = 0;
    }

    private DistanceCalculator(double distance, double latitude, double longitude) {
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private DistanceCalculator(double latitude, double longitude) {
        this.distance = 0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static DistanceCalculator getInstance() {
        if(instance == null) {
            instance = new DistanceCalculator();
        }
        return instance;
    }

    public double calculateDistance(double lat, double longi) {
        if(this.latitude == lat && this.longitude == longi) {
            return 0;
        }
        if(this.latitude == 0 || this.longitude == 0) {
            this.latitude = lat;
            this.longitude = longi;
            this.distance = 0;
        }
        // calculate distance from (this.latitude, this.longitude) to (lat, longi)
        double R = 6371;
        double dLat = deg2rad(lat - this.latitude);
        double dLong = deg2rad(longi - this.longitude);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(deg2rad(this.latitude)) * Math.cos(deg2rad(lat)) *
                        Math.sin(dLong/2) * Math.sin(dLong/2);

        double d = 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return d * 1000;
    }

    double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    public double addDistance(double lat, double longi) {
        double dist = calculateDistance(lat, longi);
        this.latitude = lat;
        this.longitude = longi;
        this.distance = this.distance + dist;
        return this.distance;
    }

    public static DistanceCalculator reset() {
        instance = new DistanceCalculator();
        return instance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
