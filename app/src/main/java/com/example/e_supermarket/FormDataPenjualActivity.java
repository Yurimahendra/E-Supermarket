package com.example.e_supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        Nik = (EditText)findViewById(R.id.EdtNik);
        Nama = (EditText)findViewById(R.id.EdtNama);
        Jk = (Spinner)findViewById(R.id.SpJk);
        NoPons = (EditText)findViewById(R.id.EdtNop);
        TeLa = (EditText)findViewById(R.id.EdtTeLa);
        Alamat = (EditText)findViewById(R.id.EdtAlamat);
        Nato = (EditText)findViewById(R.id.EdtNato);
        EdtTala = (EditText)findViewById(R.id.EdtTala);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        BtnUpdate = (Button)findViewById(R.id.btnUpdateData);
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
        String nik = Nik.getText().toString();
        String nama = Nama.getText().toString();
        String jk = Jk.getSelectedItem().toString();
        String nopons = NoPons.getText().toString();
        String tela = TeLa.getText().toString();
        String tala = EdtTala.getText().toString();
        String alamat = Alamat.getText().toString();
        String nato = Nato.getText().toString();

        DataPenjual dataPenjual = new DataPenjual(nik, nama, jk, nopons, tela, tala, alamat, nato);

        Dbroot.collection("data_penjual").add(dataPenjual)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                       /** Nik.setText("");
                        Nama.setText("");
                        Jk.getSelectedItem();
                        NoPons.setText("");
                        TeLa.setText("");
                        EdtTala.setText("");
                        Alamat.setText("");
                        Nato.setText(""); **/
                        Intent intent = new Intent(getApplicationContext(), HalamanUtamaPenjualActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    }
                });
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