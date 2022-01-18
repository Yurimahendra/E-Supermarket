package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Penjual.Adapter.AdapterProfilePenjual;
import com.example.e_supermarket.Penjual.Fragment.ProfileFragmentPenjual;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HalamanProfilePenjualActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPenjual;
    FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private List<DataPenjual> dataPenjualList;
    private AdapterProfilePenjual adapterProfilePenjual;

    private FirebaseFirestore db;

    private SwipeRefreshLayout srlDataProduk;
    private ProgressBar pbDataProduk;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_penjual = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepenjual:
                    startActivity(new Intent(HalamanProfilePenjualActivity.this, HalamanUtamaPenjualActivity.class));
                    break;
                case R.id.notifpenjual:
                    startActivity(new Intent(HalamanProfilePenjualActivity.this, HalamanNotifPenjualActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpenjual:
                    startActivity(new Intent(HalamanProfilePenjualActivity.this, HalamanChatPenjualActivity.class));
                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpenjual:
                    //startActivity(new Intent(HalamanNotifPenjualActivity.this, HalamanProfilePenjualActivity.class));
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
        setContentView(R.layout.activity_halaman_profile_penjual);
        bottomNavigationViewPenjual = findViewById(R.id.nav_penjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(navigation_penjual);
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recProfilepenjual);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        db = FirebaseFirestore.getInstance();
        dataPenjualList = new ArrayList<>();
        adapterProfilePenjual = new AdapterProfilePenjual(HalamanProfilePenjualActivity.this, dataPenjualList);

        recyclerView.setAdapter(adapterProfilePenjual);

        srlDataProduk = findViewById(R.id.sw_dataproduk);
        pbDataProduk = findViewById(R.id.pb_dataproduk);

        srlDataProduk.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataProduk.setRefreshing(true);
                getProfilePenjual();
                srlDataProduk.setRefreshing(false);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getProfilePenjual();
    }

    private void getProfilePenjual() {

        db.collection("data_penjual").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        dataPenjualList.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            DataPenjual dataPenjual = new DataPenjual(/**snapshot.getString("id"),**/ snapshot.getLong("nik"), snapshot.getString("nama"), snapshot.getString("jenis_kelamin"), snapshot.getString("no_ponsel"), snapshot.getString("tempat_lahir"), snapshot.getString("tanggal_lahir"), snapshot.getString("alamat"), snapshot.getString("nama_toko"));
                            dataPenjualList.add(dataPenjual);
                        }
                        adapterProfilePenjual.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(HalamanProfilePenjualActivity.this, MainActivity.class);
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