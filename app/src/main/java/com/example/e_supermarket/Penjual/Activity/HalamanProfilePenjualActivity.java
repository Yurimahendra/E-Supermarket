package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Pembeli.Activity.ProfilePembeliActivity;
import com.example.e_supermarket.Pembeli.Adapter.AdapterProfilePembeli;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Adapter.AdapterProfilePenjual;
import com.example.e_supermarket.Penjual.Fragment.ProfileFragmentPenjual;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanProfilePenjualActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPenjual;
    FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    private DataPenjual dataPenjual = new DataPenjual();
   // private AdapterProfilePenjual adapterProfilePenjual;
    private int index;

    private FirebaseFirestore db;

    private SwipeRefreshLayout srlDataPenjual;
    private ProgressBar pbDataPenjual;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    TextView idPnjl;
    TextView Nama_penjual;
    EditText Nik;
    EditText Tmptlahirpenjual;
    EditText Tgllahirpenjual;
    EditText Jkpenjual;
    EditText Alamatpenjual;
    EditText Noponselpenjual;
    EditText Namatokopenjual;
    EditText Namabankpenjual;
    EditText NoRek;
    TextView LatitudePenjual;
    TextView LongitudePenjual;
    CircleImageView imgProfilePenjual;

    private int id;
    private long nik ;
    private String nama ;
    private String jenis_kelamin ;
    private String no_ponsel ;
    private String tempat_lahir;
    private String tanggal_lahir ;
    private String alamat ;
    private String latitude;
    private String longitude;
    private String nama_toko ;
    private String nama_bank ;
    private long no_rekening ;
    private String gambar;


    private BottomNavigationView.OnNavigationItemSelectedListener navigation_penjual = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepenjual:
                    startActivity(new Intent(HalamanProfilePenjualActivity.this, HalamanUtamaPenjualActivity.class));
                    break;
                case R.id.notifpenjual:
                    startActivity(new Intent(HalamanProfilePenjualActivity.this, HalamanNotifPenjualActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpenjual:
                    startActivity(new Intent(HalamanProfilePenjualActivity.this, HalamanChatPenjualActivity.class));
                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpenjual:
                    //startActivity(new Intent(HalamanNotifPenjualActivity.this, HalamanProfilePenjualActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutpenjual:
                    //Fp = new LogoutFragmentPenjual();
                    firebaseAuth.signOut();
                    onBackPressedOut();
                    return true;

            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_penjual, Fp).commit();
            getContentTransitionManager();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_profile_penjual);
        bottomNavigationViewPenjual = findViewById(R.id.nav_penjual);
        bottomNavigationViewPenjual.setOnNavigationItemSelectedListener(navigation_penjual);
        firebaseAuth = FirebaseAuth.getInstance();

        /*recyclerView = findViewById(R.id.recProfilepenjual);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));*/

        //db = FirebaseFirestore.getInstance();

        //adapterProfilePenjual = new AdapterProfilePenjual(HalamanProfilePenjualActivity.this, dataPenjualList);

        //recyclerView.setAdapter(adapterProfilePenjual);

        idPnjl = findViewById(R.id.tvIdPnjl);
        Nama_penjual = findViewById(R.id.tvnamapenjual);
        Nik = findViewById(R.id.tvnikpenjual);
        Tmptlahirpenjual = findViewById(R.id.tvtmptlahirpenjual);
        Tgllahirpenjual = findViewById(R.id.tvtgllahirpenjual);
        Jkpenjual = findViewById(R.id.tvjkpenjual);
        Alamatpenjual = findViewById(R.id.tvalamatpenjual);
        Noponselpenjual = findViewById(R.id.tvnoponselpenjual);
        Namatokopenjual = findViewById(R.id.tvnamatokopenjual);
        Namabankpenjual = findViewById(R.id.tvNaBank);
        NoRek = findViewById(R.id.tvNo_Rek);
        LatitudePenjual = findViewById(R.id.tvLatitudePnjl);
        LongitudePenjual = findViewById(R.id.tvLongiPnjl);
        imgProfilePenjual = findViewById(R.id.ImgProfilePenjual);


        FloatingActionButton fabEdtPnjl = (FloatingActionButton) findViewById(R.id.fab_edtPnjl);
        fabEdtPnjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DataPenjual item = dataPenjualList.get(index);
                Bundle bundle = new Bundle();
                bundle.putInt("id", Integer.parseInt(idPnjl.getText().toString().trim()));
                bundle.putString("nama_penjual", Nama_penjual.getText().toString().trim());
                bundle.putLong("nik", Long.parseLong(Nik.getText().toString().trim()));
                bundle.putString("tempat_lahir", Tmptlahirpenjual.getText().toString().trim());
                bundle.putString("tanggal_lahir", Tgllahirpenjual.getText().toString().trim());
                bundle.putString("jenis_kelamin", Jkpenjual.getText().toString().trim());
                bundle.putString("alamat", Alamatpenjual.getText().toString().trim());
                bundle.putString("no_ponsel", Noponselpenjual.getText().toString().trim());
                bundle.putString("nama_toko", Namatokopenjual.getText().toString().trim());
                bundle.putString("nama_bank", Namabankpenjual.getText().toString().trim());
                bundle.putLong("no_rek", Long.parseLong(NoRek.getText().toString().trim()));
                bundle.putString("latitude", LatitudePenjual.getText().toString().trim());
                bundle.putString("longitude", LongitudePenjual.getText().toString().trim());
                bundle.putString("gambar", RetroServer.imageURL + imgProfilePenjual.getContext().toString().trim());

                Intent intent = new Intent(HalamanProfilePenjualActivity.this, FormEditProfilePenjualActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        srlDataPenjual = findViewById(R.id.sw_datapenjual);
        pbDataPenjual = findViewById(R.id.pb_datapenjual);

        srlDataPenjual.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataPenjual.setRefreshing(true);
                getProfilePenjual();
                srlDataPenjual.setRefreshing(false);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getProfilePenjual();
    }

    private void getProfilePenjual() {
        pbDataPenjual.setVisibility(View.VISIBLE);
        ApiRequestDataProduk requestDataPenjual = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataPenjual> tampilData = requestDataPenjual.RetrieveDataPenjual();

        tampilData.enqueue(new Callback<ResponseDataPenjual>() {
            @Override
            public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                if (response.isSuccessful()){
                    try {
                        dataPenjualList = response.body().getDataPenjual();

                        id = dataPenjualList.get(index).getId();
                        nama = dataPenjualList.get(index).getNama();
                        nik = dataPenjualList.get(index).getNik();
                        tempat_lahir = dataPenjualList.get(index).getTempat_lahir();
                        tanggal_lahir = dataPenjualList.get(index).getTanggal_lahir();
                        jenis_kelamin = dataPenjualList.get(index).getJenis_kelamin();
                        alamat = dataPenjualList.get(index).getAlamat();
                        no_ponsel = dataPenjualList.get(index).getNo_ponsel();
                        nama_toko = dataPenjualList.get(index).getNama_toko();
                        nama_bank = dataPenjualList.get(index).getNama_bank();
                        no_rekening = dataPenjualList.get(index).getNo_rekening();
                        gambar = dataPenjualList.get(index).getGambar();

                       // Log.i("tes", ""+nik);

                        idPnjl.setText(""+id);
                        Nama_penjual.setText(nama);
                        Nik.setText(""+nik);
                        Tmptlahirpenjual.setText(tempat_lahir);
                        Tgllahirpenjual.setText(tanggal_lahir);
                        Jkpenjual.setText(jenis_kelamin);
                        Alamatpenjual.setText(alamat);
                        Noponselpenjual.setText(no_ponsel);
                        Namatokopenjual.setText(nama_toko);
                        Namabankpenjual.setText(nama_bank);
                        NoRek.setText(""+no_rekening);
                        Glide.with(imgProfilePenjual.getContext())
                                .load(RetroServer.imageURL + gambar).into(imgProfilePenjual);

                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                       // Log.i("tes", ""+nik);
                    }


                    /*adapterProfilePenjual = new AdapterProfilePenjual(HalamanProfilePenjualActivity.this, dataPenjualList);
                    recyclerView.setAdapter(adapterProfilePenjual);
                    adapterProfilePenjual.notifyDataSetChanged();*/

                }

                pbDataPenjual.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                Toast.makeText(HalamanProfilePenjualActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbDataPenjual.setVisibility(View.GONE);
            }
        });

    }


    private void onBackPressedOut() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("Kamu yakin ingin keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //firebaseAuth.signOut();
                        Intent intent = new Intent(HalamanProfilePenjualActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
}