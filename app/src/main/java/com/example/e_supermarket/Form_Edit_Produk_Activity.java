package com.example.e_supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Form_Edit_Produk_Activity extends AppCompatActivity {
    EditText Nama_barang;
    EditText Merk;
    EditText Harga;
    EditText Stok;
    Spinner Satuan;
    EditText Deskripsi;
    FirebaseFirestore db;

    private String uNama_Barang;
    private String uMerk;
    private Long uHarga;
    private Long uStok;
    private String uSatuan;
    private String uDeskripsi;
    private String uId;
    Button btnUpdt;

    int id_satuan;
    private String n_satuan[] = {"Kg", "Gr", "Pcs", "Lusin", "Kodi", "Gross", "Pack"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__edit__produk_);

       /** Nama_barang = findViewById(R.id.UpdtNaBa);
        Merk = findViewById(R.id.UpdtMerk);
        Harga = findViewById(R.id.UpdtHarga);
        Stok = findViewById(R.id.UpdtStok);
        Satuan = findViewById(R.id.UpdtSpSatuan);
        Deskripsi = findViewById(R.id.UpdtDeskripsi);
        btnUpdt = findViewById(R.id.btnUpdtData);**/

       // db = FirebaseFirestore.getInstance();

       /** Bundle bundle = getIntent().getExtras();
        uId = bundle.getString("id");
        uNama_Barang = bundle.getString("nama_barang");
        uMerk = bundle.getString("merk");
        uHarga = bundle.getLong("harga", 0);
        uStok = bundle.getLong("stok", 0);
        uSatuan = bundle.getString("satuan");
        uDeskripsi = bundle.getString("deskripsi");


        Nama_barang.setText(uNama_Barang);
        Merk.setText(uMerk);
        Harga.setText(uHarga.toString());
        Stok.setText(uStok.toString());
        if (uSatuan.equals(n_satuan[0])) id_satuan = 0;
        else if (uSatuan.equals(n_satuan[1])) id_satuan = 1;
        else if (uSatuan.equals(n_satuan[2])) id_satuan = 2;
        else if (uSatuan.equals(n_satuan[3])) id_satuan = 3;
        else if (uSatuan.equals(n_satuan[4])) id_satuan = 4;
        else if (uSatuan.equals(n_satuan[5])) id_satuan = 5;
        Satuan.setSelection(id_satuan);
        Deskripsi.setText(uDeskripsi);


        btnUpdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama_barang = Nama_barang.getText().toString().trim();
                String merk = Merk.getText().toString().trim();
                Long harga = Long.parseLong(Harga.getText().toString().trim());
                Long stok = Long.parseLong(Stok.getText().toString().trim());
                String satuan = Satuan.getSelectedItem().toString().trim();
                String deskripsi = Deskripsi.getText().toString().trim();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = uId;
                    updateToFirestore(id, nama_barang, merk, harga, stok, satuan, deskripsi);
                }


            }
        });**/


    }

    /**private void updateToFirestore(String id, String nama_barang, String merk, Long harga, Long stok, String satuan, String deskripsi) {

        db.collection("data_produk").document(id)
                .update("nama_barang", nama_barang, "merk", merk, "harga", harga, "stok", stok, "satuan", satuan, "deskripsi", deskripsi)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Form_Edit_Produk_Activity.this, "berhasil update!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Form_Edit_Produk_Activity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Form_Edit_Produk_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }**/
}