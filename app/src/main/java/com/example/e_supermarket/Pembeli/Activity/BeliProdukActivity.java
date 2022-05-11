package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class BeliProdukActivity extends AppCompatActivity {
    //TextView Idbeli;
    TextView Nama_Barangbeli;
    TextView Merkbeli;
    TextView Hargabeli;
    //TextView Stokbeli;
    TextView Satuanbeli;
    //TextView Deskripsibeli;
    TextView Jumlah;
    ImageView BeliImgProduk1 ;
    ImageView backBeli;

    ImageView Increment;
    ImageView Decrement;
    TextView HitungJumlah;
    int count = 0;

    int id_satuan;
    private String n_satuan[] = {"Kg", "Gr", "Pcs", "Lusin", "Kodi", "Gross", "Pack"};

    private String BeliNama_Barang;
    private String BeliMerk;
    private String BeliHarga;
    private int BeliStok;
    private String BeliSatuan;
    private String BeliGambar;
    private String BeliDeskripsi;
    private int BeliId;
    private int jumlah1;

    Button btnBuatPesanan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_produk);

        Nama_Barangbeli = findViewById(R.id.tvNamaBarangbeli);
        Merkbeli = findViewById(R.id.tvMerkbeli);
        Hargabeli = findViewById(R.id.tvHargabeli);
        Satuanbeli = findViewById(R.id.tvSatuanbeli);
        Jumlah = findViewById(R.id.tvAngkaJumlah);
        BeliImgProduk1 = findViewById(R.id.ImgItembeli);

        Increment = findViewById(R.id.imgTambahi);
        Decrement = findViewById(R.id.imgKurangi);
        HitungJumlah = findViewById(R.id.tvAngkaJumlah);

        Increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Jumlah.setText(""+ count);
            }
        });


        Decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count <= 0){
                    count = 0;
                }else {
                    count--;
                    Jumlah.setText(""+ count);
                }

            }
        });

        btnBuatPesanan = findViewById(R.id.btnbuatPesanan);
        btnBuatPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(Jumlah.getText().toString().trim()) == 0){
                    Toast.makeText(BeliProdukActivity.this, "Jumlah Satuan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBeli = findViewById(R.id.imgBackBeliProduk);
        backBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        BeliId = bundle.getInt("id");
        BeliNama_Barang = bundle.getString("nama_barang");
        BeliMerk = bundle.getString("merk");
        BeliHarga = bundle.getString("harga");
        BeliStok = bundle.getInt("stok", 0);
        BeliSatuan = bundle.getString("satuan");
        BeliGambar = bundle.getString("gambar");
        BeliDeskripsi = bundle.getString("deskripsi");
        jumlah1 = bundle.getInt("jumlah", 0);

        Nama_Barangbeli.setText(BeliNama_Barang);
        Merkbeli.setText(BeliMerk);
        Hargabeli.setText(BeliHarga);
        //Stok.setText(uStok+"");
        /*if (BeliSatuan.equals(n_satuan[0])) id_satuan = 0;
        else if (BeliSatuan.equals(n_satuan[1])) id_satuan = 1;
        else if (BeliSatuan.equals(n_satuan[2])) id_satuan = 2;
        else if (BeliSatuan.equals(n_satuan[3])) id_satuan = 3;
        else if (BeliSatuan.equals(n_satuan[4])) id_satuan = 4;
        else if (BeliSatuan.equals(n_satuan[5])) id_satuan = 5;
        Satuanbeli.setSelection(id_satuan);*/
        Satuanbeli.setText(BeliSatuan);
        Jumlah.setText(""+jumlah1);
        Glide.with(BeliImgProduk1.getContext())
                .load(BeliGambar).into(BeliImgProduk1);
        //Deskripsi.setText(uDeskripsi);

    }
}