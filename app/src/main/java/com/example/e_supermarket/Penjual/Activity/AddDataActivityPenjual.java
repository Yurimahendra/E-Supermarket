package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.Penjual.Interface.onRequestPermissionResult;
import com.example.e_supermarket.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDataActivityPenjual extends AppCompatActivity implements onRequestPermissionResult {

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

    private String mediaPath;
    private String postPath;
    public Uri imageUri;
    private Bitmap bitmap;

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


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }
        });

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertdataproduk();

            }
        });
    }

    private void pilihgambar() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1){
                if (data!=null){
                    imageUri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    addImage.setImageURI(imageUri);
                    cursor.close();
                    postPath = mediaPath;
                }
            }
        }

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
            gambar = addImage.getContext().getContentResolver().getType(imageUri);
            File imgFile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
            MultipartBody.Part partImg = MultipartBody.Part.createFormData("gambar", imgFile.getName(), reqBody);
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
            } else if (mediaPath == null) {
                Toast.makeText(getApplicationContext(), "GAMBAR TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else {

                ApiRequestDataProduk requestDataProduk = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                Call<ResponseDataProduk> SimpanData = requestDataProduk.SendData(
                        RequestBody.create(MediaType.parse("text/plain"), nama_barang),
                        RequestBody.create(MediaType.parse("text/plain"), merk),
                        harga,
                        RequestBody.create(MediaType.parse("text/plain"), satuan),
                        stok,
                        partImg,
                        RequestBody.create(MediaType.parse("text/plain"), deskripsi)
                );


                SimpanData.enqueue(new Callback<ResponseDataProduk>() {
                    @Override
                    public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {
                        int kode = response.body().getKode();
                        String pesan = response.body().getPesan();
                        if( kode == 200) {
                            startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                            Toast.makeText(AddDataActivityPenjual.this, "pesan : "+pesan, Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(AddDataActivityPenjual.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }

                        PbSimpanData.setVisibility(View.GONE);
                        btnAddData.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onFailure(Call<ResponseDataProduk> call, Throwable t) {
                        Toast.makeText(AddDataActivityPenjual.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                        PbSimpanData.setVisibility(View.GONE);
                        btnAddData.setVisibility(View.VISIBLE);
                        //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                    }

                });

            }

        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Produk Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbSimpanData.setVisibility(View.GONE);
            btnAddData.setVisibility(View.VISIBLE);
        }catch (NullPointerException pointerException){
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            PbSimpanData.setVisibility(View.GONE);
            btnAddData.setVisibility(View.VISIBLE);
        }


    }

    private void requestPermission(){
        //PbSimpanData.setVisibility(View.VISIBLE);
        //btnAddData.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 786);
        }else {
            insertdataproduk();
            //PbSimpanData.setVisibility(View.GONE);
            //btnAddData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 786 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            insertdataproduk();
        }
    }
}