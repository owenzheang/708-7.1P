package com.example.a70871p;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    FusedLocationProviderClient fusedLocationClient;
    DatabaseHelper databaseHelper;
    ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        databaseHelper = new DatabaseHelper(this);
        itemList = databaseHelper.getAllItems();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    101
            );
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {

                    LatLng userLocation;

                    if (location != null) {
                        userLocation = new LatLng(
                                location.getLatitude(),
                                location.getLongitude()
                        );
                    } else {
                        userLocation = new LatLng(-37.8136, 144.9631);
                    }

                    double radiusKm = 30.0;

                    for (Item item : itemList) {

                        float[] results = new float[1];

                        android.location.Location.distanceBetween(
                                userLocation.latitude,
                                userLocation.longitude,
                                item.latitude,
                                item.longitude,
                                results
                        );

                        double distanceKm = results[0] / 1000;

                        if (distanceKm <= radiusKm) {

                            LatLng itemPosition =
                                    new LatLng(item.latitude, item.longitude);

                            mMap.addMarker(new MarkerOptions()
                                    .position(itemPosition)
                                    .title(item.type + ": " + item.name)
                                    .snippet(item.description));
                        }
                    }

                    mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(userLocation, 12)
                    );
                });
    }

}
