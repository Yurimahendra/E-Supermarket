package com.example.e_supermarket.Pembeli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.Chat;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatPembeliActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPembeli;
    FirebaseAuth Fauth;
    private SharedPreferences sharedPreferences;

    CircleImageView proflChatPembli;
    TextView tvNamaChatPemb;
    TextView chatPemb;
    LinearLayout lnChatPembl;
    FloatingActionButton BtnPilihKontak;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    private int index;
    private String nama ;
    private String no_ponsel ;
    private String gambar;
    TextView tvNoPenjChat;
    private String nomorPenjual;
    private String alamat ;
    private String nama_toko ;



    ProgressBar pbChatPemb;

    private List<DataPembeli> dataPembeliList = new ArrayList<>();
    private int index1;
    private String nama1 ;
    private String foto1 ;
    String no_ponsel1 ;
    TextView tvNoPembChat;
    private String nomorPembeli;

    private ArrayList<String> daftarID;
    private String ID;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_pembeli = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepembeli:
                    startActivity(new Intent(ChatPembeliActivity.this, HalamanUtamaPembeliActivity.class));
                    //startActivity(new Intent());
                    break;
                case R.id.orderanpembeli:
                    startActivity(new Intent(ChatPembeliActivity.this, OrderanPembeliActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpembeli:
                    //startActivity(new Intent(ChatPembeliActivity.this, ChatPembeliActivity.class));

                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpembeli:
                    startActivity(new Intent(ChatPembeliActivity.this, ProfilePembeliActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutpembelil:
                    //Fp = new LogoutFragmentPenjual();
                    @SuppressLint("CommitPrefEdits")
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();
                    Fauth.signOut();
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
        setContentView(R.layout.activity_chat_pembeli);
        bottomNavigationViewPembeli = findViewById(R.id.nav_pembeli);
        bottomNavigationViewPembeli.setOnNavigationItemSelectedListener(navigation_pembeli);
        sharedPreferences = getSharedPreferences("myapp-data", MODE_PRIVATE);
        Fauth = FirebaseAuth.getInstance();
        pbChatPemb = findViewById(R.id.pb_chatPemb);

        getProfilePembeli();
        getProfilePenjual();

        proflChatPembli = findViewById(R.id.profilChatPembli);
        tvNamaChatPemb = findViewById(R.id.tvNamaChatPembeli);

        //chatPemb = findViewById(R.id.textchatpemb);
        tvNoPembChat = findViewById(R.id.tvNopembChat);
        tvNoPenjChat = findViewById(R.id.tvNopenjChat);
        daftarID = new ArrayList<>();
        lnChatPembl = findViewById(R.id.lnchatPemb);

        lnChatPembl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foto_penjual", RetroServer.imageURL + gambar);
                bundle.putString("nama_penjual", nama);
                bundle.putString("no_ponsel_penjual", no_ponsel);
                bundle.putString("alamat", alamat);
                bundle.putString("nama_toko", nama_toko);

                bundle.putString("foto_pembeli", RetroServer.imageURL + foto1);
                bundle.putString("nama_pembeli", nama1);
                bundle.putString("no_ponsel_pembeli", no_ponsel1);
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(ChatPembeliActivity.this, ChattinganActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        BtnPilihKontak = findViewById(R.id.fab_msgpemb);
        BtnPilihKontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatPembeliActivity.this, HalamanKontakPembeliActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfilePenjual();
        getProfilePembeli();
    }

    private void getProfilePenjual() {
        pbChatPemb.setVisibility(View.VISIBLE);
        ApiRequestDataProduk requestDataPenjual = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataPenjual> tampilData = requestDataPenjual.RetrieveDataPenjual();

        tampilData.enqueue(new Callback<ResponseDataPenjual>() {
            @Override
            public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                if (response.isSuccessful()){
                    try {
                        dataPenjualList = response.body().getDataPenjual();

                        nama = dataPenjualList.get(index).getNama();
                        no_ponsel = dataPenjualList.get(index).getNo_ponsel();
                        gambar = dataPenjualList.get(index).getGambar();
                        alamat = dataPenjualList.get(index).getAlamat();
                        nama_toko = dataPenjualList.get(index).getNama_toko();



                        tvNamaChatPemb.setText(nama);
                        tvNoPenjChat.setText(no_ponsel);
                        Glide.with(proflChatPembli.getContext())
                                .load(RetroServer.imageURL + gambar).into(proflChatPembli);

                        //Log.i("tes", ""+no_ponsel);

                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        // Log.i("tes", ""+nik);
                    }


                    /*adapterProfilePenjual = new AdapterProfilePenjual(HalamanProfilePenjualActivity.this, dataPenjualList);
                    recyclerView.setAdapter(adapterProfilePenjual);
                    adapterProfilePenjual.notifyDataSetChanged();*/

                }

                pbChatPemb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                Toast.makeText(ChatPembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbChatPemb.setVisibility(View.GONE);
            }
        });

    }

    private void getProfilePembeli() {
        //pbProfPembeli.setVisibility(View.VISIBLE);
        ApiRequestPembeli requestDataPembeli = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseDataPembeli> tampilData = requestDataPembeli.RetrieveDataPembeli();

        tampilData.enqueue(new Callback<ResponseDataPembeli>() {
            @Override
            public void onResponse(Call<ResponseDataPembeli> call, Response<ResponseDataPembeli> response) {
                if (response.isSuccessful()){
                    try {
                        dataPembeliList = response.body().getDataPembeli();
                        nama1 = dataPembeliList.get(index1).getNama();
                        no_ponsel1 = dataPembeliList.get(index1).getNo_ponsel();
                        foto1 = dataPembeliList.get(index1).getGambar();

                        tvNoPembChat.setText(no_ponsel1);
                        Log.i("noPemb", ""+no_ponsel1);
                        Log.i("noPen", ""+no_ponsel);

                        reference.child("daftar chat").child(no_ponsel1).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                daftarID.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    ID = Objects.requireNonNull(snapshot.child("IDChat").getValue().toString());
                                    if (ID.equals(no_ponsel)){
                                        proflChatPembli.setVisibility(View.VISIBLE);
                                        tvNamaChatPemb.setVisibility(View.VISIBLE);
                                    }
                                    daftarID.add(ID);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ChatPembeliActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                            }
                        });





                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){

                    }

                }

                //pbProfPembeli.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {
                //Toast.makeText(ProfilePembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                //pbProfPembeli.setVisibility(View.GONE);
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
                        Intent intent = new Intent(ChatPembeliActivity.this, MainActivity.class);
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