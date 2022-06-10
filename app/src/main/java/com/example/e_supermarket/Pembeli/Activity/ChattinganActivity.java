package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChattinganActivity extends AppCompatActivity {
    ImageView backChatinga;
    CircleImageView fotoChtingan;
    TextView NamaChtingan;
    private String Ufoto;
    private String Unama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chattingan);
        backChatinga = findViewById(R.id.imgBackChatinganPembeli);
        backChatinga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        NamaChtingan = findViewById(R.id.tvNamaChatinganPembeli);
        fotoChtingan = findViewById(R.id.imgProfilChtinganPembeli);

        Bundle bundle = getIntent().getExtras();
        Ufoto = bundle.getString("foto");
        Unama = bundle.getString("nama");

        NamaChtingan.setText(Unama);
        Glide.with(fotoChtingan.getContext())
                .load(Ufoto).into(fotoChtingan);
    }
}