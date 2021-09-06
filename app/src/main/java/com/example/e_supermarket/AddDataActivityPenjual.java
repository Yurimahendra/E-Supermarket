package com.example.e_supermarket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.grpc.Compressor;

public class AddDataActivityPenjual extends AppCompatActivity {

    EditText Nama_barang;
    EditText Merk;
    EditText Harga;
    EditText Stok;
    Spinner Satuan;
    EditText Deskripsi;
    ImageView addImage;
    Button btnAddData;
    FirebaseFirestore Dbroot;

    public Uri imageUri;
    private Bitmap compressor;

    String id;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Task<Uri> Url;
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
        addImage = (ImageView) findViewById(R.id.ImgProduk);
        Deskripsi = (EditText) findViewById(R.id.EdtDeskripsi);
        btnAddData = (Button) findViewById(R.id.btnAddData);
        // myRef = FirebaseDatabase.getInstance().getReference().child("data_produk");
        Dbroot = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertdataproduk();
            }
        });
    }

    private void pilihGambar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            //Bitmap image = (Bitmap) data.getExtras().get("data");
            //addImage.setImageBitmap(image);
            imageUri = data.getData();
            addImage.setImageURI(imageUri);
            uploadGambar();
        }
    }

    public void uploadGambar() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Upload gambar....");
        pd.show();

        id = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + id);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Gambar Berhasil Di Upload", Toast.LENGTH_SHORT).show();
                       // Snackbar.make(findViewById(R.id.dialogplus_view_container), "image uploaded", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "gagal upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Proses" + (int) progressPercent + "%");
                    }
                });







       /** ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        compressor.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] thumb = byteArrayOutputStream.toByteArray();

        UploadTask imagePath = storageReference.child("images").child(id+".jpg").putBytes(thumb);

        imagePath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    StoreData(task);
                }else {
                    String error = task.getException().getMessage();
                    Toast.makeText(AddDataActivityPenjual.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                }
            }
        });**/
    }

  /**  private void StoreData(Task<UploadTask.TaskSnapshot> task) {
        Uri downloadUri;
        if (task != null){
            downloadUri = task.getResult().getDownL
        }
    }**/

    private void insertdataproduk() {

        try {
            StorageReference storage = FirebaseStorage.getInstance().getReference();
            StorageReference dateRef = storage.child("images" +id);
            dateRef.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });



            /**https.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(AddDataActivityPenjual.this)
                            .load(uri)
                            .error(R.drawable.ic_launcher_background)
                            .into(addImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });**/

            String id = UUID.randomUUID().toString();
            String nama_barang = Nama_barang.getText().toString().trim();
            String merk = Merk.getText().toString().trim();
            long harga = Long.parseLong(Harga.getText().toString().trim());
            long stok = Long.parseLong(Stok.getText().toString().trim());
            String satuan = Satuan.getSelectedItem().toString().trim();
            String image = dateRef.toString();
            String deskripsi = Deskripsi.getText().toString().trim();

            if (nama_barang.isEmpty()) {
                Toast.makeText(getApplicationContext(), "NAMA BARANG TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else if (merk.isEmpty()) {
                Toast.makeText(getApplicationContext(), "MERK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else if (harga <= 0) {
                Toast.makeText(getApplicationContext(), "HARGA TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else if (stok <= 0) {
                Toast.makeText(getApplicationContext(), "STOK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else if (satuan.isEmpty()) {
                Toast.makeText(getApplicationContext(), "SATUAN TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            }else if (image.isEmpty()) {
                Toast.makeText(getApplicationContext(), "GAMBAR TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
            } else {
                //DataProduk dataProduk = new DataProduk(id, nama_barang, merk, harga, stok, satuan, deskripsi);
                HashMap<String, Object> dataproduk = new HashMap<>();
                dataproduk.put("id", id);
                dataproduk.put("nama_barang", nama_barang);
                dataproduk.put("merk", merk);
                dataproduk.put("harga", harga);
                dataproduk.put("stok", stok);
                dataproduk.put("satuan", satuan);
                dataproduk.put("gambar", image);
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
        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Produk Harus Terisi Semua !", Toast.LENGTH_SHORT).show();

        }

    }
}