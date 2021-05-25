package com.example.myapplication;

/**
 * @author sarah brennan 2962279
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

/* references for getting gps sensors to work:
 * https://www.youtube.com/watch?v=V62sxpyxapU&list=PLhPyEFL5u-i0vMDegCPbfD3ZNz69sf46I
 */

public class TreasureActivity extends AppCompatActivity {

    public static final int DEFAULT_UPDATE = 10;
    public static final int FASTEST_UPDATE = 5;
    private TextView scoreTV, distanceFromTreasureTV, clueTV;
    private Button newClueBtn, setTreasureHuntBtn, checkLocationBtn;
    private Spinner huntSpinner;
    private ArrayList<TreasureHunt> treasureHunt;
    private TreasureHunt th1, th2, th3;
    private String[] clues1, clues2, clues3;
    private double distance;
    private int amountClues, score, arrayLength = 10;
    private String clue, hunt, distanceFormatted;
    private static DecimalFormat df;

    // Google API used to get the last known location
    private FusedLocationProviderClient fusedLocationProviderClient;

    /* related to FusedLocationProviderClient
     * used to set the accuracy of the location, the higher the accuracy the more battery used
     * will also be used to get how often the location will be updated
     */
    private LocationRequest locationRequest;

    // used to update the location in real time
    private LocationCallback locationCallBack;

