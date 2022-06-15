package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class MapsEditPenjualActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Boolean okeEdt = false;
    TextView latitudPenjEdt;
    TextView longitudePenjEdt;
    TextView alamatPenjEdt;

    Button btnSimpanMapsPenjualEdt;

    private int uIdPnjl;
    private long uNikPnjl;
    private String uNamaPnjl;
    private String uJkPnjl;
    private String uNoponsPnjl;
    private String uTelaPnjl;
    private String uTalaPnjl;
    private String uAlamatPnjl;
    private String uNatoPnjl;
    private String uNamaBankPnjl;
    private long uNorekeningPnjl;
    private String uFtoPnjl;
    private String uLatPnjl;
    private String uLongPnjl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_edit_penjual);
        Bundle bundle = getIntent().getExtras();
        uIdPnjl = bundle.getInt("id");
        uNikPnjl = bundle.getLong("nik");
        uNamaPnjl = bundle.getString("nama_penjual");
        uJkPnjl = bundle.getString("jenis_kelamin");
        uNoponsPnjl = bundle.getString("no_ponsel");
        uTelaPnjl = bundle.getString("tempat_lahir");
        uTalaPnjl = bundle.getString("tanggal_lahir");
        uAlamatPnjl = bundle.getString("alamat");
        uNatoPnjl = bundle.getString("nama_toko");
        uNamaBankPnjl = bundle.getString("nama_bank");
        uNorekeningPnjl = bundle.getLong("no_rek");
        uLatPnjl = bundle.getString("latitude");
        uLongPnjl = bundle.getString("longitude");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latitudPenjEdt = findViewById(R.id.latPenjualMapsEdt);
        longitudePenjEdt = findViewById(R.id.longiPenjualMapsEdt);
        alamatPenjEdt = findViewById(R.id.alamatMapsPenjualEdt);
        btnSimpanMapsPenjualEdt = findViewById(R.id.btnsimpanlokasiPenjualMapsEdt);
        btnSimpanMapsPenjualEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("id", uIdPnjl);
                bundle1.putString("nama_penjual", uNamaPnjl);
                bundle1.putLong("nik", uNikPnjl);
                bundle1.putString("tempat_lahir", uTelaPnjl);
                bundle1.putString("tanggal_lahir", uTalaPnjl);
                bundle1.putString("jenis_kelamin", uJkPnjl);
                bundle1.putString("no_ponsel", uNoponsPnjl);
                bundle1.putString("nama_toko", uNatoPnjl);
                bundle1.putString("nama_bank", uNamaBankPnjl);
                bundle1.putLong("no_rek", uNorekeningPnjl);
                bundle1.putString("latitude", latitudPenjEdt.getText().toString());
                bundle1.putString("longitude", longitudePenjEdt.getText().toString().trim());
                bundle1.putString("alamat", alamatPenjEdt.getText().toString().trim());
                Intent intent = new Intent(MapsEditPenjualActivity.this, FormEditProfilePenjualActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

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
                if (okeEdt) {
                    String addres = addressList.get(0).getAddressLine(0);
                    LatLng lokasisekarang = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(lokasisekarang).title(addres));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasisekarang));
                    latitudPenjEdt.setText(String.valueOf(location.getLatitude()));
                    longitudePenjEdt.setText(String.valueOf(location.getLongitude()));
                    alamatPenjEdt.setText(addres);
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

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        okeEdt = true;
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