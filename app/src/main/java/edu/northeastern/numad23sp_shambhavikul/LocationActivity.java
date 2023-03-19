package edu.northeastern.numad23sp_shambhavikul;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.MessageFormat;

public class LocationActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    TextView latitudeTextView;
    TextView longitudeTextView;
    TextView distanceTextView;
    LocationRequest locationRequest;
    DistanceCalculator distanceCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        latitudeTextView = findViewById(R.id.location_latitude);
        longitudeTextView = findViewById(R.id.location_longitude);
        distanceTextView = findViewById(R.id.total_distance);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Button reset_distance_button = (Button) findViewById(R.id.reset_distance_button);
        if (reset_distance_button != null) {
            reset_distance_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetDistance();
                }
            });
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }

        distanceCalculator = DistanceCalculator.getInstance();

        locationRequest = locationRequest.create();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(50);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Instantiating the LocationCallBack
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        latitudeTextView.setText(MessageFormat.format("Latitude: {0}", location.getLatitude()));
                        longitudeTextView.setText(MessageFormat.format("Longitude: {0}", location.getLongitude()));
                        double distance = distanceCalculator.addDistance(location.getLatitude(), location.getLongitude());
                        distanceTextView.setText(MessageFormat.format("Distance: {0} m", distance));
                    }
                }
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void resetDistance() {
        this.distanceCalculator = DistanceCalculator.reset();
        distanceTextView.setText("Distance:");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Return Back")
                .setMessage("Are you sure you want to go back? Distance data will be lost.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetDistance();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationRequest = locationRequest.create();
            locationRequest.setInterval(100);
            locationRequest.setFastestInterval(50);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            // Instantiating the LocationCallBack
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        if (locationResult == null) {
                            return;
                        }
                        for (Location location : locationResult.getLocations()) {
                            latitudeTextView.setText(MessageFormat.format("Latitude: {0}", location.getLatitude()));
                            longitudeTextView.setText(MessageFormat.format("Longitude: {0}", location.getLongitude()));
                            double distance = distanceCalculator.addDistance(location.getLatitude(), location.getLongitude());
                            distanceTextView.setText(MessageFormat.format("Distance: {0} m", distance));
                        }
                    }
                }
            };
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }
}