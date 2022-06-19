package com.example.e_supermarket.Kurir.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Penjual.Activity.DetailPesananPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanNotifPenjualActivity;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPesananKurirActivity extends AppCompatActivity {
    TextView Nama_BarangDetailKurir;
    TextView MerkDetailKurir;
    TextView HargaDetailKurir;
    //TextView StokDetailKurir;
    TextView SatuanDetailKurir;
    //TextView DeskripsiDetailKurir;
    TextView JumlahDetailKurir;
    EditText OngkirDetailKurir;
    EditText tvIdPesananDetailKurir;
    TextView tvTotalHargaKurir;
    ImageView BeliImgProduk1DetailKurir;
    ImageView backDetailKurir;
    EditText MetodePembayaranDetailKurir;
    EditText EdtNamaDetailKurir;
    EditText EdtAlamatDetailKurir;
    EditText EdtNopDetailKurir;
    EditText EdtTglDetailKurir;

    private int Uid;
    private String UidPesanan;
    private String UnamaPemesan;
    private String UnamaBarangPesan;
    private String UmerkBarangPesan;
    private int UjumlahBarangPesan;
    private String UsatuanPesan;
    private String UgambarPesan;
    private String UhargaBarangPesan;
    private String UTotalHargaPesan;
    private String UongkirPesan;
    private String UalamatKirimPesan;
    private String UNohpPesan;
    private String UtglKirimPesan;
    private String UMetodeBayarPesan;
    private String UStatus;
    private String latitude;
    private String longitude;

    private TextView LatDetKurir;
    private TextView LongDetKurir;

    Button TerkirimDetailKurir;
    ProgressBar PbDetailTerkirimKurir;
    Button btnLihatMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_kurir);

        tvIdPesananDetailKurir = findViewById(R.id.EdtNoPesananKurir);
        Nama_BarangDetailKurir = findViewById(R.id.tvNamaBarangOrderKurir);
        MerkDetailKurir = findViewById(R.id.tvMerkOrderKurir);
        HargaDetailKurir = findViewById(R.id.tvHargaOrderKurir);
        SatuanDetailKurir = findViewById(R.id.tvSatuanOrderKurir);
        JumlahDetailKurir = findViewById(R.id.tvAngkaJumlahOrderKurir);
        OngkirDetailKurir = findViewById(R.id.RpongkirOrderKurir);
        BeliImgProduk1DetailKurir = findViewById(R.id.ImgItemOrderKurir);
        MetodePembayaranDetailKurir = findViewById(R.id.SpMetodebayarOrderKurir);
        tvTotalHargaKurir = findViewById(R.id.TotalBayarOrderKurir);
        EdtNamaDetailKurir = findViewById(R.id.EdtNamaOrderKurir);
        EdtAlamatDetailKurir = findViewById(R.id.EdtAlamatOrderKurir);
        EdtNopDetailKurir = findViewById(R.id.EdtNopOrderKurir);
        EdtTglDetailKurir = findViewById(R.id.EdtTabeOrderKurir);
        LatDetKurir = findViewById(R.id.tvLatDetailKurir);
        LongDetKurir = findViewById(R.id.tvLongDetailKurir);

        backDetailKurir = findViewById(R.id.imgBackOrderProdukKurir);
        //TerkirimDetailKurir = findViewById(R.id.TerkirimOrder1Kurir);
        //PbDetailTerkirimKurir = findViewById(R.id.progressDataTerkirimOrder1Kurir);

        /*TerkirimDetailKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BatalOrderan();
            }
        });*/

        backDetailKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        Uid = bundle.getInt("id");
        UidPesanan = bundle.getString("id_pesanan");
        UnamaPemesan = bundle.getString("nama");
        UNohpPesan = bundle.getString("no_hp");
        UalamatKirimPesan = bundle.getString("alamat");
        UnamaBarangPesan = bundle.getString("nama_barang");
        UmerkBarangPesan = bundle.getString("merk");
        UhargaBarangPesan = bundle.getString("harga");
        UjumlahBarangPesan= bundle.getInt("jumlah", 0);
        UsatuanPesan = bundle.getString("satuan");
        UgambarPesan = bundle.getString("gambar");
        UtglKirimPesan = bundle.getString("tanggal");
        UongkirPesan = bundle.getString("ongkir");
        UTotalHargaPesan = bundle.getString("total");
        UMetodeBayarPesan = bundle.getString("metode");
        latitude = bundle.getString("latitude");
        longitude = bundle.getString("longitude");

        tvIdPesananDetailKurir.setText(UidPesanan);
        EdtNamaDetailKurir.setText(UnamaPemesan);
        EdtNopDetailKurir.setText(UNohpPesan);
        EdtAlamatDetailKurir.setText(UalamatKirimPesan);
        Nama_BarangDetailKurir.setText(UnamaBarangPesan);
        MerkDetailKurir.setText(UmerkBarangPesan);
        HargaDetailKurir.setText(UhargaBarangPesan);
        JumlahDetailKurir.setText(""+UjumlahBarangPesan);
        SatuanDetailKurir.setText(UsatuanPesan);
        Glide.with(BeliImgProduk1DetailKurir.getContext())
                .load(UgambarPesan).into(BeliImgProduk1DetailKurir);
        EdtTglDetailKurir.setText(UtglKirimPesan);
        OngkirDetailKurir.setText(UongkirPesan);
        tvTotalHargaKurir.setText(UTotalHargaPesan);
        MetodePembayaranDetailKurir.setText(UMetodeBayarPesan);
        LatDetKurir.setText(latitude);
        LongDetKurir.setText(longitude);

        btnLihatMaps = findViewById(R.id.LihatMapKurir);
        btnLihatMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("latitude", LatDetKurir.getText().toString());
                bundle.putString("longitude", LongDetKurir.getText().toString());
                bundle.putString("alamat", EdtAlamatDetailKurir.getText().toString());
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(DetailPesananKurirActivity.this, KurirMapsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void BatalOrderan() {
        //int idOrderan = buatPesananList.get(index).getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah yakin pesanan Sudah Diterima Pembeli?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PbDetailTerkirimKurir.setVisibility(View.VISIBLE);
                TerkirimDetailKurir.setVisibility(View.INVISIBLE);

                ApiRequestPembeli haDataOrderan= RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                Call<ResponseBuatPesanan> BatalOrderan = haDataOrderan.BatalDataOrderan(Uid);
                BatalOrderan.enqueue(new Callback<ResponseBuatPesanan>() {
                    @Override
                    public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                        try {

                            startActivity(new Intent(DetailPesananKurirActivity.this, HalamanUtamaKurirActivity.class));
                            Toast.makeText(DetailPesananKurirActivity.this, "Orderan Telah Terkirim", Toast.LENGTH_SHORT).show();

                        }catch (NullPointerException nullPointerException){
                            Toast.makeText(DetailPesananKurirActivity.this, "btn EROR "+nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {
                        Toast.makeText(DetailPesananKurirActivity.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                PbDetailTerkirimKurir.setVisibility(View.GONE);
                TerkirimDetailKurir.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();

            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PbDetailTerkirimKurir.setVisibility(View.GONE);
                TerkirimDetailKurir.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
}