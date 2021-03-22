package com.example.a9ld;

import androidx.fragment.app.FragmentActivity;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private EditText mAddress;
    private EditText mMarkerTitle;

    private GoogleMap mMap;

    private List<MarkerEntity> markerList = new ArrayList<>();
    private MarkerRoomDatabase database;

    private HashMap<String,Marker> hashMapMarker = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mAddress = findViewById(R.id.address);
        mMarkerTitle = findViewById(R.id.markerTitle);

        database = MarkerRoomDatabase.getInstance(this);
        markerList = database.markerDao().getAll();
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
        loadMarkers();
    }

    public void addMarker(View view) {

        String userInput = mAddress.getText().toString();
        String markerTitle = mMarkerTitle.getText().toString();

        LatLng location = getLocationFromAddress(userInput);

        double lat = location.latitude;
        double lng = location.longitude;

        saveMarker(lat, lng, markerTitle);

        Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(markerTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        hashMapMarker.put(markerTitle,marker);
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                Log.d("yes", "its null");
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return p1;
    }

    public void saveMarker(double lat, double lng, String title){
        MarkerEntity data = new MarkerEntity();
        data.setMlatitude(lat);
        data.setMlongitude(lng);
        data.setMtitle(title);

        database.markerDao().insert(data);

        mAddress.setText("");
        mMarkerTitle.setText("");
        markerList.clear();
        markerList.addAll(database.markerDao().getAll());
    }

    public void removeMarker(View view) {

        String markerTitle = mMarkerTitle.getText().toString();

        database.markerDao().deleteByTitle(markerTitle);

        mMarkerTitle.setText("");
        markerList.clear();
        markerList.addAll(database.markerDao().getAll());

        Marker marker = hashMapMarker.get(markerTitle);
        marker.remove();
        hashMapMarker.remove(markerTitle);
    }

    public void loadMarkers() {
        double lat;
        double lng;
        String title;
        LatLng latlng = null;

        if(markerList.size()>0) {
            for (int i = 0; i < markerList.size(); i++) {

                MarkerEntity data = markerList.get(i);
                lat = data.getMlatitude();
                lng = data.getMlongitude();
                title = data.getMtitle();

                latlng = new LatLng(lat, lng);

                Marker marker = mMap.addMarker(new MarkerOptions().position(latlng).title(title));

                hashMapMarker.put(title,marker);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        }
    }
}