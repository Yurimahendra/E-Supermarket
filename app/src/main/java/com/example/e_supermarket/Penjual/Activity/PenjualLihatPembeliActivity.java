package com.example.e_supermarket.Penjual.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PenjualLihatPembeliActivity extends AppCompatActivity {
    private String UfotoPembeli;
    private String UnamaPembeli;
    private String UnoponPembeli;
    private String UalamatPembeli;
    ImageView backPenjToPemb;

    TextView Nama_pembeli;
    EditText Alamatpembeli;
    EditText Noponselpembeli;
    CircleImageView ImgProfilePmbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual_lihat_pembeli);

        Bundle bundle = getIntent().getExtras();
        UfotoPembeli = bundle.getString("foto_pembeli");
        UnamaPembeli = bundle.getString("nama_pembeli");
        UnoponPembeli = bundle.getString("no_ponsel_pembeli");
        UalamatPembeli = bundle.getString("alamat");

        backPenjToPemb = findViewById(R.id.imgBackPenjToPemb);
        backPenjToPemb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Nama_pembeli = findViewById(R.id.tvnamaPenjToPemb);
        Alamatpembeli = findViewById(R.id.tvalamatPenjToPemb);
        Noponselpembeli = findViewById(R.id.tvnoponselPenjToPemb);
        ImgProfilePmbl = findViewById(R.id.ImgPenjToPemb);
        Glide.with(ImgProfilePmbl.getContext())
                .load(UfotoPembeli).into(ImgProfilePmbl);

        Nama_pembeli.setText(UnamaPembeli);
        Alamatpembeli.setText(UalamatPembeli);
        Noponselpembeli.setText(UnoponPembeli);
    }
}