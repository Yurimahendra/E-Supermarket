package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PembeliLihatProfilPenjualActivity extends AppCompatActivity {
    private String UfotoPenjual;
    private String UnamaPenjual;
    private String Unoponpenjual;
    private String Ualamat;
    private String Unamatoko;


    TextView Nama_penjual;
    EditText NamaToko;
    EditText Alamatpenjual;
    EditText Noponselpenjual;
    CircleImageView ImgProfilePenjual;

    ImageView backPembtoPenj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembeli_lihat_profil_penjual);

        backPembtoPenj = findViewById(R.id.imgBackPembToPenj);
        backPembtoPenj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Nama_penjual = findViewById(R.id.tvnamaPembToPenj);
        NamaToko = findViewById(R.id.tvnatoPembToPenj);
        Alamatpenjual = findViewById(R.id.tvalamatPembToPenj);
        Noponselpenjual = findViewById(R.id.tvnoponselPembToPenj);
        ImgProfilePenjual = findViewById(R.id.ImgPembToPenj);

        Bundle bundle = getIntent().getExtras();
        UfotoPenjual = bundle.getString("foto_penjual");
        UnamaPenjual = bundle.getString("nama_penjual");
        Unoponpenjual = bundle.getString("no_ponsel_penjual");
        Ualamat = bundle.getString("alamat");
        Unamatoko = bundle.getString("nama_toko");

        Nama_penjual.setText(UnamaPenjual);
        NamaToko.setText(Unamatoko);
        Alamatpenjual.setText(Ualamat);
        Noponselpenjual.setText(Unoponpenjual);
        Glide.with(ImgProfilePenjual.getContext())
                .load(UfotoPenjual).into(ImgProfilePenjual);



    }
}