package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.BuatPesanan;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPesananActivity extends AppCompatActivity {
    private List<BuatPesanan> buatPesananList = new ArrayList<>();
    int index1;
    int id;
    int getid;
    private List<DataPenjual> dataPenjualList = new ArrayList<>();

    Button btnPesananDiterima;

    TextView Nama_BarangDetail;
    TextView MerkDetail;
    TextView HargaDetail;
    //TextView StokDetail;
    TextView SatuanDetail;
    //TextView DeskripsiDetail;
    TextView JumlahDetail;
    EditText OngkirDetail;
    EditText tvIdPesananDetail;
    TextView tvTotalHarga;
    TextView tvStatusDetail;
    TextView tvstatus;
    ImageView BeliImgProduk1Detail ;
    ImageView backDetail;

    Spinner MetodePembayaranDetail;
    int index;
    private String n_metode[] = {"COD", "TRANSFER"};

    EditText EdtNamaDetail;
    EditText EdtAlamatDetail;
    EditText EdtNopDetail;
    EditText EdtTglDetail;
    DatePickerDialog datePickerDialogDetail;
    SimpleDateFormat dateFormatDetail;

    ProgressBar PbDetailBatal;
    ProgressBar PbDetailUpdate;

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
    private String Ulatitude;
    private String Ulongitude;
    private String UNohpPesan;
    private String UtglKirimPesan;
    private String UMetodeBayarPesan;
    private String UStatus;
    private String UStatus_pesanan;
    private String UuStatus_pesanan;
    private String UBukti_transfer;
    private MultipartBody.Part UBukti_transfer1;

    private String idPesananDetail;
    private String namaPemesanDetail;
    private String namaBarangPesanDetail;
    private String merkBarangPesanDetail;
    private int jumlahBarangPesanDetail;
    private String satuanPesanDetail;
    private String gambarPesanDetail;
    private String hargaBarangPesanDetail;
    private String TotalHargaPesanDetail;
    private String ongkirPesanDetail;
    private String alamatKirimPesanDetail;
    private String NohpPesanDetail;
    private String tglKirimPesanDetail;
    private String MetodeBayarPesanDetail;
    private String StatusDetail;
    private String status = "belum dibayar";
    private String status1 = "menunggu validasi";

    Button Bayar;
    Button UpdateDetail;
    Button BatalDetail;

    private String status_pesanan = "terima";

    Button btnmapsDetail;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);
        getProfilePenjual();

        tvIdPesananDetail = findViewById(R.id.EdtNoPesanan);
        Nama_BarangDetail = findViewById(R.id.tvNamaBarangOrder);
        MerkDetail = findViewById(R.id.tvMerkOrder);
        HargaDetail = findViewById(R.id.tvHargaOrder);
        SatuanDetail = findViewById(R.id.tvSatuanOrder);
        JumlahDetail = findViewById(R.id.tvAngkaJumlahOrder);
        OngkirDetail = findViewById(R.id.RpongkirOrder);
        BeliImgProduk1Detail = findViewById(R.id.ImgItemOrder);
        MetodePembayaranDetail = findViewById(R.id.SpMetodebayarOrder);
        tvStatusDetail = findViewById(R.id.tvStatusDetail);
        tvstatus = findViewById(R.id.tvStatus);

        tvTotalHarga = findViewById(R.id.TotalBayarOrder);

        EdtNamaDetail = findViewById(R.id.EdtNamaOrder);
        EdtAlamatDetail = findViewById(R.id.EdtAlamatOrder);
        EdtNopDetail = findViewById(R.id.EdtNopOrder);
        EdtTglDetail = findViewById(R.id.EdtTabeOrder);
        dateFormatDetail = new SimpleDateFormat("yyyy-MM-dd");

        btnPesananDiterima = findViewById(R.id.BtnPesananDiterima);
        btnmapsDetail = findViewById(R.id.btnBukaMapsDetail);



        PbDetailBatal = findViewById(R.id.progressDataBatalOrder1);
        PbDetailUpdate = findViewById(R.id.progressDataUpdateOrder);
        Bayar = findViewById(R.id.btnbuatPesananOrder);
        UpdateDetail = findViewById(R.id.UpdateOrder);
        BatalDetail = findViewById(R.id.BatalOrder1);
        BatalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BatalOrderan();
            }
        });

        backDetail = findViewById(R.id.imgBackOrderProduk);
        backDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        BatalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BatalOrderan();
            }
        });

        btnPesananDiterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TerimaOrderan();
            }
        });

        Bundle bundle = getIntent().getExtras();
        Uid = bundle.getInt("id");
        UidPesanan = bundle.getString("id_pesanan");
        UnamaPemesan = bundle.getString("nama");
        UNohpPesan = bundle.getString("no_hp");
        UalamatKirimPesan = bundle.getString("alamat");
        Ulatitude = bundle.getString("latitude");
        Ulongitude = bundle.getString("longitude");
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
        UStatus_pesanan = bundle.getString("status_pesanan");
        UuStatus_pesanan = String.valueOf(UStatus_pesanan);
        UBukti_transfer = bundle.getString("bukti_transfer");

        UpdateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDetailPesan();
            }
        });

        btnmapsDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("id", Uid);
                bundle1.putString("id_pesanan", UidPesanan);
                bundle1.putString("nama", UnamaPemesan);
                bundle1.putString("no_hp", UNohpPesan);
                bundle1.putString("alamat", UalamatKirimPesan);
                bundle1.putString("latitude", Ulatitude);
                bundle1.putString("longitude", Ulongitude);
                bundle1.putString("nama_barang", UnamaBarangPesan);
                bundle1.putString("merk", UmerkBarangPesan);
                bundle1.putString("harga", UhargaBarangPesan);
                bundle1.putInt("jumlah", UjumlahBarangPesan);
                bundle1.putString("satuan", UsatuanPesan);
                bundle1.putString("gambar", UgambarPesan);
                bundle1.putString("tanggal", UtglKirimPesan);
                bundle1.putString("ongkir", UongkirPesan);
                bundle1.putString("total", UTotalHargaPesan);
                bundle1.putString("metode",UMetodeBayarPesan);
                bundle1.putString("status", UStatus);
                bundle1.putString("status_pesanan", UStatus_pesanan);
                bundle1.putString("bukti_transfer", UBukti_transfer);
                Intent intent = new Intent(DetailPesananActivity.this, MapsDetailPesananActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });


        tvIdPesananDetail.setText(UidPesanan);
        EdtNamaDetail.setText(UnamaPemesan);
        EdtNopDetail.setText(UNohpPesan);
        EdtAlamatDetail.setText(UalamatKirimPesan);
        Nama_BarangDetail.setText(UnamaBarangPesan);
        MerkDetail.setText(UmerkBarangPesan);
        HargaDetail.setText(UhargaBarangPesan);
        JumlahDetail.setText(""+UjumlahBarangPesan);
        SatuanDetail.setText(UsatuanPesan);
        Glide.with(BeliImgProduk1Detail.getContext())
                .load(UgambarPesan).into(BeliImgProduk1Detail);
        EdtTglDetail.setText(UtglKirimPesan);
        OngkirDetail.setText(UongkirPesan);
        tvTotalHarga.setText(UTotalHargaPesan);
        if (UMetodeBayarPesan.equals(n_metode[0])) index = 0;
        else if (UMetodeBayarPesan.equals(n_metode[1])) index = 1;
        MetodePembayaranDetail.setSelection(index);
        tvStatusDetail.setText(UStatus);
        tvStatusDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id_pesanan", UidPesanan);
                bundle.putString("total", UTotalHargaPesan);
                bundle.putString("bukti_transfer", UBukti_transfer);
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(DetailPesananActivity.this, ReportPembayaranActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //Log.i("status", ""+UStatus_pesanan);

        if (UuStatus_pesanan.equals(status_pesanan)){
            btnmapsDetail.setVisibility(View.GONE);
            if (index == 0){
                EdtNamaDetail.setFocusable(false);
                EdtAlamatDetail.setFocusable(false);
                EdtNopDetail.setFocusable(false);
                MetodePembayaranDetail.setEnabled(false);
                Bayar.setVisibility(View.GONE);
                UpdateDetail.setVisibility(View.GONE);
                tvStatusDetail.setVisibility(View.GONE);
                tvstatus.setVisibility(View.GONE);
                btnPesananDiterima.setVisibility(View.VISIBLE);
            }else if (UStatus.equals(status)){
                EdtNamaDetail.setFocusable(false);
                EdtAlamatDetail.setFocusable(false);
                EdtNopDetail.setFocusable(false);
                MetodePembayaranDetail.setEnabled(false);
                UpdateDetail.setVisibility(View.GONE);
                BatalDetail.setVisibility(View.VISIBLE);
                /*EdtTglDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDateDialog();
                    }
                });*/
            }else if (UStatus.equals(status1)){
                EdtNamaDetail.setFocusable(false);
                EdtAlamatDetail.setFocusable(false);
                EdtNopDetail.setFocusable(false);
                MetodePembayaranDetail.setEnabled(false);
                Bayar.setVisibility(View.GONE);
                UpdateDetail.setVisibility(View.GONE);
                tvStatusDetail.setTextColor(Color.parseColor("#FF7F00"));
            }else {
                EdtNamaDetail.setFocusable(false);
                EdtAlamatDetail.setFocusable(false);
                EdtNopDetail.setFocusable(false);
                MetodePembayaranDetail.setEnabled(false);
                Bayar.setVisibility(View.GONE);
                btnPesananDiterima.setVisibility(View.VISIBLE);
                UpdateDetail.setVisibility(View.GONE);
                tvStatusDetail.setTextColor(Color.parseColor("#008001"));
            }
        }else {
            BatalDetail.setVisibility(View.VISIBLE);
            Bayar.setVisibility(View.GONE);
            tvStatusDetail.setVisibility(View.GONE);
            tvstatus.setVisibility(View.GONE);
        }


        Bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PbDetailUpdate.setVisibility(View.VISIBLE);
                UpdateDetail.setVisibility(View.INVISIBLE);
                try {
                    //idPesananDetail;
                    namaPemesanDetail = EdtNamaDetail.getText().toString().trim();
                    //namaBarangPesanDetail;
                    //merkBarangPesanDetail;
                    //jumlahBarangPesanDetail;
                    //satuanPesanDetail;
                    //gambarPesanDetail;
                    //hargaBarangPesanDetail;
                    //TotalHargaPesanDetail;
                    //ongkirPesanDetail;
                    alamatKirimPesanDetail = EdtAlamatDetail.getText().toString().trim();
                    NohpPesanDetail = EdtNopDetail.getText().toString().trim();
                    tglKirimPesanDetail = EdtTglDetail.getText().toString().trim();
                    MetodeBayarPesanDetail = MetodePembayaranDetail.getSelectedItem().toString().trim();
                    //StatusDetail;

                    int lenNopOrder = NohpPesanDetail.length();

                    if (MetodeBayarPesanDetail.equals(n_metode[0])){
                        Toast.makeText(DetailPesananActivity.this, "Metode Pembayaran Telah Di ubah, Silahkan Update Data", Toast.LENGTH_SHORT).show();
                        PbDetailUpdate.setVisibility(View.GONE);
                        UpdateDetail.setVisibility(View.VISIBLE);
                    }else if (namaPemesanDetail.equals("")){
                        EdtNamaDetail.setError("Nama TIDAK BOLEH KOSONG");
                        PbDetailUpdate.setVisibility(View.GONE);
                        UpdateDetail.setVisibility(View.VISIBLE);
                    }else if (NohpPesanDetail.equals("")) {
                        EdtNopDetail.setError("No hp TIDAK BOLEH KOSONG");
                        PbDetailUpdate.setVisibility(View.GONE);
                        UpdateDetail.setVisibility(View.VISIBLE);
                    }else if (lenNopOrder < 12) {
                        EdtNopDetail.setError("JUMLAH Nomor TIDAK SESUAI");
                        PbDetailUpdate.setVisibility(View.GONE);
                        UpdateDetail.setVisibility(View.VISIBLE);
                    }else if (alamatKirimPesanDetail.equals("")) {
                        EdtAlamatDetail.setError("Alamat TIDAK BOLEH KOSONG");
                        PbDetailUpdate.setVisibility(View.GONE);
                        UpdateDetail.setVisibility(View.VISIBLE);
                    }else if (tglKirimPesanDetail.equals("")){
                        EdtTglDetail.setError("Tanggal TIDAK BOLEH KOSONG");
                        PbDetailUpdate.setVisibility(View.GONE);
                        UpdateDetail.setVisibility(View.VISIBLE);
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", Uid);
                        bundle.putString("id_pesanan", UidPesanan);
                        bundle.putString("nama", namaPemesanDetail);
                        bundle.putString("no_hp", NohpPesanDetail);
                        bundle.putString("alamat", alamatKirimPesanDetail);
                        bundle.putString("latitude", Ulatitude);
                        bundle.putString("longitude", Ulongitude);
                        bundle.putString("nama_barang", UnamaBarangPesan);
                        bundle.putString("merk", UmerkBarangPesan);
                        bundle.putString("harga", UhargaBarangPesan);
                        bundle.putInt("jumlah", UjumlahBarangPesan);
                        bundle.putString("satuan", UsatuanPesan);
                        bundle.putString("gambar", UgambarPesan);
                        bundle.putString("tanggal", tglKirimPesanDetail);
                        bundle.putString("ongkir", UongkirPesan);
                        bundle.putString("total", UTotalHargaPesan);
                        bundle.putString("metode", MetodeBayarPesanDetail);
                        bundle.putString("status", UStatus);
                        bundle.putString("status_pesanan", UStatus_pesanan);
                        //bundle.putInt("jumlah", count);
                        Intent intent = new Intent(DetailPesananActivity.this, BayarPesananPembeliActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);

                        //Toast.makeText(DetailPesananActivity.this, "Bisa Bayar Debet", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException exception){
                    Toast.makeText(DetailPesananActivity.this, "Data Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
                    PbDetailUpdate.setVisibility(View.GONE);
                    UpdateDetail.setVisibility(View.VISIBLE);
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
                PbDetailBatal.setVisibility(View.VISIBLE);
                BatalDetail.setVisibility(View.INVISIBLE);

                ApiRequestPembeli haDataOrderan= RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                Call<ResponseBuatPesanan> BatalOrderan = haDataOrderan.BatalDataOrderan(Uid);
                BatalOrderan.enqueue(new Callback<ResponseBuatPesanan>() {
                    @Override
                    public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                        try {

                            startActivity(new Intent(DetailPesananActivity.this, OrderanPembeliActivity.class));
                            Toast.makeText(DetailPesananActivity.this, "Orderan Telah Dibatalkan", Toast.LENGTH_SHORT).show();

                        }catch (NullPointerException nullPointerException){
                            Toast.makeText(DetailPesananActivity.this, "Data Gagal Terhapus "+nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {
                        Toast.makeText(DetailPesananActivity.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                PbDetailBatal.setVisibility(View.GONE);
                BatalDetail.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();

            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PbDetailBatal.setVisibility(View.GONE);
                BatalDetail.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    private void TerimaOrderan() {
        //int idOrderan = buatPesananList.get(index).getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah yakin pesanan diterima ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PbDetailBatal.setVisibility(View.VISIBLE);
                //BatalDetail.setVisibility(View.INVISIBLE);

                ApiRequestPembeli haDataOrderan= RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                Call<ResponseBuatPesanan> BatalOrderan = haDataOrderan.BatalDataOrderan(Uid);
                BatalOrderan.enqueue(new Callback<ResponseBuatPesanan>() {
                    @Override
                    public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                        try {

                            startActivity(new Intent(DetailPesananActivity.this, OrderanPembeliActivity.class));
                            Toast.makeText(DetailPesananActivity.this, "Orderan Telah Diterima", Toast.LENGTH_SHORT).show();

                        }catch (NullPointerException nullPointerException){
                            Toast.makeText(DetailPesananActivity.this, "Data Gagal "+nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {
                        Toast.makeText(DetailPesananActivity.this, "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                PbDetailBatal.setVisibility(View.GONE);
                //BatalDetail.setVisibility(View.VISIBLE);


            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // PbDetailBatal.setVisibility(View.GONE);
                //BatalDetail.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


    private void UpdateDetailPesan(){
        PbDetailUpdate.setVisibility(View.VISIBLE);
        UpdateDetail.setVisibility(View.INVISIBLE);
        try {
            //idPesananDetail;
            namaPemesanDetail = EdtNamaDetail.getText().toString().trim();
            //namaBarangPesanDetail;
            //merkBarangPesanDetail;
            //jumlahBarangPesanDetail;
            //satuanPesanDetail;
            //gambarPesanDetail;
            //hargaBarangPesanDetail;
            //TotalHargaPesanDetail;
            ongkirPesanDetail = OngkirDetail.getText().toString();
            alamatKirimPesanDetail = EdtAlamatDetail.getText().toString().trim();
            NohpPesanDetail = EdtNopDetail.getText().toString().trim();
            tglKirimPesanDetail = EdtTglDetail.getText().toString().trim();
            MetodeBayarPesanDetail = MetodePembayaranDetail.getSelectedItem().toString().trim();
            //StatusDetail;
            double lattujuan = Double.valueOf(Ulatitude);
            double longtujuan = Double.valueOf(Ulongitude);
            double lattoko = Double.valueOf(latitude);
            double longtoko = Double.valueOf(longitude);

            double jarakMaks = 10000;
            double jarak = getJarak(lattujuan,longtujuan, lattoko, longtoko);


            int lenNopOrder = NohpPesanDetail.length();

            if (namaPemesanDetail.equals("")){
                EdtNamaDetail.setError("Nama TIDAK BOLEH KOSONG");
                PbDetailUpdate.setVisibility(View.GONE);
                UpdateDetail.setVisibility(View.VISIBLE);
            }else if (NohpPesanDetail.equals("")) {
                EdtNopDetail.setError("No hp TIDAK BOLEH KOSONG");
                PbDetailUpdate.setVisibility(View.GONE);
                UpdateDetail.setVisibility(View.VISIBLE);
            }else if (lenNopOrder < 12) {
                EdtNopDetail.setError("JUMLAH Nomor TIDAK SESUAI");
                PbDetailUpdate.setVisibility(View.GONE);
                UpdateDetail.setVisibility(View.VISIBLE);
            }else if (alamatKirimPesanDetail.equals("")) {
                EdtAlamatDetail.setError("Alamat TIDAK BOLEH KOSONG");
                PbDetailUpdate.setVisibility(View.GONE);
                UpdateDetail.setVisibility(View.VISIBLE);
            }else if (tglKirimPesanDetail.equals("")){
                EdtTglDetail.setError("Tanggal TIDAK BOLEH KOSONG");
                PbDetailUpdate.setVisibility(View.GONE);
                UpdateDetail.setVisibility(View.VISIBLE);
            }else if (jarak > jarakMaks) {
                Toast.makeText(this, "Jarak Alamat Pengiriman Tidak Boleh Lebih Dari 10 KM", Toast.LENGTH_SHORT).show();
                //EdtAlamatBeli.setError("Jarak Alamat Pengiriman Tidak Boleh Lebih Dari 10 KM");
                PbDetailUpdate.setVisibility(View.GONE);
                UpdateDetail.setVisibility(View.VISIBLE);
            }else {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    int id = Uid;
                    ApiRequestPembeli requestDataDetailOrder = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                    Call<ResponseBuatPesanan> UpdateDetailOrder = requestDataDetailOrder.UpdateDetailPesanan(
                            id,
                            "PUT",
                            RequestBody.create(MediaType.parse("text/plain"), UidPesanan),
                            RequestBody.create(MediaType.parse("text/plain"), namaPemesanDetail),
                            RequestBody.create(MediaType.parse("text/plain"), NohpPesanDetail),
                            RequestBody.create(MediaType.parse("text/plain"), alamatKirimPesanDetail),
                            RequestBody.create(MediaType.parse("text/plain"), Ulatitude),
                            RequestBody.create(MediaType.parse("text/plain"), Ulongitude),
                            RequestBody.create(MediaType.parse("text/plain"), UnamaBarangPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UmerkBarangPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UhargaBarangPesan),
                            UjumlahBarangPesan,
                            RequestBody.create(MediaType.parse("text/plain"), UsatuanPesan),
                            RequestBody.create(MediaType.parse("text/plain"), UgambarPesan),
                            RequestBody.create(MediaType.parse("text/plain"), tglKirimPesanDetail),
                            RequestBody.create(MediaType.parse("text/plain"), ongkirPesanDetail),
                            RequestBody.create(MediaType.parse("text/plain"), UTotalHargaPesan),
                            RequestBody.create(MediaType.parse("text/plain"), MetodeBayarPesanDetail),
                            RequestBody.create(MediaType.parse("text/plain"), UStatus),
                            RequestBody.create(MediaType.parse("text/plain"), UuStatus_pesanan),
                            null

                    );
                    UpdateDetailOrder.enqueue(new Callback<ResponseBuatPesanan>() {
                        @Override
                        public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                            if( response.isSuccessful()) {
                                // int kode = response.body().getKode();
                                //String pesan = response.body().getPesan();
                                startActivity(new Intent(DetailPesananActivity.this, OrderanPembeliActivity.class));
                                Toast.makeText(DetailPesananActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();
                                /*if (kode == 200){


                                }*/

                            }else {
                                Toast.makeText(DetailPesananActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                            PbDetailUpdate.setVisibility(View.GONE);
                            UpdateDetail.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {

                            Toast.makeText(DetailPesananActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                            PbDetailUpdate.setVisibility(View.GONE);
                            UpdateDetail.setVisibility(View.VISIBLE);
                            //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                        }

                    });
                }
            }
        }catch (NumberFormatException exception) {
            Toast.makeText(DetailPesananActivity.this, "Data Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbDetailUpdate.setVisibility(View.GONE);
            UpdateDetail.setVisibility(View.VISIBLE);
        }
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialogDetail = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                if (year > calendar.get(Calendar.YEAR)){
                    Toast.makeText(DetailPesananActivity.this, "Tidak Bisa Tahun Depan", Toast.LENGTH_SHORT).show();
                    //Log.i("tahun", ""+calendar.get(Calendar.YEAR));
                }else if (year < calendar.get(Calendar.YEAR)){
                    Toast.makeText(DetailPesananActivity.this, "Tidak Bisa Tahun Lalu", Toast.LENGTH_SHORT).show();
                }else if (month < calendar.get(Calendar.MONTH)){
                    Toast.makeText(DetailPesananActivity.this, "Tidak Bisa Bulan Lalu", Toast.LENGTH_SHORT).show();
                }else if (dayOfMonth < calendar.get(Calendar.DAY_OF_MONTH)){
                    Toast.makeText(DetailPesananActivity.this, "Tidak Bisa Hari Kemarin", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    EdtTglDetail.setText(dateFormatDetail.format(newDate.getTime()));
                    //Log.i("tahun", ""+calendar.get(Calendar.YEAR));
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialogDetail.show();
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

                        latitude = dataPenjualList.get(index1).getLatitude();
                        longitude = dataPenjualList.get(index1).getLongitude();

                        //LatToko.setText(latitude);
                        //LongToko.setText(longitude);

                        //Log.i("latToko", ""+latitude);
                        //Log.i("LongToko", ""+longitude);

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
                //Toast.makeText(BeliProdukActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                // pbDataPenjual.setVisibility(View.GONE);
            }
        });

    }

    private Double getJarak(Double latTujuan, Double longTujuan, Double latToko, Double longToko){
        Double pi = 3.14;

        Double latTuj = latTujuan;
        Double longTuj = longTujuan;

        Double latTo = latToko;
        Double longTo = longToko;
        Double R = 6371e3;

        Double latTujRad = latTuj * (pi / 180);
        Double latToRad = latTo * (pi / 180);

        Double deltalatRad = (latTo - latTuj) * (pi / 180);
        Double deltaLonRad = (longTo - longTuj) * (pi / 180);

        //rumus haversine
        Double a = Math.sin(deltalatRad / 2) * Math.sin(deltalatRad / 2) + Math.cos(latTujRad) * Math.cos(latToRad) * Math.sin(deltaLonRad /2) * Math.sin(deltaLonRad /2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 -a));
        Double s = R * c ; //jarak (meter)

        return  s;
    }
}