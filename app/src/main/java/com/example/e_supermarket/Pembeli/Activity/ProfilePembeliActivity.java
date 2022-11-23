package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Pembeli.Adapter.AdapterProfilePembeli;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Activity.FormEditProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Adapter.AdapterProdukPenjualHU;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePembeliActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPembeli;
    private SwipeRefreshLayout srlProfPembeli;
    private ProgressBar pbProfPembeli;
    private SharedPreferences sharedPreferences;

    //private RecyclerView recyclerView;
    private List<DataPembeli> dataPembeliList = new ArrayList<>();
   // private AdapterProfilePembeli adapterProfilePembeli;

    FirebaseAuth Fauth;

    TextView idPmbl;
    TextView Nama_pembeli;
    EditText Nik_Pembeli;
    EditText Tmptlahirpembeli;
    EditText Tgllahirpembeli;
    EditText Jkpembeli;
    EditText Alamatpembeli;
    EditText Noponselpembeli;
    CircleImageView ImgProfilePmbl;

    private int index = 0;

    private int id;
    private long nik ;
    private String nama ;
    private String jenis_kelamin ;
    private String no_ponsel ;
    private String tempat_lahir;
    private String tanggal_lahir ;
    private String alamat ;
    private String gambar;
    FloatingActionButton fabEdtPmbli;
    private int compare;
    private String prefs_nopon;
    private String noponUSer;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_pembeli = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homepembeli:
                    startActivity(new Intent(ProfilePembeliActivity.this, HalamanUtamaPembeliActivity.class));
                    //startActivity(new Intent());
                    break;
                case R.id.orderanpembeli:
                    startActivity(new Intent(ProfilePembeliActivity.this, OrderanPembeliActivity.class));
                    //Fp = new HalamanNotifPenjualActivity();
                    break;
                case R.id.chatpembeli:
                    startActivity(new Intent(ProfilePembeliActivity.this, ChatPembeliActivity.class));

                    //Fp = new HalamanChatPenjualActivity();
                    break;
                case R.id.profilpembeli:
                   // startActivity(new Intent(ProfilePembeliActivity.this, ProfilePembeliActivity.class));
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
        setContentView(R.layout.activity_profile_pembeli);
        bottomNavigationViewPembeli = findViewById(R.id.nav_pembeli);
        bottomNavigationViewPembeli.setOnNavigationItemSelectedListener(navigation_pembeli);

        sharedPreferences = getSharedPreferences("myapp-data", MODE_PRIVATE);
        prefs_nopon = sharedPreferences.getString("pref_nopon", null);
        Log.d("prefprofil", prefs_nopon);

        srlProfPembeli = findViewById(R.id.sw_profpembeli);
        pbProfPembeli = findViewById(R.id.pb_profilePembeli);
        Fauth = FirebaseAuth.getInstance();

        /*recyclerView = findViewById(R.id.recProfilepembeli);;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProfilePembeliActivity.this, LinearLayoutManager.VERTICAL, false));*/

        idPmbl = findViewById(R.id.tvIdPmbl);
        Nama_pembeli = findViewById(R.id.tvnamapembeli);
        Nik_Pembeli = findViewById(R.id.tvnikpembeli);
        Tmptlahirpembeli = findViewById(R.id.tvtmptlahirpembeli);
        Tgllahirpembeli = findViewById(R.id.tvtgllahirpembeli);
        Jkpembeli = findViewById(R.id.tvjkpembeli);
        Alamatpembeli = findViewById(R.id.tvalamatpembeli);
        Noponselpembeli = findViewById(R.id.tvnoponselpembeli);
        ImgProfilePmbl = findViewById(R.id.ImgProfilePembeli);

        fabEdtPmbli = (FloatingActionButton) findViewById(R.id.fab_edtPmbli);
        fabEdtPmbli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", Integer.parseInt(idPmbl.getText().toString().trim()));
                bundle.putString("nama_pembeli", Nama_pembeli.getText().toString().trim());
                bundle.putLong("nik", Long.parseLong(Nik_Pembeli.getText().toString().trim()));
                bundle.putString("tempat_lahir", Tmptlahirpembeli.getText().toString().trim());
                bundle.putString("tanggal_lahir", Tgllahirpembeli.getText().toString().trim());
                bundle.putString("jenis_kelamin", Jkpembeli.getText().toString().trim());
                bundle.putString("alamat", Alamatpembeli.getText().toString().trim());
                bundle.putString("no_ponsel", Noponselpembeli.getText().toString().trim());
                bundle.putString("gambar", RetroServer.imageURL + ImgProfilePmbl.getContext().toString().trim());

                Intent intent = new Intent(ProfilePembeliActivity.this, FormEditProfilePembeliActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getProfilePembeli();

        srlProfPembeli.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlProfPembeli.setRefreshing(true);
                getProfilePembeli();
                /**try {

                    //prefs_nopon = sharedPreferences.getString("pref_nopon", null);
                    compare = prefs_nopon.compareTo(noponUSer);
                    if (compare == 0){
                        Nama_pembeli.setVisibility(View.VISIBLE);
                        Nik_Pembeli.setVisibility(View.VISIBLE);
                        Tmptlahirpembeli.setVisibility(View.VISIBLE);
                        Tgllahirpembeli.setVisibility(View.VISIBLE);
                        Jkpembeli.setVisibility(View.VISIBLE);
                        Alamatpembeli.setVisibility(View.VISIBLE);
                        Noponselpembeli.setVisibility(View.VISIBLE);
                        ImgProfilePmbl.setVisibility(View.VISIBLE);
                        fabEdtPmbli.setVisibility(View.VISIBLE);
                    }


                }catch (NullPointerException e){

                }**/
                srlProfPembeli.setRefreshing(false);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getProfilePembeli();
        /**try {
            //prefs_nopon = sharedPreferences.getString("pref_nopon", null);
            compare = prefs_nopon.compareTo(noponUSer);
            if (compare == 0){
                Nama_pembeli.setVisibility(View.VISIBLE);
                Nik_Pembeli.setVisibility(View.VISIBLE);
                Tmptlahirpembeli.setVisibility(View.VISIBLE);
                Tgllahirpembeli.setVisibility(View.VISIBLE);
                Jkpembeli.setVisibility(View.VISIBLE);
                Alamatpembeli.setVisibility(View.VISIBLE);
                Noponselpembeli.setVisibility(View.VISIBLE);
                ImgProfilePmbl.setVisibility(View.VISIBLE);
                fabEdtPmbli.setVisibility(View.VISIBLE);
            }



        }catch (NullPointerException e){

        }**/
    }

    private void getProfilePembeli() {
        pbProfPembeli.setVisibility(View.VISIBLE);
        ApiRequestPembeli requestDataPembeli = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseDataPembeli> tampilData = requestDataPembeli.RetrieveDataPembeli();

        tampilData.enqueue(new Callback<ResponseDataPembeli>() {
            @Override
            public void onResponse(Call<ResponseDataPembeli> call, Response<ResponseDataPembeli> response) {
                if (response.isSuccessful()){
                    try {
                        dataPembeliList = response.body().getDataPembeli();


                        do {
                            no_ponsel = dataPembeliList.get(index).getNo_ponsel();
                            id = dataPembeliList.get(index).getId();
                            nama = dataPembeliList.get(index).getNama();
                            nik = dataPembeliList.get(index).getNik();
                            tempat_lahir = dataPembeliList.get(index).getTempat_lahir();
                            tanggal_lahir = dataPembeliList.get(index).getTanggal_lahir();
                            jenis_kelamin = dataPembeliList.get(index).getJenis_kelamin();
                            alamat = dataPembeliList.get(index).getAlamat();
                            gambar = dataPembeliList.get(index).getGambar();
                            index ++;
                            //noponUSer = no_ponsel;
                        }while (!no_ponsel.equals(prefs_nopon));

                        idPmbl.setText(""+id);
                        Nama_pembeli.setText(nama);
                        Nik_Pembeli.setText(""+nik);
                        Tmptlahirpembeli.setText(tempat_lahir);
                        Tgllahirpembeli.setText(tanggal_lahir);
                        Jkpembeli.setText(jenis_kelamin);
                        Alamatpembeli.setText(alamat);
                        Noponselpembeli.setText(no_ponsel);
                        Glide.with(ImgProfilePmbl.getContext())
                                .load(RetroServer.imageURL + gambar).into(ImgProfilePmbl);


                        compare = prefs_nopon.compareTo(no_ponsel);
                        if (compare == 0){
                            Nama_pembeli.setVisibility(View.VISIBLE);
                            Nik_Pembeli.setVisibility(View.VISIBLE);
                            Tmptlahirpembeli.setVisibility(View.VISIBLE);
                            Tgllahirpembeli.setVisibility(View.VISIBLE);
                            Jkpembeli.setVisibility(View.VISIBLE);
                            Alamatpembeli.setVisibility(View.VISIBLE);
                            Noponselpembeli.setVisibility(View.VISIBLE);
                            ImgProfilePmbl.setVisibility(View.VISIBLE);
                            fabEdtPmbli.setVisibility(View.VISIBLE);
                        }





                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){

                    }


                        /*adapterProfilePembeli = new AdapterProfilePembeli(ProfilePembeliActivity.this, dataPembeliList);
                        recyclerView.setAdapter(adapterProfilePembeli);
                        adapterProfilePembeli.notifyDataSetChanged();*/

                }

                pbProfPembeli.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {
                Toast.makeText(ProfilePembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbProfPembeli.setVisibility(View.GONE);
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

                        Intent intent = new Intent(ProfilePembeliActivity.this, MainActivity.class);
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