package com.example.e_supermarket.Pembeli.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Penjual.Activity.HalamanProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BayarPesananPembeliActivity extends AppCompatActivity implements onRequestPermissionResultBayar {
    private int index;
    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    private String nama ;
    private String nama_bank ;
    private long no_rekening ;

    EditText NamaBayar;
    EditText Namabankpenjual;
    EditText NoRek;
    ImageView BackBayar;
    ImageView GambarBuktiBayar;

    private int Uid;
    private String UidPesanan;
    private String UnamaPemesan;
    private String UnamaBarangPesan;
    private String UmerkBarangPesan;
    private int UjumlahBarangPesan;
    private String UsatuanPesan;
    private String UgambarPesan;
    private String UhargaBarangPesan;
    private String UTotalHargaPesan;
    private String UongkirPesan;
    private String UalamatKirimPesan;
    private String UNohpPesan;
    private String UtglKirimPesan;
    private String UMetodeBayarPesan;
    private String UStatus;
    //private String UBukti_transfer;
    //private MultipartBody.Part UBukti_transfer1;

    Button btnUploadBukti;
    ProgressBar PbUploadBukti;
    private String mediaPath;
    public Uri imageUri;
    private String postPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_pesanan_pembeli);
        NamaBayar = findViewById(R.id.EdtNamaBayar);
        Namabankpenjual = findViewById(R.id.EdtNaBankBayar);
        NoRek = findViewById(R.id.EdtNo_RekBayar);
        BackBayar = findViewById(R.id.imgBackbayar);
        BackBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        GambarBuktiBayar = findViewById(R.id.ImgBuktiBayar);
        GambarBuktiBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }
        });

        Bundle bundle = getIntent().getExtras();
        Uid = bundle.getInt("id");
        UidPesanan = bundle.getString("id_pesanan");
        UnamaPemesan = bundle.getString("nama");
        UNohpPesan = bundle.getString("no_hp");
        UalamatKirimPesan = bundle.getString("alamat");
        UnamaBarangPesan = bundle.getString("nama_barang");
        UmerkBarangPesan = bundle.getString("merk");
        UhargaBarangPesan = bundle.getString("harga");
        UjumlahBarangPesan= bundle.getInt("jumlah", 0);
        UsatuanPesan = bundle.getString("satuan");
        UgambarPesan = bundle.getString("gambar");
        UtglKirimPesan = bundle.getString("tanggal");
        UongkirPesan = bundle.getString("ongkir");
        UTotalHargaPesan = bundle.getString("total");
        UMetodeBayarPesan = bundle.getString("metode");
        UStatus = bundle.getString("status");



        btnUploadBukti = findViewById(R.id.btnUploadBuktibayar);
        PbUploadBukti = findViewById(R.id.PbUploadBuktibayar);
        btnUploadBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadBuktiBayar();
            }
        });
    }

    private void UploadBuktiBayar() {
        PbUploadBukti.setVisibility(View.VISIBLE);
        btnUploadBukti.setVisibility(View.INVISIBLE);
        try {
            File imgFile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
            MultipartBody.Part UBukti_transfer1 = MultipartBody.Part.createFormData("bukti_transfer", imgFile.getName(), reqBody);

            if (mediaPath == null) {
                Toast.makeText(BayarPesananPembeliActivity.this, "GAMBAR TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                PbUploadBukti.setVisibility(View.GONE);
                btnUploadBukti.setVisibility(View.VISIBLE);
            }

            Bundle bundle1 = getIntent().getExtras();
            if (bundle1 != null) {
                int id = Uid;
                ApiRequestPembeli requestDataDetailOrder = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                Call<ResponseBuatPesanan> UpdateDetailOrder = requestDataDetailOrder.UpdateDetailPesanan(
                        id,
                        "PUT",
                        RequestBody.create(MediaType.parse("text/plain"), UidPesanan),
                        RequestBody.create(MediaType.parse("text/plain"), UnamaPemesan),
                        RequestBody.create(MediaType.parse("text/plain"), UNohpPesan),
                        RequestBody.create(MediaType.parse("text/plain"), UalamatKirimPesan),
                        RequestBody.create(MediaType.parse("text/plain"), UnamaBarangPesan),
                        RequestBody.create(MediaType.parse("text/plain"), UmerkBarangPesan),
                        RequestBody.create(MediaType.parse("text/plain"), UhargaBarangPesan),
                        UjumlahBarangPesan,
                        RequestBody.create(MediaType.parse("text/plain"), UsatuanPesan),
                        RequestBody.create(MediaType.parse("text/plain"), UgambarPesan),
                        RequestBody.create(MediaType.parse("text/plain"), UtglKirimPesan),
                        //RequestBody.create(MediaType.parse("text/plain"), ongkirPesan),
                        RequestBody.create(MediaType.parse("text/plain"), UTotalHargaPesan),
                        RequestBody.create(MediaType.parse("text/plain"), UMetodeBayarPesan),
                        RequestBody.create(MediaType.parse("text/plain"), UStatus),
                        UBukti_transfer1

                );
                UpdateDetailOrder.enqueue(new Callback<ResponseBuatPesanan>() {
                    @Override
                    public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                        if( response.isSuccessful()) {
                            // int kode = response.body().getKode();
                            //String pesan = response.body().getPesan();
                            startActivity(new Intent(BayarPesananPembeliActivity.this, OrderanPembeliActivity.class));
                            Toast.makeText(BayarPesananPembeliActivity.this, "berhasil upload Bukti", Toast.LENGTH_SHORT).show();
                                /*if (kode == 200){


                                }*/

                        }else {
                            Toast.makeText(BayarPesananPembeliActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }

                        PbUploadBukti.setVisibility(View.GONE);
                        btnUploadBukti.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {

                        Toast.makeText(BayarPesananPembeliActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                        PbUploadBukti.setVisibility(View.GONE);
                        btnUploadBukti.setVisibility(View.VISIBLE);
                        //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                    }

                });
            }
        }catch (NullPointerException pointerException){
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            PbUploadBukti.setVisibility(View.GONE);
            btnUploadBukti.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getProfilePenjual();
    }

    private void getProfilePenjual() {
        //pbDataPenjual.setVisibility(View.VISIBLE);
        ApiRequestDataProduk requestDataPenjual = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataPenjual> tampilData = requestDataPenjual.RetrieveDataPenjual();

        tampilData.enqueue(new Callback<ResponseDataPenjual>() {
            @Override
            public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                if (response.isSuccessful()){
                    try {
                        dataPenjualList = response.body().getDataPenjual();

                        nama = dataPenjualList.get(index).getNama();
                        nama_bank = dataPenjualList.get(index).getNama_bank();
                        no_rekening = dataPenjualList.get(index).getNo_rekening();

                        NamaBayar.setText(nama);
                        Namabankpenjual.setText(nama_bank);
                        NoRek.setText(""+no_rekening);

                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        // Log.i("tes", ""+nik);
                    }


                    /*adapterProfilePenjual = new AdapterProfilePenjual(HalamanProfilePenjualActivity.this, dataPenjualList);
                    recyclerView.setAdapter(adapterProfilePenjual);
                    adapterProfilePenjual.notifyDataSetChanged();*/

                }

                //pbDataPenjual.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                Toast.makeText(BayarPesananPembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                //pbDataPenjual.setVisibility(View.GONE);
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
                    GambarBuktiBayar.setImageURI(imageUri);
                    cursor.close();
                    postPath = mediaPath;
                }
            }
        }

    }

    private void requestPermission(){
        //PbSimpanData.setVisibility(View.VISIBLE);
        //btnAddData.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 786);
        }else {
            UploadBuktiBayar();
            //PbSimpanData.setVisibility(View.GONE);
            //btnAddData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestPermissionResultBayar(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 786 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            UploadBuktiBayar();
        }
    }
}