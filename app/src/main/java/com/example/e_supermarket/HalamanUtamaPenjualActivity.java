package com.example.e_supermarket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class HalamanUtamaPenjualActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewPenjual;
    FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public Uri imageUri;
    ImageView imageProduk;


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
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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
