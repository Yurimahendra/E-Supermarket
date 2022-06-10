package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatPembeliActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPembeli;
    FirebaseAuth Fauth;

    ImageView backChatPembeli;
    CircleImageView proflChatPembli;
    TextView tvNamaChatPemb;
    TextView chatPemb;
    LinearLayout lnChatPembl;
    FloatingActionButton BtnPilihKontak;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_pembeli = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepembeli:
                    startActivity(new Intent(ChatPembeliActivity.this, HalamanUtamaPembeliActivity.class));
                    //startActivity(new Intent());
                    break;
                case R.id.orderanpembeli:
                    startActivity(new Intent(ChatPembeliActivity.this, OrderanPembeliActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpembeli:
                    //startActivity(new Intent(ChatPembeliActivity.this, ChatPembeliActivity.class));

                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpembeli:
                    startActivity(new Intent(ChatPembeliActivity.this, ProfilePembeliActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutpembelil:
                    //Fp = new LogoutFragmentPenjual();
                    Fauth.signOut();
                    onBackPressedOut();
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
        setContentView(R.layout.activity_chat_pembeli);
        bottomNavigationViewPembeli = findViewById(R.id.nav_pembeli);
        bottomNavigationViewPembeli.setOnNavigationItemSelectedListener(navigation_pembeli);
        Fauth = FirebaseAuth.getInstance();

        proflChatPembli = findViewById(R.id.profilChatPembli);
        tvNamaChatPemb = findViewById(R.id.tvNamaChatPembeli);
        chatPemb = findViewById(R.id.textchatpemb);

        lnChatPembl = findViewById(R.id.lnchatPemb);
        lnChatPembl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        BtnPilihKontak = findViewById(R.id.fab_msgpemb);
        BtnPilihKontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatPembeliActivity.this, HalamanKontakPembeliActivity.class));
            }
        });

    }


    private void onBackPressedOut() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("Kamu yakin ingin keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //firebaseAuth.signOut();
                        Intent intent = new Intent(ChatPembeliActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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