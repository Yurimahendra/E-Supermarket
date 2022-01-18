package com.example.e_supermarket.Penjual.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HalamanNotifPenjualActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPenjual;
    FirebaseAuth firebaseAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_penjual = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepenjual:
                    startActivity(new Intent(HalamanNotifPenjualActivity.this, HalamanUtamaPenjualActivity.class));
                    break;
                case R.id.notifpenjual:
                    break;
                case R.id.chatpenjual:
                    startActivity(new Intent(HalamanNotifPenjualActivity.this, HalamanChatPenjualActivity.class));
                    break;
                case R.id.profilpenjual:
                    startActivity(new Intent(HalamanNotifPenjualActivity.this, HalamanProfilePenjualActivity.class));
                    break;
                case R.id.logoutpenjual:
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
        setContentView(R.layout.activity_halaman_notif_penjual);
        bottomNavigationViewPenjual = findViewById(R.id.nav_penjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(navigation_penjual);
        firebaseAuth = FirebaseAuth.getInstance();
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
                        Intent intent = new Intent(HalamanNotifPenjualActivity.this, MainActivity.class);
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