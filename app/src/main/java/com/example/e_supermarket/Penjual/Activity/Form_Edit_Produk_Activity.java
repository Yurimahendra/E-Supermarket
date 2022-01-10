package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.NonNull;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Penjual.Fragment.HomeFragmentPenjual;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Form_Edit_Produk_Activity extends AppCompatActivity implements onRequestPermissionResult {
    EditText Nama_barang;
    EditText Merk;
    EditText Harga;
    EditText Stok;
    Spinner Satuan;
    EditText Deskripsi;
    FirebaseFirestore db;

    private String uNama_Barang;
    private String uMerk;
    private int uHarga;
    private int uStok;
    private String uSatuan;
    private String uGambar;
    private String uDeskripsi;
    private int uId;
    Button btnUpdt;
    ProgressBar PbUpdteData;

    int id_satuan;
    private String n_satuan[] = {"Kg", "Gr", "Pcs", "Lusin", "Kodi", "Gross", "Pack"};

    private String mediaPath;
    private String postPath;
    public Uri imageUri;

    String nama_barang;
    String merk;
    int harga;
    int stok;
    String satuan;
    String gambar;
    MultipartBody.Part partImg;
    String deskripsi;
    CircleImageView EditImgProduk1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__edit__produk_);


        Nama_barang = findViewById(R.id.UpdtNaBa);
        Merk = findViewById(R.id.UpdtMerk);
        Harga = findViewById(R.id.UpdtHarga);
        Stok = findViewById(R.id.UpdtStok);
        Satuan = findViewById(R.id.UpdtSpSatuan);
        EditImgProduk1 = findViewById(R.id.GambarProdukEdit);
        EditImgProduk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }

        });
        Deskripsi = findViewById(R.id.UpdtDeskripsi);
        btnUpdt = findViewById(R.id.btnUpdtData);
        PbUpdteData = findViewById(R.id.progressUDataP);

       // db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        uId = bundle.getInt("id");
        uNama_Barang = bundle.getString("nama_barang");
        uMerk = bundle.getString("merk");
        uHarga = bundle.getInt("harga", 0);
        uStok = bundle.getInt("stok", 0);
        uSatuan = bundle.getString("satuan");
        uGambar = bundle.getString("gambar");
        uDeskripsi = bundle.getString("deskripsi");


        Nama_barang.setText(uNama_Barang);
        Merk.setText(uMerk);
        Harga.setText(uHarga+"");
        Stok.setText(uStok+"");
        if (uSatuan.equals(n_satuan[0])) id_satuan = 0;
        else if (uSatuan.equals(n_satuan[1])) id_satuan = 1;
        else if (uSatuan.equals(n_satuan[2])) id_satuan = 2;
        else if (uSatuan.equals(n_satuan[3])) id_satuan = 3;
        else if (uSatuan.equals(n_satuan[4])) id_satuan = 4;
        else if (uSatuan.equals(n_satuan[5])) id_satuan = 5;
        Satuan.setSelection(id_satuan);
        Glide.with(EditImgProduk1.getContext())
                .load(uGambar).into(EditImgProduk1);
        Deskripsi.setText(uDeskripsi);


        btnUpdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Updatedataproduk();
            }
        });


    }

    private void pilihgambar(){
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
                    EditImgProduk1.setImageURI(imageUri);
                    cursor.close();
                    postPath = mediaPath;
                }
            }
        }
    }

    private void Updatedataproduk() {
        PbUpdteData.setVisibility(View.VISIBLE);
        btnUpdt.setVisibility(View.INVISIBLE);
        try {

            nama_barang = Nama_barang.getText().toString().trim();
            merk = Merk.getText().toString().trim();
            harga = Integer.parseInt(Harga.getText().toString().trim());
            stok = Integer.parseInt(Stok.getText().toString().trim());
            satuan = Satuan.getSelectedItem().toString().trim();
//            mediaPath = EditImgProduk1.getContext().getContentResolver().getType(imageUri);

            if (mediaPath != null){
                File imgFile = new File(mediaPath);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
                partImg = MultipartBody.Part.createFormData("gambar", imgFile.getName(), reqBody);
            }else {
                partImg = null;
            }
            //token gthub
//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
            deskripsi = Deskripsi.getText().toString().trim();

            if (nama_barang.equals("")) {
                Nama_barang.setError("NAMA BARANG TIDAK BOLEH KOSONG");
                PbUpdteData.setVisibility(View.GONE);
                btnUpdt.setVisibility(View.VISIBLE);
            } else if (merk.equals("")) {
                Merk.setError("MERK TIDAK BOLEH KOSONG");
                PbUpdteData.setVisibility(View.GONE);
                btnUpdt.setVisibility(View.VISIBLE);
            } else if (harga <= 0) {
                Harga.setError("HARGA TIDAK BOLEH KOSONG");
                PbUpdteData.setVisibility(View.GONE);
                btnUpdt.setVisibility(View.VISIBLE);
            } else if (stok <= 0) {
                Stok.setError("STOK TIDAK BOLEH KOSONG");
                PbUpdteData.setVisibility(View.GONE);
                btnUpdt.setVisibility(View.VISIBLE);
            } else if (satuan.equals("")) {
                Toast.makeText(getApplicationContext(), "SATUAN TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                PbUpdteData.setVisibility(View.GONE);
                btnUpdt.setVisibility(View.VISIBLE);
            } else {

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    int id = uId;
                    ApiRequestDataProduk requestDataProduk = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                    Call<ResponseDataProduk> UpdateData = requestDataProduk.UpdateData(
                            id,
                            "PUT",
                            RequestBody.create(MediaType.parse("text/plain"), nama_barang),
                            RequestBody.create(MediaType.parse("text/plain"), merk),
                            harga,
                            RequestBody.create(MediaType.parse("text/plain"), satuan),
                            stok,
                            partImg,
                            RequestBody.create(MediaType.parse("text/plain"), deskripsi)
                    );
                    UpdateData.enqueue(new Callback<ResponseDataProduk>() {
                        @Override
                        public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {
                            if( response.isSuccessful()) {
                                int kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                if (kode == 200){
                                    startActivity(new Intent(Form_Edit_Produk_Activity.this, HalamanUtamaPenjualActivity.class));
                                    Toast.makeText(Form_Edit_Produk_Activity.this, ""+pesan, Toast.LENGTH_SHORT).show();

                                }

                            }else {
                                Toast.makeText(Form_Edit_Produk_Activity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                            PbUpdteData.setVisibility(View.GONE);
                            btnUpdt.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onFailure(Call<ResponseDataProduk> call, Throwable t) {
                            Toast.makeText(Form_Edit_Produk_Activity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                            PbUpdteData.setVisibility(View.GONE);
                            btnUpdt.setVisibility(View.VISIBLE);
                            //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                        }

                    });
                }
            }

        }catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Produk Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbUpdteData.setVisibility(View.GONE);
            btnUpdt.setVisibility(View.VISIBLE);
        }
    }

    private void requestPermission(){
        //PbSimpanData.setVisibility(View.VISIBLE);
        //btnAddData.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 786);
        }else {
            Updatedataproduk();
            //PbSimpanData.setVisibility(View.GONE);
            //btnAddData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 786 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Updatedataproduk();
        }
    }
}