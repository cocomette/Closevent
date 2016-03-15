package com.closevent.closevent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng pin;
    int lat;
    int lng;
    private TextView rdArea;
    private int offset;
    private FloatingActionButton validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        rdArea=(TextView) findViewById(R.id.area);
        validate=(FloatingActionButton) findViewById(R.id.floattingButton);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        pin = new LatLng(-34, 151);
        lat=(int)(pin.longitude);
        lng=(int)(pin.latitude);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateActivity.editLocation.setText("("+lat+", "+lng+")");
                MapsActivity.this.finish();
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

    // 3. bitmap creation:

    private Bitmap getBitmap() {

        // fill color
        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(0x110000FF);
        paint1.setStyle(Paint.Style.FILL);

        // stroke color
        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(0xFF0000FF);
        paint2.setStyle(Paint.Style.STROKE);

        // circle radius - 200 meters
        int radius = 200;
        offset=radius;

        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.ic_place_24dp);

        // create empty bitmap
        Bitmap b = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);


        // draw icon
        c.drawBitmap(icon, radius - icon.getWidth() / 2, radius - icon.getHeight() / 2, new Paint());
        c.drawCircle(radius, radius, radius, paint1);
        c.drawCircle(radius, radius, radius, paint2);

        return b;
    }

    private LatLng getCoords(LatLng pin) {


        Projection proj = mMap.getProjection();
        Point p = proj.toScreenLocation(pin);
        p.set(p.x, p.y + offset);

        return proj.fromScreenLocation(p);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        rdArea.setText("Location: (-34, 151)");
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        pin = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(pin).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));


        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            private float currentZoom = -1;
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mMap.clear();
                MarkerOptions options = new MarkerOptions();
                options.position(getCoords(pin));
                options.icon(BitmapDescriptorFactory.fromBitmap(getBitmap()));
                mMap.addMarker(options);
                lat=(int)(pin.longitude);
                lng=(int)(pin.latitude);
                rdArea.setText("(Location: "+lat+", "+lng+")");
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                pin = latLng;
                lat=(int)(pin.longitude);
                lng=(int)(pin.latitude);
                mMap.clear();
                MarkerOptions options = new MarkerOptions();
                options.position(getCoords(pin));
                options.icon(BitmapDescriptorFactory.fromBitmap(getBitmap()));
                mMap.addMarker(options);
                rdArea.setText("(Location: "+lat+", "+lng+")");
            }
        });



    }

}
