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

public class PenjualMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Boolean oke = false;
    TextView latitudPenj;
    TextView longitudePenj;
    TextView alamatPenj;

    Button btnSimpanMapsPenjual;

    long nik;
    String nikS;
    int lenNik;
    String nama;
    String jenis_kelamin;
    String no_ponsel;
    String tempat_lahir;
    String tanggal_lahir;
    String alamat;
    String latitude;
    String longitude;
    String nama_toko;
    String nama_bank;
    long no_rekening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latitudPenj = findViewById(R.id.latPenjualMaps);
        longitudePenj = findViewById(R.id.longiPenjualMaps);
        alamatPenj = findViewById(R.id.alamatMapsPenjual);
        btnSimpanMapsPenjual = findViewById(R.id.btnsimpanlokasiPenjualMaps);

        Bundle bundle = getIntent().getExtras();
        nik = bundle.getLong("nik");
        nama = bundle.getString("nama_penjual");
        jenis_kelamin = bundle.getString("jenis_kelamin");
        no_ponsel = bundle.getString("no_ponsel");
        tempat_lahir = bundle.getString("tempat_lahir");
        tanggal_lahir = bundle.getString("tanggal_lahir");
        alamat = bundle.getString("alamat");
        nama_toko = bundle.getString("nama_toko");
        nama_bank = bundle.getString("nama_bank");
        no_rekening = bundle.getLong("no_rek");

       // Log.i("noponsel", ": "+no_ponsel);



        btnSimpanMapsPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("nik", nik);
                bundle.putString("nama_penjual", nama);
                bundle.putString("jenis_kelamin", jenis_kelamin);
                bundle.putString("no_ponsel", no_ponsel);
                bundle.putString("tempat_lahir", tempat_lahir);
                bundle.putString("tanggal_lahir", tanggal_lahir);
                bundle.putString("alamat", alamatPenj.getText().toString().trim());
                bundle.putString("latitude", latitudPenj.getText().toString());
                bundle.putString("longitude", longitudePenj.getText().toString().trim());
                bundle.putString("nama_toko", nama_toko);
                bundle.putString("nama_bank", nama_bank);
                bundle.putLong("no_rek", no_rekening);
               // Log.i("noponsel", ": "+no_ponsel);


                Intent intent = new Intent(PenjualMapsActivity.this, FormDataPenjualActivity.class);
                intent.putExtras(bundle);
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
                if (oke) {
                    String addres = addressList.get(0).getAddressLine(0);
                    LatLng lokasisekarang = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(lokasisekarang).title(addres));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasisekarang));
                    latitudPenj.setText(String.valueOf(location.getLatitude()));
                    longitudePenj.setText(String.valueOf(location.getLongitude()));
                    alamatPenj.setText(addres);
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
        oke = true;
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