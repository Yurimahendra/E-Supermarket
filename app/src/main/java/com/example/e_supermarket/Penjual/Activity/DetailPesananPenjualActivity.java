package com.example.e_supermarket.Penjual.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Activity.DetailPesananActivity;
import com.example.e_supermarket.Pembeli.Activity.OrderanPembeliActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPesananPenjualActivity extends AppCompatActivity {

    TextView Nama_BarangDetailPenjual;
    TextView MerkDetailPenjual;
    TextView HargaDetailPenjual;
    //TextView StokDetailPenjual;
    TextView SatuanDetailPenjual;
    //TextView DeskripsiDetailPenjual;
    TextView JumlahDetailPenjual;
    EditText OngkirDetailPenjual;
    EditText tvIdPesananDetailPenjual;
    TextView tvTotalHargaPenjual;
    ImageView BeliImgProduk1DetailPenjual ;
    ImageView backDetailPenjual;
    EditText MetodePembayaranDetailPenjual;
    EditText EdtNamaDetailPenjual;
    EditText EdtAlamatDetailPenjual;
    EditText EdtNopDetailPenjual;
    EditText EdtTglDetailPenjual;

    TextView tvStatusDetailPnjl;
    TextView tvstatusPnjl;

    private String MetodeBayarPnjl;

    private String status = "lunas";
    private String n_metode[] = {"COD", "DEBET SALDO"};
    Button btnLunas;

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

    Button BatalDetailPenjual;
    ProgressBar PbDetailBatalPenjual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_penjual);

        tvIdPesananDetailPenjual = findViewById(R.id.EdtNoPesananPenjual);
        Nama_BarangDetailPenjual = findViewById(R.id.tvNamaBarangOrderPenjual);
        MerkDetailPenjual = findViewById(R.id.tvMerkOrderPenjual);
        HargaDetailPenjual = findViewById(R.id.tvHargaOrderPenjual);
        SatuanDetailPenjual = findViewById(R.id.tvSatuanOrderPenjual);
        JumlahDetailPenjual = findViewById(R.id.tvAngkaJumlahOrderPenjual);
        OngkirDetailPenjual = findViewById(R.id.RpongkirOrderPenjual);
        BeliImgProduk1DetailPenjual = findViewById(R.id.ImgItemOrderPenjual);
        MetodePembayaranDetailPenjual = findViewById(R.id.SpMetodebayarOrderPenjual);

        tvStatusDetailPnjl = findViewById(R.id.tvStatusDetailPnjl);
        tvstatusPnjl = findViewById(R.id.tvStatusPnjl);

        tvTotalHargaPenjual = findViewById(R.id.TotalBayarOrderPenjual);
        EdtNamaDetailPenjual = findViewById(R.id.EdtNamaOrderPenjual);
        EdtAlamatDetailPenjual = findViewById(R.id.EdtAlamatOrderPenjual);
        EdtNopDetailPenjual = findViewById(R.id.EdtNopOrderPenjual);
        EdtTglDetailPenjual = findViewById(R.id.EdtTabeOrderPenjual);

        backDetailPenjual = findViewById(R.id.imgBackOrderProdukPenjual);
        BatalDetailPenjual = findViewById(R.id.BatalOrder1Penjual);
        PbDetailBatalPenjual = findViewById(R.id.progressDataBatalOrder1Penjual);

        btnLunas = findViewById(R.id.btnLunas);



        BatalDetailPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BatalOrderan();
            }
        });

        backDetailPenjual.setOnClickListener(new View.OnClickListener() {
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
        UStatus = bundle.getString("status");

        tvIdPesananDetailPenjual.setText(UidPesanan);
        EdtNamaDetailPenjual.setText(UnamaPemesan);
        EdtNopDetailPenjual.setText(UNohpPesan);
        EdtAlamatDetailPenjual.setText(UalamatKirimPesan);
        Nama_BarangDetailPenjual.setText(UnamaBarangPesan);
        MerkDetailPenjual.setText(UmerkBarangPesan);
        HargaDetailPenjual.setText(UhargaBarangPesan);
        JumlahDetailPenjual.setText(""+UjumlahBarangPesan);
        SatuanDetailPenjual.setText(UsatuanPesan);
        Glide.with(BeliImgProduk1DetailPenjual.getContext())
                .load(UgambarPesan).into(BeliImgProduk1DetailPenjual);
        EdtTglDetailPenjual.setText(UtglKirimPesan);
        OngkirDetailPenjual.setText(UongkirPesan);
        tvTotalHargaPenjual.setText(UTotalHargaPesan);
        MetodePembayaranDetailPenjual.setText(UMetodeBayarPesan);
        tvStatusDetailPnjl.setText(UStatus);

        MetodeBayarPnjl = MetodePembayaranDetailPenjual.getText().toString();


        if (MetodeBayarPnjl.equals("COD")){
            btnLunas.setVisibility(View.GONE);
            tvStatusDetailPnjl.setVisibility(View.GONE);
            tvstatusPnjl.setVisibility(View.GONE);
        }else if (UStatus.equals("lunas")){
            btnLunas.setVisibility(View.GONE);
            tvStatusDetailPnjl.setTextColor(Color.parseColor("#008001"));
        }
        //Log.i("bayar", ""+MetodeBayarPnjl);

        btnLunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PbDetailBatalPenjual.setVisibility(View.VISIBLE);
                BatalDetailPenjual.setVisibility(View.INVISIBLE);
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    int id = Uid;
                    ApiRequestPembeli requestDataDetailOrder = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                    Call<ResponseBuatPesanan> UpdateDetailOrder = requestDataDetailOrder.UpdateDetailPesanan(
                            id,
                            "PUT",
                            RequestBody.create(MediaType.parse("text/plain"), UidPesanan),
                            RequestBody.create(MediaType.parse("text/plain"), UnamaPemesan),
                            RequestBody.create(MediaType.parse("text/plain"), UNohpPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UalamatKirimPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UnamaBarangPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UmerkBarangPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UhargaBarangPesan),
                            UjumlahBarangPesan,
                            RequestBody.create(MediaType.parse("text/plain"), UsatuanPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UgambarPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UtglKirimPesan),
                            //RequestBody.create(MediaType.parse("text/plain"), ongkirPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UTotalHargaPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UMetodeBayarPesan),
                            RequestBody.create(MediaType.parse("text/plain"), status)

                    );
                    UpdateDetailOrder.enqueue(new Callback<ResponseBuatPesanan>() {
                        @Override
                        public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                            if( response.isSuccessful()) {
                                // int kode = response.body().getKode();
                                //String pesan = response.body().getPesan();
                                startActivity(new Intent(DetailPesananPenjualActivity.this, HalamanNotifPenjualActivity.class));
                                Toast.makeText(DetailPesananPenjualActivity.this, "Pesanan telah LUNAS", Toast.LENGTH_SHORT).show();
                                /*if (kode == 200){


                                }*/

                            }else {
                                Toast.makeText(DetailPesananPenjualActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                            PbDetailBatalPenjual.setVisibility(View.GONE);
                            BatalDetailPenjual.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {

                            Toast.makeText(DetailPesananPenjualActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                            PbDetailBatalPenjual.setVisibility(View.GONE);
                            BatalDetailPenjual.setVisibility(View.VISIBLE);
                            //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                        }

                    });
                }
            }
        });
    }

    private void BatalOrderan() {
        //int idOrderan = buatPesananList.get(index).getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah yakin ingin membatalkan pesanan ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PbDetailBatalPenjual.setVisibility(View.VISIBLE);
                BatalDetailPenjual.setVisibility(View.INVISIBLE);

                ApiRequestPembeli haDataOrderan= RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                Call<ResponseBuatPesanan> BatalOrderan = haDataOrderan.BatalDataOrderan(Uid);
                BatalOrderan.enqueue(new Callback<ResponseBuatPesanan>() {
                    @Override
                    public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                        try {

                            startActivity(new Intent(DetailPesananPenjualActivity.this, HalamanNotifPenjualActivity.class));
                            Toast.makeText(DetailPesananPenjualActivity.this, "Orderan Telah Dibatalkan", Toast.LENGTH_SHORT).show();

                        }catch (NullPointerException nullPointerException){
                            Toast.makeText(DetailPesananPenjualActivity.this, "Data Gagal Terhapus "+nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {
                        Toast.makeText(DetailPesananPenjualActivity.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                PbDetailBatalPenjual.setVisibility(View.GONE);
                BatalDetailPenjual.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();

            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PbDetailBatalPenjual.setVisibility(View.GONE);
                BatalDetailPenjual.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
}