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
import android.text.Editable;
import android.text.TextWatcher;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

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
    EditText Min_belanja;
    EditText Ongkir;
    Spinner Satuan;
    EditText Deskripsi;
    ImageView addImage;
    Button btnAddData;
    ProgressBar PbSimpanData;

    String nama_barang;
    String merk;
    String harga;
    String satuan;
    int minbelanja;
    String ongkir;
    String gambar;
    String deskripsi;

    /*Number number;
    int harga1;
    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    number = kursIndonesia.parse(harga);
    harga1 = number.intValue();*/

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
        Min_belanja = findViewById(R.id.EdtminBelanja);
        Ongkir = findViewById(R.id.EdtOngkir);
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

        Harga.addTextChangedListener(new TextWatcher() {
            private String edtText = Harga.getText().toString().trim();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(edtText)){
                    Harga.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()){
                        edtText = formatrupiah(Double.parseDouble(replace));
                    }else {
                        edtText = "";
                    }
                    Harga.setText(edtText);
                    Harga.setSelection(edtText.length());
                    Harga.addTextChangedListener(this);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Ongkir.addTextChangedListener(new TextWatcher() {
            private String edtText1 = Ongkir.getText().toString().trim();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(edtText1)){
                    Ongkir.removeTextChangedListener(this);
                    String replace = s.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()){
                        edtText1 = formatrupiah(Double.parseDouble(replace));
                    }else {
                        edtText1 = "";
                    }
                    Ongkir.setText(edtText1);
                    Ongkir.setSelection(edtText1.length());
                    Ongkir.addTextChangedListener(this);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String formatrupiah(Double number){
        Locale localeId = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeId);
        String formatrupiah = numberFormat.format(number);
        String[] split = formatrupiah.split(",");
        int length = split[0].length();
        return split[0].substring(0,2)+". "+split[0].substring(2, length);
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
            harga = Harga.getText().toString().trim();
            satuan = Satuan.getSelectedItem().toString().trim();
            minbelanja = Integer.parseInt(Min_belanja.getText().toString().trim());
            ongkir = Ongkir.getText().toString().trim();
            gambar = addImage.getContext().getContentResolver().getType(imageUri);
            File imgFile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
            MultipartBody.Part partImg = MultipartBody.Part.createFormData("gambar", imgFile.getName(), reqBody);
            deskripsi = Deskripsi.getText().toString().trim();

            if (nama_barang.equals("")) {
                Nama_barang.setError("NAMA BARANG TIDAK BOLEH KOSONG");
                PbSimpanData.setVisibility(View.GONE);
                btnAddData.setVisibility(View.VISIBLE);
            } else if (merk.equals("")) {
                Merk.setError("MERK TIDAK BOLEH KOSONG");
                PbSimpanData.setVisibility(View.GONE);
                btnAddData.setVisibility(View.VISIBLE);
            } else if (harga.equals("")) {
                Harga.setError("HARGA TIDAK BOLEH KOSONG");
                PbSimpanData.setVisibility(View.GONE);
                btnAddData.setVisibility(View.VISIBLE);
            } else if (minbelanja <= 0) {
                Min_belanja.setError("Minimal Belanja TIDAK BOLEH KOSONG");
                PbSimpanData.setVisibility(View.GONE);
                btnAddData.setVisibility(View.VISIBLE);
            } else if (ongkir.equals("")) {
                Ongkir.setError("Ongkir TIDAK BOLEH KOSONG");
                PbSimpanData.setVisibility(View.GONE);
                btnAddData.setVisibility(View.VISIBLE);
            } else if (satuan.equals("")) {
                 Toast.makeText(getApplicationContext(), "SATUAN TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                PbSimpanData.setVisibility(View.GONE);
                btnAddData.setVisibility(View.VISIBLE);
            } else if (mediaPath == null) {
                Toast.makeText(getApplicationContext(), "GAMBAR TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                PbSimpanData.setVisibility(View.GONE);
                btnAddData.setVisibility(View.VISIBLE);
            } else {

                ApiRequestDataProduk requestDataProduk = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                Call<ResponseDataProduk> SimpanData = requestDataProduk.SendData(
                        RequestBody.create(MediaType.parse("text/plain"), nama_barang),
                        RequestBody.create(MediaType.parse("text/plain"), merk),
                        RequestBody.create(MediaType.parse("text/plain"), harga),
                        RequestBody.create(MediaType.parse("text/plain"), satuan),
                        minbelanja,
                        RequestBody.create(MediaType.parse("text/plain"), ongkir),
                        partImg,
                        RequestBody.create(MediaType.parse("text/plain"), deskripsi)
                );


                SimpanData.enqueue(new Callback<ResponseDataProduk>() {
                    @Override
                    public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {
                        if (response.isSuccessful()){
                            //int kode = response.body().getKode();
                            //String pesan = response.body().getPesan();
                            startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                            Toast.makeText(AddDataActivityPenjual.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                            /*if( kode == 200) {

                            }*/
                        }
                        else {
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