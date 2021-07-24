package com.example.e_supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

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


    }

    private void insertdatapenjual() {
        try {
            Long nik = Long.parseLong(Nik.getText().toString().trim());
            String nama = Nama.getText().toString().trim();
            String jenis_kelamin = Jk.getSelectedItem().toString().trim();
            String no_ponsel = NoPons.getText().toString().trim();
            String tempat_lahir = TeLa.getText().toString().trim();
            String tanggal_lahir = EdtTala.getText().toString().trim();
            String alamat = Alamat.getText().toString().trim();
            String nama_toko = Nato.getText().toString().trim();

            if (nik == null) {
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
               // DataPenjual dataPenjual = new DataPenjual(nik, nama, jenis_kelamin, no_ponsel, tempat_lahir, tanggal_lahir, alamat, nama_toko);

                HashMap<String, Object> datapenjual = new HashMap<>();
                datapenjual.put("nik", nik);
                datapenjual.put("nama", nama);
                datapenjual.put("jenis_kelamin", jenis_kelamin);
                datapenjual.put("no_ponsel", no_ponsel);
                datapenjual.put("tempat_lahir", tempat_lahir);
                datapenjual.put("tanggal_lahir", tanggal_lahir);
                datapenjual.put("alamat", alamat);
                datapenjual.put("nama_toko", nama_toko);



                Dbroot.collection("data_penjual").document(nik.toString())
                        .set(datapenjual)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getApplicationContext(), HalamanUtamaPenjualActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }catch (NumberFormatException exception){
            Toast.makeText(this, "Data Diri Harus Terisi Semua !", Toast.LENGTH_SHORT).show();

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