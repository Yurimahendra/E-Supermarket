package com.example.e_supermarket.Kurir.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Kurir.Adapter.AdapterProfileKurir;
import com.example.e_supermarket.Kurir.Interface.ApiRequestDataKurir;
import com.example.e_supermarket.Kurir.Model.DataKurir;
import com.example.e_supermarket.Kurir.ResponseModel.ResponseDataKurir;
import com.example.e_supermarket.MainActivity;
import com.example.e_supermarket.Pembeli.Activity.FormEditProfilePembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.ProfilePembeliActivity;
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

public class HalamanProfilKurirActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewKurir;
    private SwipeRefreshLayout srlProfKurir;
    private ProgressBar pbProfKurir;

    //private RecyclerView recyclerViewK;
    private List<DataKurir> dataKurirList = new ArrayList<>();
    //private AdapterProfileKurir adapterProfileKurir;

    FirebaseAuth firebaseAuth;

    TextView idKurir;
    TextView nama_kurir;
    EditText nik_kurir;
    EditText TeLa_kurir;
    EditText Tala_Kurir;
    EditText jkKurir;
    EditText alamat_kurir;
    EditText nopon_kurir;
    CircleImageView ImgProfileKurir;

    private int index;
    private int id;
    private long nik ;
    private String nama ;
    private String jenis_kelamin ;
    private String no_ponsel ;
    private String tempat_lahir;
    private String tanggal_lahir ;
    private String alamat ;
    private String gambar;

    private BottomNavigationView.OnNavigationItemSelectedListener navigation_kurir = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            //Activity Fp = null;
            switch (item.getItemId()) {
                case R.id.homekurir:
                    startActivity(new Intent(HalamanProfilKurirActivity.this, HalamanUtamaKurirActivity.class));
                    //startActivity(new Intent());
                    break;
                case R.id.profilkurir:
                    // startActivity(new Intent(ProfilePembeliActivity.this, ProfilePembeliActivity.class));
                    //Fp = new HalamanProfilePenjualActivity();
                    break;
                case R.id.logoutkurir:
                    //Fp = new LogoutFragmentPenjual();
                    onBackPressed();
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
        setContentView(R.layout.activity_halaman_profil_kurir);
        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationViewKurir = findViewById(R.id.nav_kurir);
        bottomNavigationViewKurir.setOnNavigationItemSelectedListener(navigation_kurir);

        srlProfKurir = findViewById(R.id.sw_datakurir);
        pbProfKurir = findViewById(R.id.pb_dataKurir);

        idKurir = findViewById(R.id.tvIdKurir);
        nama_kurir = findViewById(R.id.tvnamaKurir);
        nik_kurir = findViewById(R.id.tvnikKurir);
        TeLa_kurir = findViewById(R.id.tvtmptlahirKurir);
        Tala_Kurir = findViewById(R.id.tvtgllahirKurir);
        jkKurir = findViewById(R.id.tvjkKurir);
        alamat_kurir = findViewById(R.id.tvalamatKurir);
        nopon_kurir = findViewById(R.id.tvnoponselKurir);
        ImgProfileKurir = findViewById(R.id.ImgProfileK);

        /*recyclerViewK = findViewById(R.id.recProfileKurir);;
        recyclerViewK.setHasFixedSize(true);
        recyclerViewK.setLayoutManager(new LinearLayoutManager(HalamanProfilKurirActivity.this, LinearLayoutManager.VERTICAL, false));
*/
        FloatingActionButton fabEdtKurir = (FloatingActionButton) findViewById(R.id.fab_edtKurir);
        fabEdtKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", Integer.parseInt(idKurir.getText().toString().trim()));
                bundle.putString("nama_kurir", nama_kurir.getText().toString().trim());
                bundle.putLong("nik", Long.parseLong(nik_kurir.getText().toString().trim()));
                bundle.putString("tempat_lahir", TeLa_kurir.getText().toString().trim());
                bundle.putString("tanggal_lahir", Tala_Kurir.getText().toString().trim());
                bundle.putString("jenis_kelamin", jkKurir.getText().toString().trim());
                bundle.putString("alamat", alamat_kurir.getText().toString().trim());
                bundle.putString("no_ponsel", nopon_kurir.getText().toString().trim());
                bundle.putString("gambar", RetroServer.imageURL + ImgProfileKurir.getContext().toString().trim());

                Intent intent = new Intent(HalamanProfilKurirActivity.this, FormEditProfileKurirActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        srlProfKurir.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlProfKurir.setRefreshing(true);
                getProfileKurir();
                srlProfKurir.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfileKurir();
    }

    private void getProfileKurir() {
        pbProfKurir.setVisibility(View.VISIBLE);
        ApiRequestDataKurir requestDataKurir = RetroServer.konekRetrofit().create(ApiRequestDataKurir.class);
        Call<ResponseDataKurir> tampilData = requestDataKurir.RetrieveDataKurir();

        tampilData.enqueue(new Callback<ResponseDataKurir>() {
            @Override
            public void onResponse(Call<ResponseDataKurir> call, Response<ResponseDataKurir> response) {
                if (response.isSuccessful()){

                        //Toast.makeText(HalamanProfilKurirActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();

                    try {
                        dataKurirList = response.body().getDataKurir();

                        id = dataKurirList.get(index).getId();
                        nama = dataKurirList.get(index).getNama();
                        nik = dataKurirList.get(index).getNik();
                        tempat_lahir = dataKurirList.get(index).getTempat_lahir();
                        tanggal_lahir = dataKurirList.get(index).getTanggal_lahir();
                        jenis_kelamin = dataKurirList.get(index).getJenis_kelamin();
                        alamat = dataKurirList.get(index).getAlamat();
                        no_ponsel = dataKurirList.get(index).getNo_ponsel();
                        gambar = dataKurirList.get(index).getGambar();

                        idKurir.setText(""+id);
                        nama_kurir.setText(nama);
                        nik_kurir.setText(""+nik);
                        TeLa_kurir.setText(tempat_lahir);
                        Tala_Kurir.setText(tanggal_lahir);
                        jkKurir.setText(jenis_kelamin);
                        alamat_kurir.setText(alamat);
                        nopon_kurir.setText(no_ponsel);
                        Glide.with(ImgProfileKurir.getContext())
                                .load(RetroServer.imageURL + gambar).into(ImgProfileKurir);
                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){

                    }


                        /*adapterProfileKurir = new AdapterProfileKurir(HalamanProfilKurirActivity.this, dataKurirList);
                        recyclerViewK.setAdapter(adapterProfileKurir);
                        adapterProfileKurir.notifyDataSetChanged();*/


                }

                pbProfKurir.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataKurir> call, Throwable t) {
                Toast.makeText(HalamanProfilKurirActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbProfKurir.setVisibility(View.GONE);
            }
        });
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
                        Intent intent = new Intent(HalamanProfilKurirActivity.this, MainActivity.class);
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