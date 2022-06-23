package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Activity.HalamanProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HalamanKontakPembeliActivity extends AppCompatActivity {
    ImageView backKontakPembeli;
    CircleImageView proflKontkPembli;
    TextView tvNamaKontakPemb;
    TextView tvNoponKontakPemb;
    TextView tvNoponpengirim;
    LinearLayout lnkontakPembl;

    private int id;
    private long nik ;
    private String nama ;
    private String jenis_kelamin ;
    private String no_ponsel ;
    private String tempat_lahir;
    private String tanggal_lahir ;
    private String alamat ;
    private String nama_toko ;
    private String nama_bank ;
    private long no_rekening ;
    private String gambar;

    private RecyclerView recyclerView;
    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    private int index;

    private List<DataPembeli> dataPembeliList = new ArrayList<>();
    private int index1;
    private String no_ponsel1 ;

    ProgressBar pbKontakPemb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_kontak_pembeli);

        pbKontakPemb = findViewById(R.id.pb_kontakPemb);

        backKontakPembeli = findViewById(R.id.imgBackKontakPembeli);
        backKontakPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        proflKontkPembli = findViewById(R.id.imgProfilKontakPembeli);
        tvNamaKontakPemb = findViewById(R.id.tvNamaKontakPembeli);
        tvNoponKontakPemb = findViewById(R.id.tvNoponKontakPembeli);
        tvNoponpengirim = findViewById(R.id.tvNoponPengirim);

        lnkontakPembl = findViewById(R.id.lnKontakPemb);
        lnkontakPembl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foto", RetroServer.imageURL + gambar);
                bundle.putString("nama", tvNamaKontakPemb.getText().toString());
                bundle.putString("no_ponsel", tvNoponKontakPemb.getText().toString());
                bundle.putString("pengirim", tvNoponpengirim.getText().toString());
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(HalamanKontakPembeliActivity.this, ChattinganActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getProfilePenjual();
        getProfilePembeli();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //pbKontakPemb.setVisibility(View.VISIBLE);
        getProfilePenjual();
        getProfilePembeli();
        //pbKontakPemb.setVisibility(View.GONE);
    }

    private void getProfilePenjual() {
        pbKontakPemb.setVisibility(View.VISIBLE);
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

                        //idPnjl.setText(""+id);
                        tvNamaKontakPemb.setText(nama);
                       // Nik.setText(""+nik);
                        //Tmptlahirpenjual.setText(tempat_lahir);
                        //Tgllahirpenjual.setText(tanggal_lahir);
                        //Jkpenjual.setText(jenis_kelamin);
                        //Alamatpenjual.setText(alamat);
                        tvNoponKontakPemb.setText(no_ponsel);
                        //Namatokopenjual.setText(nama_toko);
                        //Namabankpenjual.setText(nama_bank);
                        //NoRek.setText(""+no_rekening);
                        Glide.with(proflKontkPembli.getContext())
                                .load(RetroServer.imageURL + gambar).into(proflKontkPembli);

                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        // Log.i("tes", ""+nik);
                    }


                    /*adapterProfilePenjual = new AdapterProfilePenjual(HalamanProfilePenjualActivity.this, dataPenjualList);
                    recyclerView.setAdapter(adapterProfilePenjual);
                    adapterProfilePenjual.notifyDataSetChanged();*/

                }

                pbKontakPemb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                Toast.makeText(HalamanKontakPembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbKontakPemb.setVisibility(View.GONE);
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
                        no_ponsel1 = dataPembeliList.get(index1).getNo_ponsel();

                        tvNoponpengirim.setText(no_ponsel1);


                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){

                    }


                        /*adapterProfilePembeli = new AdapterProfilePembeli(ProfilePembeliActivity.this, dataPembeliList);
                        recyclerView.setAdapter(adapterProfilePembeli);
                        adapterProfilePembeli.notifyDataSetChanged();*/

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
}