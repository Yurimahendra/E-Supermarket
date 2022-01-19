package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfilePembeliActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPembeli;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_pembeli = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepembeli:
                    startActivity(new Intent(ProfilePembeliActivity.this, HalamanUtamaPembeliActivity.class));
                    //startActivity(new Intent());
                    break;
                case R.id.orderanpembeli:
                    startActivity(new Intent(ProfilePembeliActivity.this, OrderanPembeliActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpembeli:
                    startActivity(new Intent(ProfilePembeliActivity.this, ChatPembeliActivity.class));

                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpembeli:
                   // startActivity(new Intent(ProfilePembeliActivity.this, ProfilePembeliActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutpembelil:
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
        setContentView(R.layout.activity_profile_pembeli);
        bottomNavigationViewPembeli = findViewById(R.id.nav_pembeli);
        bottomNavigationViewPembeli.setOnNavigationItemSelectedListener(navigation_pembeli);
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
                        Intent intent = new Intent(ProfilePembeliActivity.this, MainActivity.class);
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