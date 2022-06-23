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
    //FirebaseFirestore Dbroot;

    private List<DataPenjual> dataPenjualList;
    private AdapterProfilePenjual adapterProfilePenjual;

    //private FirebaseFirestore db;

     int p = 0;

    ProgressBar PbSimpanDataS;
    //FirebaseStorage storage;
    //StorageReference storageReference;

    int index_jk;
    private String jk[] = {"Pria", "Wanita"};

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

    String newNopon ;


    TextView LatPenj, LongPenj;
    Button btnBukaMapsdtaPenj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_penjual);


        Nik = findViewById(R.id.EdtNik);
        Nama =  findViewById(R.id.EdtNama);
        Jk = findViewById(R.id.SpJk);
        NoPons =  findViewById(R.id.EdtNop);
        TeLa = findViewById(R.id.EdtTeLa);
        Alamat = findViewById(R.id.EdtAlamat);
        Nato =  findViewById(R.id.EdtNato);
        Nabank = findViewById(R.id.EdtNaBank);
        Norek = findViewById(R.id.EdtNo_Rek);
        EdtTala = findViewById(R.id.EdtTala);
        LatPenj = findViewById(R.id.latPenjual);
        LongPenj = findViewById(R.id.longiPenjual);
        btnBukaMapsdtaPenj = findViewById(R.id.btnBukaMapsDataPenjual);

        PbSimpanDataS = findViewById(R.id.progressDataS);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        BtnUpdate = (Button) findViewById(R.id.btnUpdateData);
        //Dbroot = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            nik = bundle.getLong("nik");
            nama = bundle.getString("nama_penjual");
            jenis_kelamin = bundle.getString("jenis_kelamin");
            no_ponsel = bundle.getString("no_ponsel");
            tempat_lahir = bundle.getString("tempat_lahir");
            tanggal_lahir = bundle.getString("tanggal_lahir");
            alamat = bundle.getString("alamat");
            latitude = bundle.getString("latitude");
            longitude = bundle.getString("longitude");
            nama_toko = bundle.getString("nama_toko");
            nama_bank = bundle.getString("nama_bank");
            no_rekening = bundle.getLong("no_rek");

            Nik.setText(""+nik);
            Nama.setText(nama);
            if (Jk.equals(jk[0])) index_jk = 0;
            else if (Jk.equals(jk[1])) index_jk = 1;
            Jk.setSelection(index_jk);
            NoPons.setText(no_ponsel);
            TeLa.setText(tempat_lahir);
            EdtTala.setText(tanggal_lahir);
            Alamat.setText(alamat);
            LatPenj.setText(latitude);
            LongPenj.setText(longitude);
            Nato.setText(nama_toko);
            Nabank.setText(nama_bank);
            Norek.setText(""+no_rekening);

            Log.i("noponsel", ": "+alamat);
            Log.i("nlat", ": "+latitude);
            Log.i("longitude", ": "+longitude);

        }

        newNopon = NoPons.getText().toString().trim();
        if (newNopon.equals("")){
            NoPons.setText(getIntent().getStringExtra("mobile"));
        }







        //EditText editTextS = findViewById(R.id.EdtNop);



       // Log.i("no ponsel", ": "+no_ponsel);


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

        btnBukaMapsdtaPenj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("nama_penjual", Nama.getText().toString().trim());
                bundle.putLong("nik", Long.parseLong(Nik.getText().toString().trim()));
                bundle.putString("tempat_lahir", TeLa.getText().toString().trim());
                bundle.putString("tanggal_lahir", EdtTala.getText().toString().trim());
                bundle.putString("jenis_kelamin", Jk.getSelectedItem().toString());
                //bundle.putString("alamat", Alamat.getText().toString().trim());
                bundle.putString("no_ponsel", NoPons.getText().toString().trim());
                bundle.putString("nama_toko", Nato.getText().toString().trim());
                bundle.putString("nama_bank", Nabank.getText().toString().trim());
                bundle.putLong("no_rek", Long.parseLong(Norek.getText().toString().trim()));
                //bundle.putString("latitude", LatPenj.getText().toString().trim());
                //bundle.putString("longitude", LongPenj.getText().toString().trim());


                Intent intent = new Intent(FormDataPenjualActivity.this, PenjualMapsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //db = FirebaseFirestore.getInstance();
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