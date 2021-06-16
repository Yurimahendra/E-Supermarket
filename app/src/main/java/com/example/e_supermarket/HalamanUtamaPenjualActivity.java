package com.example.e_supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HalamanUtamaPenjualActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewPenjual;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_penjual = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected( MenuItem item) {
            Fragment Fp = null;
            switch (item.getItemId()){
                case R.id.homepenjual:
                    Fp = new HomeFragmentPenjual();
                    break;
                case R.id.notifpenjual:
                    Fp = new NotifFragmentPenjual();
                    break;
                case R.id.profilpenjual:
                    Fp = new ProfileFragmentPenjual();
                    break;
                case R.id.logoutpenjual:
                    Fp = new LogoutFragmentPenjual();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_penjual, Fp).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama_penjual);

        bottomNavigationViewPenjual = findViewById(R.id.nav_penjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(navigation_penjual);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddDataActivityPenjual.class));
            }
        });
    }
}