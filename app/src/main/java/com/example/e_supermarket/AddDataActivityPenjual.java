package com.example.e_supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class AddDataActivityPenjual extends AppCompatActivity {

    EditText Nama_barang;
    EditText Merk;
    EditText Harga;
    EditText Stok;
    Spinner Satuan;
    EditText Deskripsi;
    Button BtnAddData;
    FirebaseFirestore Dbroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_penjual);

        Nama_barang = (EditText) findViewById(R.id.EdtNaBa);
        Merk = (EditText) findViewById(R.id.EdtMerk);
        Harga = (EditText) findViewById(R.id.EdtHarga);
        Stok = (EditText) findViewById(R.id.EdtStok);
        Satuan = (Spinner) findViewById(R.id.SpSatuan);
        Deskripsi = (EditText) findViewById(R.id.EdtDeskripsi);
        BtnAddData = (Button) findViewById(R.id.btnAddData);
        Dbroot = FirebaseFirestore.getInstance();

        BtnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertdataproduk();
            }
        });
    }

    private void insertdataproduk() {
       // String id = UUID.randomUUID().toString();
        String nama_barang = Nama_barang.getText().toString();
        String merk = Merk.getText().toString();
        String harga = Harga.getText().toString();
        String stok = Stok.getText().toString();
        String satuan = Satuan.getSelectedItem().toString();
        String deskripsi = Deskripsi.getText().toString();


        if (nama_barang.isEmpty()) {
            Toast.makeText(getApplicationContext(), "NAMA BARANG TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
        } else if (merk.isEmpty()) {
            Toast.makeText(getApplicationContext(), "MERK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
        } else if (harga.isEmpty()) {
            Toast.makeText(getApplicationContext(), "HARGA TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
        } else if (stok.isEmpty()) {
            Toast.makeText(getApplicationContext(), "STOK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
        } else if (satuan.isEmpty()) {
            Toast.makeText(getApplicationContext(), "SATUAN TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
        } else {
            DataProduk dataProduk = new DataProduk(/**id,**/ nama_barang, merk, harga, stok, satuan, deskripsi);

            Dbroot.collection("data_produk").add(dataProduk)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Intent intent = new Intent(getApplicationContext(), HalamanUtamaPenjualActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}