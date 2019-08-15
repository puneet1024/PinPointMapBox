package com.example.puneet.gotennatest;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;


import java.util.List;

public class MapActivity extends AppCompatActivity implements LocationEngineListener,PermissionsListener,com.mapbox.mapboxsdk.maps.OnMapReadyCallback{
    private static final String TAG = "MapActivity";
    private MapboxMap mapbox;
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private Marker destinationMarker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String pos = intent.getStringExtra("position");
        Float longitude = intent.getFloatExtra("longitude", 20);
        Float latitude = intent.getFloatExtra("latitude", 50);
        mapView.getMapAsync(this);
        mapView.getMapAsync(mapboxMap -> {
            MarkerOptions marker = new MarkerOptions();
            destinationMarker = mapboxMap.addMarker(marker.title(pos).position(new LatLng(latitude,longitude)));
            destinationMarker.showInfoWindow(mapboxMap,mapView);
        });
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        MapActivity.this.mapbox = mapboxMap;
        enableLocation();
    }

    private void enableLocation(){
        if(PermissionsManager.areLocationPermissionsGranted((this))) {
            initializeLocationEngine();
            initializeLocationLayer();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationEngine() {
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();
        locationEngine.addLocationEngineListener(this);

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null){
            originLocation = lastLocation;
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationLayer() {
        locationLayerPlugin = new LocationLayerPlugin(mapView,mapbox,locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
    }

    private void setCameraPosition(Location location) {
        mapbox.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 5.0));
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null ) {
            originLocation = location;
            setCameraPosition(location);
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "Need Permission", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation();
        } else {
            Toast.makeText(this,"user permission not granted " ,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        if (locationEngine != null)
            locationEngine.requestLocationUpdates();
        if(locationLayerPlugin != null)
            locationLayerPlugin.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        if (locationEngine!=null)
            locationEngine.removeLocationUpdates();
        if(locationLayerPlugin!=null)
            locationLayerPlugin.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationEngine != null)
            locationEngine.deactivate();
        mapView.onDestroy();
    }
}
