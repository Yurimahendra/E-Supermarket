package com.example.e_supermarket.Kurir.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Pembeli.Activity.ChatPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.OrderanPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.ProfilePembeliActivity;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HalamanUtamaKurirActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewKurir;
    private SwipeRefreshLayout srlDataPesanan;
    private ProgressBar pbDataPesanan;


    private BottomNavigationView.OnNavigationItemSelectedListener navigation_Kurir = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homekurir:
                    //startActivity(new Intent());
                    break;
                case R.id.profilkurir:
                    startActivity(new Intent(HalamanUtamaKurirActivity.this, HalamanProfilKurirActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutkurir:
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
        setContentView(R.layout.activity_halaman_utama_kurir);
       /* srlDataPesanan = findViewById(R.id.sw_data);
        pbDataPesanan = findViewById(R.id.pb_dataprodukPembeli);*/

        bottomNavigationViewKurir = findViewById(R.id.nav_kurir);
        bottomNavigationViewKurir.setOnNavigationItemSelectedListener(navigation_Kurir);
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
                        //firebaseAuth.signOut();
                        Intent intent = new Intent(HalamanUtamaKurirActivity.this, MainActivity.class);
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