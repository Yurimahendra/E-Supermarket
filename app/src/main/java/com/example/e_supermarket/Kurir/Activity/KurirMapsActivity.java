package com.example.e_supermarket.Kurir.Activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.e_supermarket.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class KurirMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Boolean oke = false;
    TextView latitud;
    TextView longitude;
    TextView alamat;
    private String latitudeOrder;
    private String longitudeOrder;
    private String alamatOrder;

    double ConvLat;
    double ConvLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurir_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latitud = findViewById(R.id.lat);
        longitude = findViewById(R.id.longi);
        alamat = findViewById(R.id.alamatlatlon);

        Bundle bundle = getIntent().getExtras();
        latitudeOrder = bundle.getString("latitude");
        longitudeOrder = bundle.getString("longitude");
        alamatOrder = bundle.getString("alamat");

        ConvLat = Double.valueOf(latitudeOrder);
        ConvLong = Double.valueOf(longitudeOrder);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, new LocationListener() {
            List<Address> addressList = null;

            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addressList != null) {
                        Address returnAddr = addressList.get(0);
                        StringBuilder stringBuilder = new StringBuilder("");
                        for (int i = 0; i < returnAddr.getMaxAddressLineIndex(); i++) {
                            stringBuilder.append(returnAddr.getAddressLine(i)).append("\n");
                        }
                        Log.w("my Addres", stringBuilder.toString());
                    } else {
                        Log.w("my Addres", "no addres");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (oke) {
                    String addres = addressList.get(0).getAddressLine(0);
                    LatLng lokasisekarang = new LatLng(location.getLatitude(), location.getLongitude());
                    //mMap.addMarker(new MarkerOptions().position(lokasisekarang).title(addres));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasisekarang, 16));
                    //latitud.setText(String.valueOf(location.getLatitude()));
                    //longitude.setText(String.valueOf(location.getLongitude()));
                    //alamat.setText(addres);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        oke = true;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Add a marker in Sydney and move the camera
        LatLng phb = new LatLng(ConvLat, ConvLong);
        mMap.addMarker(new MarkerOptions().position(phb).title(alamatOrder));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(phb));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}