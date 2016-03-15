package com.closevent.closevent;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng pin;
    private TextView rdArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        rdArea=(TextView) findViewById(R.id.area);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        pin = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(pin).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));

        //LatLng latLngRight = mMap.getProjection().getVisibleRegion().farRight;
        //LatLng latLngLeft = mMap.getProjection().getVisibleRegion().nearLeft;
        double radius = mMap.getCameraPosition().zoom/(mMap.getMaxZoomLevel()-mMap.getMinZoomLevel());
        //double radius = Math.abs(latLngLeft.longitude - latLngRight.longitude)/4;
        mMap.addCircle(new CircleOptions().center(pin).radius(radius * 100));
        rdArea.setText("");

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mMap.clear();
                //LatLng latLngRight = mMap.getProjection().getVisibleRegion().farRight;
                //LatLng latLngLeft = mMap.getProjection().getVisibleRegion().nearLeft;
                mMap.addMarker(new MarkerOptions().position(pin));
                //double radius = Math.abs(latLngLeft.longitude - latLngRight.longitude) / 4;
                double radius = (mMap.getMaxZoomLevel()-mMap.getMinZoomLevel())/(mMap.getCameraPosition().zoom-mMap.getMinZoomLevel());
                if (radius > 30) {
                    radius = 30;
                }
                System.out.println(radius);
                mMap.addCircle(new CircleOptions().center(pin).radius(radius * 100));
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                pin = latLng;
                mMap.clear();
                //LatLng latLngRight = mMap.getProjection().getVisibleRegion().farRight;
                //LatLng latLngLeft = mMap.getProjection().getVisibleRegion().nearLeft;
                mMap.addMarker(new MarkerOptions().position(pin));
                //double radius = Math.abs(latLngLeft.longitude - latLngRight.longitude) / 4;
                double radius = mMap.getCameraPosition().zoom/(mMap.getMaxZoomLevel()-mMap.getMinZoomLevel());
                mMap.addCircle(new CircleOptions().center(pin).radius(radius * 100));
            }
        });



    }

}
