package com.example.e_supermarket.Pembeli.Activity;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsDetailPesananActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private Boolean oke = false;
    TextView latitudDetail;
    TextView longitudeDetail;
    TextView alamatDetail;

    private int Uid;
    private String UidPesanan;
    private String UnamaPemesan;
    private String UnamaBarangPesan;
    private String UmerkBarangPesan;
    private int UjumlahBarangPesan;
    private String UsatuanPesan;
    private String UgambarPesan;
    private String UhargaBarangPesan;
    private String UTotalHargaPesan;
    private String UongkirPesan;
    private String UalamatKirimPesan;
    private String Ulatitude;
    private String Ulongitude;
    private String UNohpPesan;
    private String UtglKirimPesan;
    private String UMetodeBayarPesan;
    private String UStatus;
    private String UStatus_pesanan;
    private String UuStatus_pesanan;
    private String UBukti_transfer;

    Button btnSimpanLokasiDetail;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_detail_pesanan);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnSimpanLokasiDetail = findViewById(R.id.btnsimpanlokasiMapsDetail);
        latitudDetail = findViewById(R.id.latMapsDetail);
        longitudeDetail = findViewById(R.id.longiMapsDetail);
        alamatDetail = findViewById(R.id.alamatMapsDetail);


        Bundle bundle = getIntent().getExtras();
        Uid = bundle.getInt("id");
        UidPesanan = bundle.getString("id_pesanan");
        UnamaPemesan = bundle.getString("nama");
        UNohpPesan = bundle.getString("no_hp");
        UalamatKirimPesan = bundle.getString("alamat");
        Ulatitude = bundle.getString("latitude");
        Ulongitude = bundle.getString("longitude");
        UnamaBarangPesan = bundle.getString("nama_barang");
        UmerkBarangPesan = bundle.getString("merk");
        UhargaBarangPesan = bundle.getString("harga");
        UjumlahBarangPesan = bundle.getInt("jumlah", 0);
        UsatuanPesan = bundle.getString("satuan");
        UgambarPesan = bundle.getString("gambar");
        UtglKirimPesan = bundle.getString("tanggal");
        UongkirPesan = bundle.getString("ongkir");
        UTotalHargaPesan = bundle.getString("total");
        UMetodeBayarPesan = bundle.getString("metode");
        UStatus = bundle.getString("status");
        UStatus_pesanan = bundle.getString("status_pesanan");
        UBukti_transfer = bundle.getString("bukti_transfer");

        geocoder = new Geocoder(this, Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);

        }

        btnSimpanLokasiDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("id", Uid);
                bundle1.putString("id_pesanan", UidPesanan);
                bundle1.putString("nama", UnamaPemesan);
                bundle1.putString("no_hp", UNohpPesan);
                bundle1.putString("alamat", alamatDetail.getText().toString());
                bundle1.putString("latitude", latitudDetail.getText().toString());
                bundle1.putString("longitude", longitudeDetail.getText().toString());
                bundle1.putString("nama_barang", UnamaBarangPesan);
                bundle1.putString("merk", UmerkBarangPesan);
                bundle1.putString("harga", UhargaBarangPesan);
                bundle1.putInt("jumlah", UjumlahBarangPesan);
                bundle1.putString("satuan", UsatuanPesan);
                bundle1.putString("gambar", UgambarPesan);
                bundle1.putString("tanggal", UtglKirimPesan);
                bundle1.putString("ongkir", UongkirPesan);
                bundle1.putString("total", UTotalHargaPesan);
                bundle1.putString("metode",UMetodeBayarPesan);
                bundle1.putString("status", UStatus);
                bundle1.putString("status_pesanan", UStatus_pesanan);
                bundle1.putString("bukti_transfer", UBukti_transfer);
                Intent intent = new Intent(MapsDetailPesananActivity.this, DetailPesananActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
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
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(Double.parseDouble(Ulatitude), Double.parseDouble(Ulongitude));
        mMap.addMarker(new MarkerOptions().position(sydney).title(UalamatKirimPesan).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
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
        if (oke){
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, new LocationListener() {
                List<Address> addressList = null;

                @Override
                public void onLocationChanged(@NonNull Location location) {
                    try {
                        addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (addressList.size() > 0 ) {
                            Address returnAddr = addressList.get(0);
                            StringBuilder stringBuilder = new StringBuilder("");
                            for (int i = 0; i < returnAddr.getMaxAddressLineIndex(); i++) {
                                stringBuilder.append(returnAddr.getAddressLine(i)).append("\n");
                            }
                            //Log.w("my Addres", stringBuilder.toString());
                        } else {
                            Log.w("my Addres", "no addres");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (oke) {
                        oke = false;
                        Address addres = addressList.get(0);
                        String adres1 = addres.getAddressLine(0);
                        LatLng lokasisekarang = new LatLng(addres.getLatitude(), addres.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(lokasisekarang).title(adres1).draggable(true));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasisekarang, 16));
                        latitudDetail.setText(String.valueOf(location.getLatitude()));
                        longitudeDetail.setText(String.valueOf(location.getLongitude()));
                        alamatDetail.setText(adres1);
                    }
                }


            });
        }


        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerDragListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        List<Address> addressList = null;
        LatLng latLng = marker.getPosition();
        try {
            addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
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
        if (oke == false) {
            String addres = addressList.get(0).getAddressLine(0);
            LatLng lokasisekarang = new LatLng(latLng.latitude, latLng.longitude);
            marker.setTitle(addres);
            marker.setPosition(lokasisekarang);
            //mMap.addMarker(new MarkerOptions().position(lokasisekarang).title(addres).draggable(true));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasisekarang, 16));
            latitudDetail.setText(String.valueOf(latLng.latitude));
            longitudeDetail.setText(String.valueOf(latLng.longitude));
            alamatDetail.setText(addres);
        }
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }
}