package com.example.myapplication;

/**
 * @author sarah brennan 2962279
 */

import androidx.annotation.NonNull;
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
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/* references for getting gps sensors to work:
 * https://www.youtube.com/watch?v=V62sxpyxapU&list=PLhPyEFL5u-i0vMDegCPbfD3ZNz69sf46I
 */
public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_UPDATE = 10;
    public static final int FASTEST_UPDATE = 5;
    final String turnTrackerOn = "Turn Tracker On", turnTrackerOff = "Turn Tracker Off";
    // create UI elements
    private TextView currentLatTV, currentLongTV;
    private Switch trackerSW;
    private Button goToTreasureBtn;
    private Chronometer timer;

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

    /**
      * initialise UI elements, set up current longitude and latitude, create on click listeners for buttons
      * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // give UI elements their values
        currentLatTV = findViewById(R.id.currentLatTV);
        currentLongTV = findViewById(R.id.currentLongTV);
        trackerSW = findViewById(R.id.trackerSW);
        trackerSW.setText(turnTrackerOn);
        goToTreasureBtn = findViewById(R.id.goToTreasureActivityBtn);
        timer = (Chronometer) findViewById(R.id.timerCMMain);

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
                longitude = locationResult.getLastLocation().getLongitude();
                latitude = locationResult.getLastLocation().getLatitude();
                // gets the longitude and latitude of location to put into UI elements
                currentLongTV.setText(String.valueOf(longitude));
                currentLatTV.setText(String.valueOf(latitude));
            }
        };

        // switch to turn on/off tracking
        trackerSW.setOnClickListener((View.OnClickListener) v -> {
            if (trackerSW.isChecked()) {
                // change text as turning on
                trackerSW.setText(turnTrackerOff);
                // reset time on clock
                timer.setBase(SystemClock.elapsedRealtime());
                // start timer
                timer.start();
                timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    public void onChronometerTick(Chronometer arg0) {
                        long minutes = ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000) / 60;
                        long seconds = ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000) % 60;
                        long elapsedTime = SystemClock.elapsedRealtime();
                    }
                });
                getGPS();
            } else {
                // change text as turning off
                trackerSW.setText(turnTrackerOn);
                // stop timer
                timer.stop();
                // do not need to update the location at set intervals
                fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
            }
        });

        // button to view TreasureActivity
        goToTreasureBtn.setOnClickListener((View.OnClickListener) v -> {
            if(trackerSW.isChecked()) {
                Intent intent = new Intent(MainActivity.this, TreasureActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Turn on tracker before you can choose treasure map", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case 99:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getGPS();
                } else{
                    Toast.makeText(this, "Need permission to use app correctly", Toast.LENGTH_LONG).show();
                    // exit program
                    finish();
                }
                break;
        }
    }

    /**
     * get current location using FusedLocationProviderClient
     * get permission from user to allow gps when running app
     */
    public void getGPS(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
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
                    currentLongTV.setText(String.valueOf(longitude));
                    currentLatTV.setText(String.valueOf(latitude));
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
}