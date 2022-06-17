package com.example.e_supermarket.Penjual.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatinganPenjualActivity extends AppCompatActivity {
    private ImageView backChatingaPenj;
    private CircleImageView fotoChtinganPenj;
    private TextView NamaChtingan;
    private TextView id;
    private String Uid;
    private String Ufoto;
    private String Unama;
    private String Uno_ponsel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatingan_penjual);

        backChatingaPenj = findViewById(R.id.imgBackChatinganPenj);
        backChatingaPenj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        NamaChtingan = findViewById(R.id.tvNamaChatinganPenj);
        fotoChtinganPenj = findViewById(R.id.imgProfilChtinganPenj);
        id = findViewById(R.id.tvIdChatinganPenj);

        Bundle bundle = getIntent().getExtras();
        Uid = bundle.getString("id");
        Ufoto = bundle.getString("foto");
        Unama = bundle.getString("nama");
        Uno_ponsel = bundle.getString("no_ponsel");

        NamaChtingan.setText(Unama);
        id.setText(Uid);
        Glide.with(fotoChtinganPenj.getContext())
                .load(Ufoto).into(fotoChtinganPenj);
    }
}