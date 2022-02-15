package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_supermarket.Pembeli.Activity.FormDataPembeliActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Adapter.AdapterProfilePenjual;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormDataPenjualActivity extends AppCompatActivity {

    EditText Nik;
    EditText Nama;
    Spinner Jk;
    EditText NoPons;
    EditText TeLa;
    EditText EdtTala;
    EditText Alamat;
    EditText Nato;
    Button BtnUpdate;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    FirebaseFirestore Dbroot;

    private List<DataPenjual> dataPenjualList;
    private AdapterProfilePenjual adapterProfilePenjual;

    private FirebaseFirestore db;

     int p = 0;

    ProgressBar PbSimpanDataS;
    FirebaseStorage storage;
    StorageReference storageReference;

    long nik;
    String nama;
    String jenis_kelamin;
    String no_ponsel;
    String tempat_lahir;
    String tanggal_lahir;
    String alamat;
    String nama_toko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_penjual);

        Nik = (EditText) findViewById(R.id.EdtNik);
        Nama = (EditText) findViewById(R.id.EdtNama);
        Jk = (Spinner) findViewById(R.id.SpJk);
        NoPons = (EditText) findViewById(R.id.EdtNop);
        TeLa = (EditText) findViewById(R.id.EdtTeLa);
        Alamat = (EditText) findViewById(R.id.EdtAlamat);
        Nato = (EditText) findViewById(R.id.EdtNato);
        EdtTala = (EditText) findViewById(R.id.EdtTala);
        PbSimpanDataS = findViewById(R.id.progressDataS);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        BtnUpdate = (Button) findViewById(R.id.btnUpdateData);
        Dbroot = FirebaseFirestore.getInstance();


        EditText editTextS = findViewById(R.id.EdtNop);
        editTextS.setText(String.format(
                "%s", getIntent().getStringExtra("mobile")
        ));


        EdtTala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertdatapenjual();
            }
        });

        db = FirebaseFirestore.getInstance();
        dataPenjualList = new ArrayList<>();


    }

    private void insertdatapenjual() {
        PbSimpanDataS.setVisibility(View.VISIBLE);
        BtnUpdate.setVisibility(View.INVISIBLE);
        try {
           // String id = UUID.randomUUID().toString();
            nik = Long.parseLong(Nik.getText().toString().trim());
            nama = Nama.getText().toString().trim();
            jenis_kelamin = Jk.getSelectedItem().toString().trim();
            no_ponsel = NoPons.getText().toString().trim();
            tempat_lahir = TeLa.getText().toString().trim();
            tanggal_lahir = EdtTala.getText().toString().trim();
            alamat = Alamat.getText().toString().trim();
            nama_toko = Nato.getText().toString().trim();


            if (nik <= 0 ) {
                Toast.makeText(getApplicationContext(), "NIK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            }else if (nama.isEmpty()) {
                Toast.makeText(getApplicationContext(), "NAMA TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            }else if (jenis_kelamin.isEmpty()) {
                Toast.makeText(getApplicationContext(), "JENIS KELAMIN TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            }else if (no_ponsel.isEmpty()) {
                Toast.makeText(getApplicationContext(), "NOMOR PONSEL TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            }else if (tempat_lahir.isEmpty()) {
                Toast.makeText(getApplicationContext(), "TEMPAT LAHIR TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            }else if (tanggal_lahir.isEmpty()) {
                Toast.makeText(getApplicationContext(), "TANGGAL LAHIR TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            }else if (alamat.isEmpty()) {
                Toast.makeText(getApplicationContext(), "ALAMAT TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            }else if (nama_toko.isEmpty()) {
                Toast.makeText(getApplicationContext(), "NAMA TOKO TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            }else {
                ApiRequestDataProduk requestDataPenjual = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                Call<ResponseDataPenjual> SimpanDataPenjual = requestDataPenjual.SendDataPenjual(nik, nama, jenis_kelamin, alamat, tempat_lahir, tanggal_lahir, no_ponsel, nama_toko);


                SimpanDataPenjual.enqueue(new Callback<ResponseDataPenjual>() {
                    @Override
                    public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {

                        if( response.isSuccessful()) {
                            int kode = response.body().getKode();
                            String pesan = response.body().getPesan();
                            if (kode == 200){
                                startActivity(new Intent(FormDataPenjualActivity.this, HalamanUtamaPenjualActivity.class));
                                Toast.makeText(FormDataPenjualActivity.this, "pesan : "+pesan, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(FormDataPenjualActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }

                        PbSimpanDataS.setVisibility(View.GONE);
                        BtnUpdate.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                        Toast.makeText(FormDataPenjualActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                        PbSimpanDataS.setVisibility(View.GONE);
                        BtnUpdate.setVisibility(View.VISIBLE);
                        //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                    }

                });
            }

        }catch (NumberFormatException exception){
            Toast.makeText(this, "Data Diri Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbSimpanDataS.setVisibility(View.GONE);
            BtnUpdate.setVisibility(View.VISIBLE);
        }

    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                EdtTala.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}