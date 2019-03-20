package com.steamybeans.drop.firebase;

public class Firebasemarker {

    public double longitude;
    public double latitude;
    public String content;

    public Firebasemarker() {

    }

    public Firebasemarker(double longitude, double latitude, String content) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.content = content;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getContent() {
        return content;
    }
}
