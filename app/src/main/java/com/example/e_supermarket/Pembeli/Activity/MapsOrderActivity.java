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

import com.example.e_supermarket.Penjual.Activity.FormDataPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.PenjualMapsActivity;
import com.example.e_supermarket.Penjual.Server.RetroServer;
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

public class MapsOrderActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private Boolean oke = false;
    TextView latitudOrder;
    TextView longitudeOrder;
    TextView alamatOrder;

    Button btnSimpanMapsOrder;

    private String BeliNama_Barang;
    private String BeliMerk;
    private String BeliHarga;
    private int BeliMin_belanja;
    private int BeliMin_belanja1;
    private String BeliOngkir;
    private String BeliSatuan;
    private String BeliGambar;
    private String BeliDeskripsi;
    private int BeliId;

    private int minBelanjaPesan;
    private String TotalHargaPesan;
    private String ongkirPesan;
    private String NohpPesan;
    private String tglKirimPesan;
    private String MetodeBayarPesan;
    private String namaPemesan;

    private String LatToko;
    private  String LongToko;
    private String ConvOngkir1;
    private String ConvTotalHarga1;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_order);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        BeliId = bundle.getInt("id");
        BeliNama_Barang = bundle.getString("nama_barang");
        BeliMerk = bundle.getString("merk");
        BeliHarga = bundle.getString("harga");
        minBelanjaPesan = bundle.getInt("min_belanja", 0);
        //BeliMin_belanja1 = bundle.getInt("min_belanja", 0);
        BeliOngkir = bundle.getString("ongkir");
        BeliSatuan = bundle.getString("satuan");
        BeliGambar = bundle.getString("gambar");
        BeliDeskripsi = bundle.getString("deskripsi");
        //TotalHargaPesan = bundle.getString("total");
        //ongkirPesan = bundle.getString("ongkir");
        NohpPesan = bundle.getString("no_ponsel");
        tglKirimPesan = bundle.getString("tanggal");
        MetodeBayarPesan = bundle.getString("metode_bayar");
        namaPemesan = bundle.getString("nama");
        LatToko = bundle.getString("lattoko");
        LongToko = bundle.getString("longtoko");


        /*ConvOngkir = ongkirPesan.replace("Rp", "Rp. ");
        ConvOngkir1 = ConvOngkir.replace(",00", "");

        ConvTotalHarga = TotalHargaPesan.replace("Rp", "Rp. ");
        ConvTotalHarga1 = ConvTotalHarga.replace(",00", "");*/



        latitudOrder = findViewById(R.id.latMapsOrder);
        longitudeOrder = findViewById(R.id.longiMapsOrder);
        alamatOrder = findViewById(R.id.alamatMapsOrder);
        btnSimpanMapsOrder = findViewById(R.id.btnsimpanlokasiMapsOrder);
        btnSimpanMapsOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("id", BeliId);
                bundle1.putString("nama_barang", BeliNama_Barang);
                bundle1.putString("merk", BeliMerk);
                bundle1.putString("harga", BeliHarga);
                bundle1.putInt("min_belanja", minBelanjaPesan);
                bundle1.putString("ongkir", BeliOngkir);
                bundle1.putString("satuan", BeliSatuan);
                bundle1.putString("gambar", BeliGambar);
                bundle1.putString("deskripsi", BeliDeskripsi);
                bundle1.putString("latitude", latitudOrder.getText().toString());
                bundle1.putString("longitude", longitudeOrder.getText().toString());
                bundle1.putString("alamat", alamatOrder.getText().toString());
                //bundle1.putString("ongkir", ConvOngkir1);
                //bundle1.putString("total", ConvTotalHarga1);
                bundle1.putString("no_ponsel", NohpPesan);
                bundle1.putString("tanggal", tglKirimPesan);
                bundle1.putString("metode_bayar", MetodeBayarPesan);
                bundle1.putString("nama", namaPemesan);
                bundle1.putString("lattoko", LatToko);
                bundle1.putString("longtoko", LongToko);

                Intent intent = new Intent(MapsOrderActivity.this, BeliProdukActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        geocoder = new Geocoder(this, Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);

        }

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
                        latitudOrder.setText(String.valueOf(location.getLatitude()));
                        longitudeOrder.setText(String.valueOf(location.getLongitude()));
                        alamatOrder.setText(adres1);
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
        //oke = false;
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
            latitudOrder.setText(String.valueOf(latLng.latitude));
            longitudeOrder.setText(String.valueOf(latLng.longitude));
            alamatOrder.setText(addres);
        }
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }
}