    private double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure);

        // give UI elements their values
        scoreTV = findViewById(R.id.scoreTV);
        distanceFromTreasureTV = findViewById(R.id.distanceFromTreasureLocationTV);
        clueTV = findViewById(R.id.clueTV);
        newClueBtn = findViewById(R.id.newClueBtn);
        // not allowed to click clue until treasure picked
        newClueBtn.setEnabled(false);
        setTreasureHuntBtn = findViewById(R.id.setTreasureHuntBtn);
        checkLocationBtn = findViewById(R.id.checkLocationBtn);
        huntSpinner = (Spinner) findViewById(R.id.huntSpinner);

        amountClues = 1;
        score = arrayLength;
        scoreTV.setText(String.valueOf(score));
        df = new DecimalFormat("0.00");

        locationRequest = new LocationRequest();

        // default time to update is 30 seconds
        locationRequest.setInterval(DEFAULT_UPDATE * 1000);

        // fastest time to update is 5 seconds when max power/accuracy being used
        locationRequest.setFastestInterval(FASTEST_UPDATE * 1000);

        // use GPS sensors  to update location, uses high battery power
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // a trigger for when the update is met (seconds it has been since app last updated)
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // gets the longitude and latitude of location to put into UI elements
                longitude = locationResult.getLastLocation().getLongitude();
                latitude = locationResult.getLastLocation().getLatitude();
            }
        };

        // update the longitude and latitude to see how close to the treasure phone is
        getGPS();

        // Treasure Hunt picking spinner, uses strings from string.xml to populate
        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(this,
                R.array.hunt_spinner, android.R.layout.simple_spinner_item);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        huntSpinner.setAdapter(genreAdapter);

        clues1 = new String[arrayLength];
        for(int i = 0; i < clues1.length; i++){
            clues1[i] = String.valueOf(i+1);
        }
        clues2 = new String[arrayLength];
        for(int i = 0; i < clues2.length; i++){
            clues2[i] = String.valueOf(i+1);
        }
        clues3 = new String[arrayLength];
        for(int i = 0; i < clues3.length; i++){
            clues3[i] = String.valueOf(i+1);
        }
        // add locations of treasure
        th1 = new TreasureHunt(-8.476735, 51.902765, clues1); // butter museum
        th2 = new TreasureHunt(-8.5708847, 51.9290922, clues2); // blarney castle
        th3 = new TreasureHunt(-8.4988676, 51.8990434, clues3); // cork city gaol
        treasureHunt = new ArrayList<>();
        treasureHunt.add(th1);
        treasureHunt.add(th2);
        treasureHunt.add(th3);

        // button to get which treasure hunt clues to load
        setTreasureHuntBtn.setOnClickListener((View.OnClickListener) v -> {
            hunt = huntSpinner.getSelectedItem().toString();
            if(hunt.equals("Treasure Hunt 1")){
                startGame(0);
            }else if(hunt.equals("Treasure Hunt 2")){
                startGame(1);
            }else if(hunt.equals("Treasure Hunt 3")){
                startGame(2);
            }
            //If genre item selected, show error message below
            else{
                Toast.makeText(this, "Select a Treasure Hunt to do", Toast.LENGTH_LONG).show();
            }
        });

        // button to get another clue
        newClueBtn.setOnClickListener((View.OnClickListener) v -> {
            hunt = huntSpinner.getSelectedItem().toString();
            if(amountClues < arrayLength){
                if(hunt.equals("Treasure Hunt 1")){
                    updateScore(0);
                }
                else if(hunt.equals("Treasure Hunt 2")){
                    updateScore(1);
                }
                else if(hunt.equals("Treasure Hunt 3")){
                    updateScore(2);
                }
                else {
                    Toast.makeText(this, "Select a hunt to choose", Toast.LENGTH_LONG).show();
                }
            } else{
                Toast.makeText(this, "No more clues to give", Toast.LENGTH_LONG).show();
            }
        });

        // button to check if near location of treasure
        checkLocationBtn.setOnClickListener((View.OnClickListener) v -> {
            hunt = huntSpinner.getSelectedItem().toString();
            if(hunt.equals("Treasure Hunt 1")){
                checkWinState(0);
            } else if(hunt.equals("Treasure Hunt 2")){
                checkWinState(1);
            } else if(hunt.equals("Treasure Hunt 3")){
                checkWinState(2);
            } else{
                Toast.makeText(this, "Please select a Treasure Hunt to check", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * when player selects which hunt they want to try, a clue is shown to screen,
     * the distance is checked and outputted to the screen,
     * spinner and setTreasureHuntBtn are disabled
     * @param i which treasure hunt is selected
     */

    private void startGame(int i) {
        clue = treasureHunt.get(i).getClue(0);
        clueTV.setText(clue);
        distance = distanceKM(longitude, latitude, treasureHunt.get(i).getLongitude(), treasureHunt.get(i).getLatitude());
        distanceFormatted = df.format(distance);
        distanceFromTreasureTV.setText(distanceFormatted + "km");
        setTreasureHuntBtn.setEnabled(false);
        huntSpinner.setEnabled(false);
        newClueBtn.setEnabled(true);
    }

    /**
     * if user looks for another clue, the score goes down by one point
     * @param i which treasure hunt is selected
     */
    private void updateScore(int i) {
        clue += "\n" + treasureHunt.get(i).getClue(amountClues);
        amountClues++;
        score--;
        scoreTV.setText(String.valueOf(score));
        clueTV.setText(clue);
    }

    /**
     * Looks at the distance of phone to treasure
     * if treasure is within 500m, player has won, otherwise keep going
     * @param i which treasure hunt is selected
     */
    @SuppressWarnings("deprecation")
    private void checkWinState(int i) {
        distance = distanceKM(longitude, latitude, treasureHunt.get(i).getLongitude(), treasureHunt.get(i).getLatitude());
        distanceFormatted = df.format(distance);
        distanceFromTreasureTV.setText(distanceFormatted + "km");
        if (distance < 0.05) {
            // do not need to update the location at set intervals
            fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
            Toast toast = Toast.makeText(this, "Winner!\n Your score was: " + score, Toast.LENGTH_LONG);
            // centre's text in toast
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            toast.show();
            Intent intent = new Intent(TreasureActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Still have further to go", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * get current location using FusedLocationProviderClient
     * get permission from user to allow gps when running app
     */
    public void getGPS(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(TreasureActivity.this);
        // check permission to use gps
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            // already have permission from user to use gps
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @SuppressLint("MissingPermission")
                @Override
                public void onSuccess(Location location) {
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
                    // gets the longitude and latitude of location to put into UI elements
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            });
        }
        else{
            // get permission from user to use gps
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // if statement automatically generated to check correct OS
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            }
        }
    }

    /**
     * takes in the longitude of where phone is and compares it to where treasure location is
     * https://stackoverflow.com/questions/6981916/how-to-calculate-distance-between-two-locations-using-their-longitude-and-latitu
     * @param long1 longitude of where positioned
     * @param lat1 latitude of where positioned
     * @param long2 longitude of treasure
     * @param lat2 latitude of treasure
     * @return returns the distance to the location needed in kilometers
     */
    public double distanceKM(double long1, double lat1, double long2, double lat2){
        double theta = long1 - long2;
        double distance = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;
        // changed to return kms so multiply by below
        return distance * 1.60934;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}