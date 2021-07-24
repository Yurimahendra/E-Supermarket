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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddDataActivityPenjual extends AppCompatActivity {

    EditText Nama_barang;
    EditText Merk;
    EditText Harga;
    EditText Stok;
    Spinner Satuan;
    EditText Deskripsi;
    Button btnAddData;
    FirebaseFirestore Dbroot;
   // FirebaseDatabase database;
   // DatabaseReference myRef;


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
        btnAddData = (Button) findViewById(R.id.btnAddData);
        // myRef = FirebaseDatabase.getInstance().getReference().child("data_produk");
        Dbroot = FirebaseFirestore.getInstance();

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertdataproduk();
            }
        });
    }

    private void insertdataproduk() {
        try {
            String id = UUID.randomUUID().toString();
            String nama_barang = Nama_barang.getText().toString().trim();
            String merk = Merk.getText().toString().trim();
            long harga = Long.parseLong(Harga.getText().toString().trim());
            long stok = Long.parseLong(Stok.getText().toString().trim());
            String satuan = Satuan.getSelectedItem().toString().trim();
            String deskripsi = Deskripsi.getText().toString().trim();

            if ( nama_barang.isEmpty()) {
                Toast.makeText(getApplicationContext(), "NAMA BARANG TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else if (merk.isEmpty()) {
                Toast.makeText(getApplicationContext(), "MERK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else if (harga <= 0  ) {
                Toast.makeText(getApplicationContext(), "HARGA TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else if (stok <= 0  ) {
                Toast.makeText(getApplicationContext(), "STOK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else if (satuan.isEmpty()) {
                Toast.makeText(getApplicationContext(), "SATUAN TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else {
                //DataProduk dataProduk = new DataProduk(id, nama_barang, merk, harga, stok, satuan, deskripsi);
                HashMap<String, Object> dataproduk = new HashMap<>();
                dataproduk.put("id", id);
                dataproduk.put("nama_barang", nama_barang);
                dataproduk.put("merk", merk);
                dataproduk.put("harga", harga);
                dataproduk.put("stok", stok);
                dataproduk.put("satuan", satuan);
                dataproduk.put("deskripsi", deskripsi);



                Dbroot.collection("data_produk").document(id)
                        .set(dataproduk)
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
            Toast.makeText(this, "Data Produk Harus Terisi Semua !", Toast.LENGTH_SHORT).show();

        }

    }
}