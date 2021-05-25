package com.example.myapplication;

/**
 * @author sarah brennan 2962279
 */

import java.util.Arrays;

/**
 * used for arraylist of longitude and latitude of location where "treasure" is
 * will have a holder for clues also
 */
public class TreasureHunt {
    private double longitude;
    private double latitude;
    private String[] clues;

    public TreasureHunt(double longitude, double latitude, String[] clues) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.clues = clues;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getClue(int i) {
        return clues[i];
    }

    public String[] getClues() {
        return clues;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setClue(String clue, int i) {
        this.clues[i] = clue;
    }

    public void setClues(String[] clues) {
        this.clues = clues;
    }
}
