package com.example.e_supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDataActivityPenjual extends AppCompatActivity {

    EditText Nama_barang;
    EditText Merk;
    EditText Harga;
    EditText Stok;
    Spinner Satuan;
    EditText Deskripsi;
    ImageView addImage;
    Button btnAddData;
    ProgressBar PbSimpanData;

    String nama_barang;
    String merk;
    int harga;
    String satuan;
    int stok;
    String gambar;
    String deskripsi;

    private List<DataProduk> dataProdukList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_penjual);

        Nama_barang = findViewById(R.id.EdtNaBa);
        Merk = findViewById(R.id.EdtMerk);
        Harga = findViewById(R.id.EdtHarga);
        Satuan = findViewById(R.id.SpSatuan);
        Stok = findViewById(R.id.EdtStok);
        addImage = findViewById(R.id.ImgProduk);
        Deskripsi = findViewById(R.id.EdtDeskripsi);
        btnAddData = findViewById(R.id.btnAddData);
        PbSimpanData = findViewById(R.id.progressDataP);


        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertdataproduk();
            }
        });
    }


    private void insertdataproduk() {
        PbSimpanData.setVisibility(View.VISIBLE);
        btnAddData.setVisibility(View.INVISIBLE);
        try {

            nama_barang = Nama_barang.getText().toString().trim();
            merk = Merk.getText().toString().trim();
            harga = Integer.parseInt(Harga.getText().toString().trim());
            satuan = Satuan.getSelectedItem().toString().trim();
            stok = Integer.parseInt(Stok.getText().toString().trim());
            gambar = addImage.getContext().toString();
            deskripsi = Deskripsi.getText().toString().trim();

            if (nama_barang.equals("")) {
                Nama_barang.setError("NAMA BARANG TIDAK BOLEH KOSONG");
            } else if (merk.equals("")) {
                Merk.setError("MERK TIDAK BOLEH KOSONG");
            } else if (harga <= 0) {
                Harga.setError("HARGA TIDAK BOLEH KOSONG");
            } else if (stok <= 0) {
                Stok.setError("STOK TIDAK BOLEH KOSONG");
            } else if (satuan.equals("")) {
                 Toast.makeText(getApplicationContext(), "SATUAN TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else {
                DataProduk produk = new DataProduk();
                produk.setNama_barang(nama_barang);
                produk.setMerk(merk);
                produk.setHarga(harga);
                produk.setSatuan(satuan);
                produk.setStok(stok);
                produk.setGambar(gambar);
                produk.setDeskripsi(deskripsi);
                /*HashMap<String, Object> BuatdataProduk = new HashMap<>();
                BuatdataProduk.put("nama_barang", nama_barang);
                BuatdataProduk.put("merk", merk);
                BuatdataProduk.put("harga", harga);
                BuatdataProduk.put("satuan", satuan);
                BuatdataProduk.put("stok", stok);
                BuatdataProduk.put("gambar", gambar);
                BuatdataProduk.put("deskripsi", deskripsi);*/


                ApiRequestDataProduk requestDataProduk = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                //Call<ResponseDataProduk> SimpanData = RetroServer.GetapiRequestDataProduk().SendData(produk);

                Call<ResponseDataProduk> SimpanData = requestDataProduk.SendData(nama_barang, merk, harga, satuan, stok, gambar, deskripsi);

                SimpanData.enqueue(new Callback<ResponseDataProduk>() {
                    @Override
                    public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {
                        if(response.body() != null) {
                            int kode = response.body().getKode();
                            String pesan = response.body().getPesan();
                            Toast.makeText(AddDataActivityPenjual.this, "kode : "+kode+" | pesan : "+pesan, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddDataActivityPenjual.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }

                        /*if (kode == 200){
                            startActivity(new Intent(getApplicationContext(), HalamanUtamaPenjualActivity.class));
                            Toast.makeText(AddDataActivityPenjual.this, "pesan : "+pesan, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddDataActivityPenjual.this, "Data Gagal Tersimpan"+response.message(), Toast.LENGTH_SHORT).show();
                        }*/
                        //Toast.makeText(AddDataActivityPenjual.this, "kode : "+kode+" | pesan : "+pesan, Toast.LENGTH_SHORT).show();

                        //finish();
                        PbSimpanData.setVisibility(View.GONE);
                        btnAddData.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onFailure(Call<ResponseDataProduk> call, Throwable t) {
                        Toast.makeText(AddDataActivityPenjual.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                        PbSimpanData.setVisibility(View.GONE);
                        btnAddData.setVisibility(View.VISIBLE);
                    }
                });

            }

        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Produk Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbSimpanData.setVisibility(View.GONE);
            btnAddData.setVisibility(View.VISIBLE);
        }


    }
}