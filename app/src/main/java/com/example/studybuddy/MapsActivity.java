package com.example.studybuddy;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.tool.util.L;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.studybuddy.databinding.ActivityMapsBinding;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.lang.System;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private SearchView mapSearchView;
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

//    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//            .findFragmentById(R.id.map);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.studybuddy.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //getLastLocation();

        mapSearchView = findViewById(R.id.mapSearch);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;

        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //Get and store user input
                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null) {

                    Geocoder geocoder = new Geocoder(MapsActivity.this);

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    assert addressList != null;
                    Address address = addressList.get(0);
                    LatLng latLing = new LatLng(address.getLatitude(), address.getLongitude());
                    myMap.addMarker(new MarkerOptions().position(latLing).title(location));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLing, 10));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);

        getLastLocation();

    }

    /*
    NOTE: getLastLocation only updates the marker on the map interface after google maps
    is opened on the users device.
     */

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    //System.out.println(currentLocation);
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    //assert mapFragment != null;
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap myMap) {

        float zoomLevel = 17;
        if (currentLocation!= null) {
            //System.out.println(currentLocation);
            LatLng currLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            myMap.addMarker(new MarkerOptions().position(currLocation).title("My Location"));
            myMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, zoomLevel));
        }

        LatLng wsuLibrary = new LatLng(42.26691278324695, -71.84326764564923);
        myMap.addMarker(new MarkerOptions().position(wsuLibrary).title("Study Group 1: Learning Resources Center/Library"));

        LatLng wsuSullivanCenter = new LatLng(42.26784964308041, -71.84206601596094);
        myMap.addMarker(new MarkerOptions().position(wsuSullivanCenter).title("Study Group 2: Sullivan Academic Center"));

        LatLng wsuWellnessCenter = new LatLng(42.269366056194755, -71.84410449468824);
        myMap.addMarker(new MarkerOptions().position(wsuWellnessCenter).title("Study Group 3: Wellness Center"));

        LatLng wsuScienceTechCenter = new LatLng(42.27014916593119, -71.84345668449474);
        myMap.addMarker(new MarkerOptions().position(wsuScienceTechCenter).title("Study Group 4: Ghosh Science & Tech Center"));

        LatLng wsuSheehanHall = new LatLng(42.267168451252104, -71.84478947785838);
        myMap.addMarker(new MarkerOptions().position(wsuSheehanHall).title("Study Group 5: Sheehan Hall"));



        // Add a marker in Worcester State and move the camera
        //LatLng worcesterStateUni = new LatLng(42.2681, -71.8443);
        //myMap.addMarker(new MarkerOptions().position(worcesterStateUni).title("Marker at Worcester State University"));
        //myMap.moveCamera(CameraUpdateFactory.newLatLng(worcesterStateUni));
        //myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(worcesterStateUni, zoomLevel));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission is denied. Please allow the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}