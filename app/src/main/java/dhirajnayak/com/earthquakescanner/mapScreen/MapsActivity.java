package dhirajnayak.com.earthquakescanner.mapScreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Date;

import dhirajnayak.com.earthquakescanner.R;
import dhirajnayak.com.earthquakescanner.model.Feature;
import dhirajnayak.com.earthquakescanner.model.GeoPlace;
import dhirajnayak.com.earthquakescanner.model.MarkerDetails;
import dhirajnayak.com.earthquakescanner.utility.Constants;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GeoPlace geoPlace;
    private LocationManager mLocationManager;
    ArrayList<MarkerDetails> markerList;
    private ClusterManager<MarkerCluster> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markerList=new ArrayList<>();
        geoPlace= (GeoPlace) getIntent().getSerializableExtra(Constants.PLACES_KEY);
        mLocationManager= (LocationManager) getSystemService(LOCATION_SERVICE);

        //getting coordinates for all markers
        for(Feature feature:geoPlace.getFeatures()){
            MarkerDetails markerDetails=new MarkerDetails();
            markerDetails.setLatitude(Float.parseFloat(feature.getGeometry().getCoordinates().get(1)));
            markerDetails.setLongitude(Float.parseFloat(feature.getGeometry().getCoordinates().get(0)));
            markerDetails.setMagnitude(feature.getProperties().getMag());
            markerDetails.setTsunami(feature.getProperties().getTsunami());
            markerDetails.setTitle(feature.getProperties().getTitle());
            Date date=new Date(Long.parseLong(feature.getProperties().getTime()));
            markerDetails.setEventTime(date.toString());
            markerList.add(markerDetails);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mClusterManager=new ClusterManager<MarkerCluster>(this,mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        // Add cluster items (markers) to the cluster manager.
        addItems(markerList);
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
        for (MarkerDetails details:detailsArrayList) {
            int count=0;
            double offset = count / 60d;
            double lat = details.getLatitude() + offset;
            double lng = details.getLongitude() + offset;
            String title=details.getTitle();
            String snippet=details.getMagnitude()+" on "+details.getEventTime();
            MarkerCluster offsetItem = new MarkerCluster(lat, lng,title,snippet);
            mClusterManager.addItem(offsetItem);
        }
    }
}
