package com.steamybeans.drop.map;

import com.google.android.gms.maps.model.LatLng;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class MapTest extends DistanceCalculator{

    @org.junit.jupiter.api.Test

    @DisplayName("Test 1 - A drop over 1500km away")
    void oneThousandKilometreTest() {
        LatLng myPosition = new LatLng(10, 10);
        LatLng dropPosition = new LatLng(20, 20);
        DistanceCalculator calc = new DistanceCalculator();
        assertEquals(1544758, calc.distanceBetweenDropsInMetres(dropPosition, myPosition));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test 2 - A drop 1km away")
    void oneKilometreTest() {
        LatLng myPosition = new LatLng(35.5, 6);
        LatLng dropPosition = new LatLng(35.5, 6.01);
        DistanceCalculator calc = new DistanceCalculator();
        assertEquals(905, calc.distanceBetweenDropsInMetres(dropPosition, myPosition));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test 3 - A drop 0m away")
    void zeroMetreTest() {
        LatLng myPosition = new LatLng(35.5, 6.01);
        LatLng dropPosition = new LatLng(35.5, 6.01);
        DistanceCalculator calc = new DistanceCalculator();
        assertEquals(0, calc.distanceBetweenDropsInMetres(dropPosition, myPosition));
    }
}