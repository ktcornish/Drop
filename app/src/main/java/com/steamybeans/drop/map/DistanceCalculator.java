package com.steamybeans.drop.map;

import com.google.android.gms.maps.model.LatLng;

public class DistanceCalculator {

    public double distanceBetweenDropsInMetres(LatLng dropLocation, LatLng userLocation) {
        double dropLatitude = dropLocation.latitude;
        double dropLongitude = dropLocation.longitude;
        double userLatitude = userLocation.latitude;
        double userLongitude = userLocation.longitude;
        double AVERAGE_RADIUS_OF_EARTH = 6371;

        double distanceBetweenLat = Math.toRadians(userLatitude - dropLatitude);
        double distanceBetweenLong = Math.toRadians(userLongitude - dropLongitude);

        double a = (Math.sin(distanceBetweenLat / 2) * Math.sin(distanceBetweenLat / 2)) +
                (Math.cos(Math.toRadians(userLatitude))) * (Math.cos(Math.toRadians(dropLatitude))) *
                        (Math.sin(distanceBetweenLong / 2)) * (Math.sin(distanceBetweenLong / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (double) (Math.round((AVERAGE_RADIUS_OF_EARTH * c) * 1000));

    }
}
