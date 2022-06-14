package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Date;
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
    EditText Nabank;
    EditText Norek;
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
    String nikS;
    int lenNik;
    String nama;
    String jenis_kelamin;
    String no_ponsel;
    String tempat_lahir;
    String tanggal_lahir;
    String alamat;
    String latitude;
    String longitude;
    String nama_toko;
    String nama_bank;
    long no_rekening;

    String ualamat;
    String ulatitude;
    String ulongitude;

    TextView LatPenj, LongPenj;
    Button btnBukaMapsdtaPenj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_penjual);

        LatPenj = findViewById(R.id.latPenjual);
        LongPenj = findViewById(R.id.longiPenjual);
        btnBukaMapsdtaPenj = findViewById(R.id.btnBukaMapsDataPenjual);
        btnBukaMapsdtaPenj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormDataPenjualActivity.this, PenjualMapsActivity.class));
            }
        });



        Nik = (EditText) findViewById(R.id.EdtNik);
        Nama = (EditText) findViewById(R.id.EdtNama);
        Jk = (Spinner) findViewById(R.id.SpJk);
        NoPons = (EditText) findViewById(R.id.EdtNop);
        TeLa = (EditText) findViewById(R.id.EdtTeLa);
        Alamat = (EditText) findViewById(R.id.EdtAlamat);
        Nato = (EditText) findViewById(R.id.EdtNato);
        Nabank = findViewById(R.id.EdtNaBank);
        Norek = findViewById(R.id.EdtNo_Rek);
        EdtTala = (EditText) findViewById(R.id.EdtTala);
        PbSimpanDataS = findViewById(R.id.progressDataS);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        BtnUpdate = (Button) findViewById(R.id.btnUpdateData);
        Dbroot = FirebaseFirestore.getInstance();


        EditText editTextS = findViewById(R.id.EdtNop);
        editTextS.setText(getIntent().getStringExtra("mobile")
        );


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


        Bundle bundle = getIntent().getExtras();
        ulatitude = bundle.getString("latitude");
        ulongitude = bundle.getString("longitude");
        ualamat = bundle.getString("alamat");

        LatPenj.setText(ulatitude);
        LongPenj.setText(ulongitude);
        Alamat.setText(ualamat);


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
            nama_bank = Nabank.getText().toString().trim();
            no_rekening = Long.parseLong(Norek.getText().toString().trim());
            latitude = LatPenj.getText().toString().trim();
            longitude = LongPenj.getText().toString().trim();

            nikS = Nik.getText().toString().trim();
            lenNik = nikS.length();

            if (nik <= 0 ) {
                Nik.setError("NIK TIDAK BOLEH KOSONG");
                PbSimpanDataS.setVisibility(View.GONE);
                BtnUpdate.setVisibility(View.VISIBLE);
            }else if (lenNik < 16){
                Nik.setError("JUMLAH NIK TIDAK SESUAI");
                PbSimpanDataS.setVisibility(View.GONE);
                BtnUpdate.setVisibility(View.VISIBLE);
            }else if (nama.isEmpty()) {
                Nama.setError("NAMA TIDAK BOLEH KOSONG");
                PbSimpanDataS.setVisibility(View.GONE);
                BtnUpdate.setVisibility(View.VISIBLE);
            }else if (tempat_lahir.isEmpty()) {
                TeLa.setError("TEMPAT LAHIR TIDAK BOLEH KOSONG");
                PbSimpanDataS.setVisibility(View.GONE);
                BtnUpdate.setVisibility(View.VISIBLE);
            }else if (alamat.isEmpty()) {
                Alamat.setError("ALAMAT TIDAK BOLEH KOSONG");
                PbSimpanDataS.setVisibility(View.GONE);
                BtnUpdate.setVisibility(View.VISIBLE);
            }else if (nama_toko.isEmpty()) {
                Nato.setError("NAMA TOKO TIDAK BOLEH KOSONG");
                PbSimpanDataS.setVisibility(View.GONE);
                BtnUpdate.setVisibility(View.VISIBLE);
            }else if (nama_bank.isEmpty()) {
                Nabank.setError("NAMA Bank TIDAK BOLEH KOSONG");
                PbSimpanDataS.setVisibility(View.GONE);
                BtnUpdate.setVisibility(View.VISIBLE);
            }else if (no_rekening <= 0) {
                Norek.setError("No rekening TIDAK BOLEH KOSONG");
                PbSimpanDataS.setVisibility(View.GONE);
                BtnUpdate.setVisibility(View.VISIBLE);
            } else {
                ApiRequestDataProduk requestDataPenjual = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                Call<ResponseDataPenjual> SimpanDataPenjual = requestDataPenjual.SendDataPenjual(nik, nama, jenis_kelamin, alamat, latitude, longitude, tempat_lahir, tanggal_lahir, no_ponsel, nama_toko, nama_bank, no_rekening);


                SimpanDataPenjual.enqueue(new Callback<ResponseDataPenjual>() {
                    @Override
                    public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                        if(response.isSuccessful()) {
                            //int kode = response.body().getKode();
                            //String pesan = response.body().getPesan();
                            startActivity(new Intent(getApplicationContext(), HalamanUtamaPenjualActivity.class));
                            Toast.makeText(FormDataPenjualActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                            /*if (kode == 200){

                            }*/
                        }else {
                            Toast.makeText(FormDataPenjualActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }


                        PbSimpanDataS.setVisibility(View.GONE);
                        BtnUpdate.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                       // Log.i("Ilegal", "eror "+t);

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
                if (year > calendar.get(Calendar.YEAR)){
                    Toast.makeText(FormDataPenjualActivity.this, "Tidak bisa tahun depan", Toast.LENGTH_SHORT).show();
                }else {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    EdtTala.setText(dateFormat.format(newDate.getTime()));
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}