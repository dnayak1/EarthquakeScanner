package dhirajnayak.com.earthquakescanner.mapScreen;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Date;

import dhirajnayak.com.earthquakescanner.R;
import dhirajnayak.com.earthquakescanner.model.City;
import dhirajnayak.com.earthquakescanner.model.Feature;
import dhirajnayak.com.earthquakescanner.model.GeoPlace;
import dhirajnayak.com.earthquakescanner.model.MarkerDetails;
import dhirajnayak.com.earthquakescanner.utility.Constants;

import static dhirajnayak.com.earthquakescanner.utility.Constants.DEFAULT_ZOOM;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GeoPlace geoPlace;
    private City city;
    private LocationManager mLocationManager;
    private ArrayList<MarkerDetails> markerList;
    private ClusterManager<MarkerCluster> mClusterManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted=false;
    SupportMapFragment mapFragment;
    private Snackbar snackbar;
    private View parentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        parentView=findViewById(R.id.map);
        mapFragment.getMapAsync(this);
        markerList = new ArrayList<>();
        geoPlace = (GeoPlace) getIntent().getSerializableExtra(Constants.PLACES_KEY);
        city = (City) getIntent().getSerializableExtra(Constants.CITY_KEY);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        getLocationPermission();

        //getting coordinates for all markers
        for (Feature feature : geoPlace.getFeatures()) {
            MarkerDetails markerDetails = new MarkerDetails();
            markerDetails.setLatitude(Float.parseFloat(feature.getGeometry().getCoordinates().get(1)));
            markerDetails.setLongitude(Float.parseFloat(feature.getGeometry().getCoordinates().get(0)));
            markerDetails.setMagnitude(feature.getProperties().getMag());
            markerDetails.setTsunami(feature.getProperties().getTsunami());
            markerDetails.setTitle(feature.getProperties().getTitle());
            Date date = new Date(Long.parseLong(feature.getProperties().getTime()));
            markerDetails.setEventTime(date.toString());
            markerList.add(markerDetails);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mClusterManager = new ClusterManager<>(this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        // Add cluster items (markers) to the cluster manager.
        addItems(markerList);
        if(city==null){
            getDeviceLocation();
        }else{
            LatLng customLatLng=new LatLng(Double.parseDouble(city.getLat()),Double.parseDouble(city.getLon()));
            moveCamera(customLatLng,Constants.DEFAULT_ZOOM);
        }
        showMessageForNoData(geoPlace.getFeatures());

    }

    @Override
    protected void onResume() {
        super.onResume();
        //action for disabled gps
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enable GPS");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            builder.create().show();
        }
    }

    private void addItems(ArrayList<MarkerDetails> detailsArrayList) {

        // Add cluster items in close proximity
        for (MarkerDetails details : detailsArrayList) {
            int count = 0;
            double offset = count / 60d;
            double lat = details.getLatitude() + offset;
            double lng = details.getLongitude() + offset;
            String title = details.getTitle();
            String snippet = details.getMagnitude() + " on " + details.getEventTime();
            MarkerCluster offsetItem = new MarkerCluster(lat, lng, title, snippet);
            mClusterManager.addItem(offsetItem);
        }
    }

    public void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionsGranted){
                final Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e("LOG", "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    public void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,permissions,Constants.LOCATION_PERMISSION_REQUEST_CODE);
        }else{
            mLocationPermissionsGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0){
            for(int i = 0; i < grantResults.length; i++){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    mLocationPermissionsGranted = false;
                    return;
                }
            }
            mLocationPermissionsGranted = true;
        }
    }

    public void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void showMessageForNoData(ArrayList<Feature> features){
        if(features.size()==0){
            String message="No earthquake around ";
            if(city!=null){
                message=message+city.getName()+"!";
            }else{
                message=message+"the world!";
            }

            snackbar= Snackbar.make(parentView,message,Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }
}
