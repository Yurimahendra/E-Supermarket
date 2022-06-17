package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_kontak_pembeli);

        backKontakPembeli = findViewById(R.id.imgBackKontakPembeli);
        backKontakPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        proflKontkPembli = findViewById(R.id.imgProfilKontakPembeli);
        tvNamaKontakPemb = findViewById(R.id.tvNamaKontakPembeli);

        lnkontakPembl = findViewById(R.id.lnKontakPemb);
        lnkontakPembl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foto", RetroServer.imageURL + gambar);
                bundle.putString("nama", tvNamaKontakPemb.getText().toString());
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(HalamanKontakPembeliActivity.this, ChattinganActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfilePenjual();
    }

    private void getProfilePenjual() {
        //pbDataPenjual.setVisibility(View.VISIBLE);
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
                        //Noponselpenjual.setText(no_ponsel);
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

                //pbDataPenjual.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                Toast.makeText(HalamanKontakPembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                //pbDataPenjual.setVisibility(View.GONE);
            }
        });

    }
}