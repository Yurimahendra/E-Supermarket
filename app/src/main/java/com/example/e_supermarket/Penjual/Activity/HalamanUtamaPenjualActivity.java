package com.example.e_supermarket.Penjual.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_supermarket.Penjual.Adapter.AdapterProdukPenjualHU;
import com.example.e_supermarket.Penjual.Fragment.ChatFragmentPenjual;
import com.example.e_supermarket.Penjual.Fragment.HomeFragmentPenjual;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.Fragment.NotifFragmentPenjual;
import com.example.e_supermarket.Penjual.Fragment.ProfileFragmentPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanUtamaPenjualActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewPenjual;
    FirebaseAuth firebaseAuth;
    //private FirebaseStorage storage;
    //private StorageReference storageReference;
    public Uri imageUri;
    ImageView imageProduk;

    private RecyclerView recyclerView;
    private List<DataProduk> dataProdukList = new ArrayList<>();
    private AdapterProdukPenjualHU adapterProdukPenjualHU;


    private BottomNavigationView.OnNavigationItemSelectedListener navigation_penjual = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment Fp = null;
            switch (item.getItemId()) {
                case R.id.homepenjual:
                    Fp = new HomeFragmentPenjual();
                    break;
                case R.id.notifpenjual:
                    Fp = new NotifFragmentPenjual();
                    break;
                case R.id.chatpenjual:
                    Fp = new ChatFragmentPenjual();
                    break;
                case R.id.profilpenjual:
                    Fp = new ProfileFragmentPenjual();
                    break;
                case R.id.logoutpenjual:
                    //Fp = new LogoutFragmentPenjual();
                    onBackPressed();
                    return true;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_penjual, Fp).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama_penjual);
       // storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReference();

        /*recyclerView = (RecyclerView)findViewById(R.id.recItem);;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HalamanUtamaPenjualActivity.this, LinearLayoutManager.VERTICAL, false));

        retrieveData();*/
        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationViewPenjual = findViewById(R.id.nav_penjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(navigation_penjual);


        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddDataActivityPenjual.class));
            }
        });
       // CheckUserStatus(this);
    }

    /*public void retrieveData(){
        ApiRequestDataProduk requestDataProduk = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataProduk> tampilData = requestDataProduk.RetrieveDataProduk();

        tampilData.enqueue(new Callback<ResponseDataProduk>() {
            @Override
            public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(HalamanUtamaPenjualActivity.this, "kode : "+kode+" | pesan : "+pesan, Toast.LENGTH_SHORT).show();

                dataProdukList = response.body().getData();

                adapterProdukPenjualHU = new AdapterProdukPenjualHU(HalamanUtamaPenjualActivity.this, dataProdukList);
                recyclerView.setAdapter(adapterProdukPenjualHU);
                adapterProdukPenjualHU.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseDataProduk> call, Throwable t) {
                Toast.makeText(HalamanUtamaPenjualActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });


    }*/

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("Kamu yakin ingin keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        Intent intent = new Intent(HalamanUtamaPenjualActivity.this, SendOTPActivityPenjual.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


   /** @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            imageProduk.setImageURI(imageUri);

            uploadGambar();
        }
    }

    private void uploadGambar() {
        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setTitle("Upload gambar....");
        pd.show();

        final String id = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + id);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(R.id.dialogplus_content_container), "image uploaded", Snackbar.LENGTH_SHORT).show();
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

    }**/
    /**private void CheckUserStatus(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Kamu yakin ingin keluar?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseAuth.signOut();
                startActivity(new Intent(HalamanUtamaPenjualActivity.this, SendOTPActivityPenjual.class));
                System.exit(0);
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
     FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
     if (firebaseUser != null){
     //setContentView(R.layout.activity_halaman_utama_penjual);
     }
     else {
     FirebaseAuth.getInstance().signOut();
     startActivity(new Intent(getApplicationContext(), SendOTPActivityPenjual.class));
     finish();
     }
     }**/


}
