package com.example.e_supermarket.Penjual.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Penjual.Adapter.AdapterProdukPenjualHU;
import com.example.e_supermarket.Penjual.Fragment.ChatFragmentPenjual;
import com.example.e_supermarket.Penjual.Fragment.HomeFragmentPenjual;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.Fragment.NotifFragmentPenjual;
import com.example.e_supermarket.Penjual.Fragment.ProfileFragmentPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanUtamaPenjualActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewPenjual;
    FirebaseAuth firebaseAuth;
    //private FirebaseStorage storage;
    //private StorageReference storageReference;
    public Uri imageUri;
    ImageView imageProduk;
    private SwipeRefreshLayout srlDataProduk;
    private ProgressBar pbDataProduk;

    private RecyclerView recyclerView;
    private List<DataProduk> dataProdukList = new ArrayList<>();
    private AdapterProdukPenjualHU adapterProdukPenjualHU;


    private BottomNavigationView.OnNavigationItemSelectedListener navigation_penjual = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepenjual:
                    //startActivity(new Intent());
                    break;
                case R.id.notifpenjual:
                     startActivity(new Intent(HalamanUtamaPenjualActivity.this, HalamanNotifPenjualActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpenjual:
                    startActivity(new Intent(HalamanUtamaPenjualActivity.this, HalamanChatPenjualActivity.class));

                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpenjual:
                    startActivity(new Intent(HalamanUtamaPenjualActivity.this, HalamanProfilePenjualActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutpenjual:
                    //Fp = new LogoutFragmentPenjual();
                    onBackPressed();
                    return true;

            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_penjual, Fp).commit();
            getContentTransitionManager();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama_penjual);
        srlDataProduk = findViewById(R.id.sw_dataproduk);
        pbDataProduk = findViewById(R.id.pb_dataproduk);


        recyclerView = (RecyclerView)findViewById(R.id.recItem);;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HalamanUtamaPenjualActivity.this, LinearLayoutManager.VERTICAL, false));

        //retrieveData();
        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationViewPenjual = findViewById(R.id.nav_penjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(navigation_penjual);

        srlDataProduk.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataProduk.setRefreshing(true);
                retrieveData();
                srlDataProduk.setRefreshing(false);
            }
        });


        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddDataActivityPenjual.class));
            }
        });
       // CheckUserStatus(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        pbDataProduk.setVisibility(View.VISIBLE);
        retrieveData();
        pbDataProduk.setVisibility(View.GONE);
    }

    public void retrieveData(){
        ApiRequestDataProduk requestDataProduk = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataProduk> tampilData = requestDataProduk.RetrieveDataProduk();

        tampilData.enqueue(new Callback<ResponseDataProduk>() {
            @Override
            public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(HalamanUtamaPenjualActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();

                dataProdukList = response.body().getData();

                adapterProdukPenjualHU = new AdapterProdukPenjualHU(HalamanUtamaPenjualActivity.this, dataProdukList);
                recyclerView.setAdapter(adapterProdukPenjualHU);
                adapterProdukPenjualHU.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDataProduk> call, Throwable t) {
                Toast.makeText(HalamanUtamaPenjualActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("Kamu yakin ingin keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        Intent intent = new Intent(HalamanUtamaPenjualActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }




}
