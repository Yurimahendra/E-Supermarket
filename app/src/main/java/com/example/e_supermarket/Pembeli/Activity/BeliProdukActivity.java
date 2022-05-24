package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeliProdukActivity extends AppCompatActivity {
    //TextView Idbeli;
    TextView Nama_Barangbeli;
    TextView Merkbeli;
    TextView Hargabeli;
    TextView MinBelanjabeli;
    TextView Satuanbeli;
    //TextView Deskripsibeli;
    //TextView Jumlah;
    TextView Ongkir;
    ImageView BeliImgProduk1 ;
    ImageView backBeli;

    Spinner MetodePembayaran;
    private int index;
    private String n_metode[] = {"COD", "DEBET SALDO"};

    EditText EdtNamaBeli;
    EditText EdtAlamatBeli;
    EditText EdtNopBeli;
    EditText EdtTglBeli;
    DatePickerDialog datePickerDialogBeli;
    SimpleDateFormat dateFormatBeli;

    ProgressBar PbBuatPesan;

    ImageView Increment;
    ImageView Decrement;
    TextView HitungJumlah;
    int getMinBel ;
    //int hasilDecInc;

    int id_satuan;
    private String n_satuan[] = {"Kg", "Gr", "Pcs", "Lusin", "Kodi", "Gross", "Pack"};

    private String BeliNama_Barang;
    private String BeliMerk;
    private String BeliHarga;
    private int BeliMin_belanja;
    private int BeliMin_belanja1;
    private String BeliSatuan;
    private String BeliGambar;
    private String BeliDeskripsi;
    private int BeliId;
    //private int jumlah1;

    private String idPesanan;
    private String namaPemesan;
    private String namaBarangPesan;
    private String merkBarangPesan;
    private int minBelanjaPesan;
    private String satuanPesan;
    private String gambarPesan;
    private String hargaBarangPesan;
    private String TotalHargaPesan;
    private String ongkirPesan;
    private String alamatKirimPesan;
    private String NohpPesan;
    private String tglKirimPesan;
    private String MetodeBayarPesan;
    private String Status;


    Button btnBuatPesanan;


    int hasil;
    int nilaiRp;
    String Getharga;
    String HargaRepl;
    String HargaRepl1;
    TextView TotalBayar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_produk);

        Nama_Barangbeli = findViewById(R.id.tvNamaBarangbeli);
        Merkbeli = findViewById(R.id.tvMerkbeli);
        Hargabeli = findViewById(R.id.tvHargabeli);
        Satuanbeli = findViewById(R.id.tvSatuanbeli);
        MinBelanjabeli = findViewById(R.id.tvMinBelanjaBeli);
        //Jumlah = findViewById(R.id.tvAngkaJumlah);
        Ongkir = findViewById(R.id.Rpongkir);
        BeliImgProduk1 = findViewById(R.id.ImgItembeli);
        MetodePembayaran = findViewById(R.id.SpMetodebayar);

        EdtNamaBeli = findViewById(R.id.EdtNamaBeli);
        EdtAlamatBeli = findViewById(R.id.EdtAlamatBeli);
        EdtNopBeli = findViewById(R.id.EdtNopBeli);
        EdtTglBeli = findViewById(R.id.EdtTabe);
        dateFormatBeli = new SimpleDateFormat("yyyy-MM-dd");

        EdtTglBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        PbBuatPesan = findViewById(R.id.progressDataBeli);

        Increment = findViewById(R.id.imgTambahi);
        Decrement = findViewById(R.id.imgKurangi);
        HitungJumlah = findViewById(R.id.tvMinBelanjaBeli);
        TotalBayar = findViewById(R.id.TotalBayar);

        Increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeliMin_belanja += 1;
                MinBelanjabeli.setText(""+BeliMin_belanja);
                minBelanjaPesan =  Integer.parseInt(MinBelanjabeli.getText().toString().trim());
                HargaRepl = Getharga.replace("Rp. ", "");
                HargaRepl1 = HargaRepl.replace(".", "");
                nilaiRp = Integer.parseInt(HargaRepl1);
                hasil = nilaiRp * minBelanjaPesan;

                Locale localeID = new Locale("in", "ID");
                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                TotalBayar.setText(formatRupiah.format((double)hasil));

            }
        });


        Decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BeliMin_belanja <= BeliMin_belanja1){
                    MinBelanjabeli.setText(""+BeliMin_belanja1);
                }else {
                    BeliMin_belanja -= 1;
                    MinBelanjabeli.setText(""+BeliMin_belanja);
                    minBelanjaPesan =  Integer.parseInt(MinBelanjabeli.getText().toString().trim());
                    HargaRepl = Getharga.replace("Rp. ", "");
                    HargaRepl1 = HargaRepl.replace(".", "");
                    nilaiRp = Integer.parseInt(HargaRepl1);
                    hasil = nilaiRp * minBelanjaPesan;

                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    TotalBayar.setText(formatRupiah.format((double)hasil));
                }

            }
        });

        btnBuatPesanan = findViewById(R.id.btnbuatPesanan);
        btnBuatPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(MinBelanjabeli.getText().toString().trim()) == 0){
                    Toast.makeText(BeliProdukActivity.this, "Jumlah Satuan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else {
                    insertbuatPesanan();
                }
            }
        });

        backBeli = findViewById(R.id.imgBackBeliProduk);
        backBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        BeliId = bundle.getInt("id");
        BeliNama_Barang = bundle.getString("nama_barang");
        BeliMerk = bundle.getString("merk");
        BeliHarga = bundle.getString("harga");
        BeliMin_belanja = bundle.getInt("min_belanja", 0);
        BeliMin_belanja1 = bundle.getInt("min_belanja", 0);
        BeliSatuan = bundle.getString("satuan");
        BeliGambar = bundle.getString("gambar");
        BeliDeskripsi = bundle.getString("deskripsi");



        Nama_Barangbeli.setText(BeliNama_Barang);
        Merkbeli.setText(BeliMerk);
        Hargabeli.setText(BeliHarga);
        Satuanbeli.setText(BeliSatuan);
        MinBelanjabeli.setText(""+BeliMin_belanja);
        //getMinBel = BeliMin_belanja;
        Glide.with(BeliImgProduk1.getContext())
                .load(BeliGambar).into(BeliImgProduk1);


        Getharga = Hargabeli.getText().toString().trim();
        //minBelanjaPesan =  Integer.parseInt(MinBelanjabeli.getText().toString().trim());

        HargaRepl = Getharga.replace("Rp. ", "");
        HargaRepl1 = HargaRepl.replace(".", "");
        nilaiRp = Integer.parseInt(HargaRepl1);
        hasil = nilaiRp * BeliMin_belanja;

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        TotalBayar.setText(formatRupiah.format((double)hasil));


    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialogBeli = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                if (year > calendar.get(Calendar.YEAR)){
                    Toast.makeText(BeliProdukActivity.this, "Tidak Bisa Tahun Depan", Toast.LENGTH_SHORT).show();
                    //Log.i("tahun", ""+calendar.get(Calendar.YEAR));
                }else if (year < calendar.get(Calendar.YEAR)){
                    Toast.makeText(BeliProdukActivity.this, "Tidak Bisa Tahun Lalu", Toast.LENGTH_SHORT).show();
                }else if (month < calendar.get(Calendar.MONTH)){
                    Toast.makeText(BeliProdukActivity.this, "Tidak Bisa Bulan Lalu", Toast.LENGTH_SHORT).show();
                }else if (dayOfMonth < calendar.get(Calendar.DAY_OF_MONTH)){
                    Toast.makeText(BeliProdukActivity.this, "Tidak Bisa Hari Kemarin", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    EdtTglBeli.setText(dateFormatBeli.format(newDate.getTime()));
                    //Log.i("tahun", ""+calendar.get(Calendar.YEAR));
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialogBeli.show();
    }

    private void insertbuatPesanan() {
        PbBuatPesan.setVisibility(View.VISIBLE);
        btnBuatPesanan.setVisibility(View.INVISIBLE);
        try {
            idPesanan = UUID.randomUUID().toString();
            namaPemesan = EdtNamaBeli.getText().toString().trim();
            NohpPesan = EdtNopBeli.getText().toString().trim();
            alamatKirimPesan = EdtAlamatBeli.getText().toString().trim();
            namaBarangPesan = Nama_Barangbeli.getText().toString().trim();
            merkBarangPesan = Merkbeli.getText().toString().trim();
            hargaBarangPesan = Hargabeli.getText().toString().trim();
            minBelanjaPesan = Integer.parseInt(MinBelanjabeli.getText().toString().trim());
            //jumlahBarangPesan = Satuanbeli.getText().toString().trim();
            tglKirimPesan = EdtTglBeli.getText().toString().trim();
           // ongkirPesan = Ongkir.getText().toString().trim();
            TotalHargaPesan = TotalBayar.getText().toString().trim();
            MetodeBayarPesan = MetodePembayaran.getSelectedItem().toString();

            int lenNopBeli = NohpPesan.length();


            if (namaPemesan.equals("")) {
                EdtNamaBeli.setError("Nama TIDAK BOLEH KOSONG");
                PbBuatPesan.setVisibility(View.GONE);
                btnBuatPesanan.setVisibility(View.VISIBLE);
            }else if (alamatKirimPesan.equals("")){
                EdtAlamatBeli.setError("Alamat TIDAK BOLEH KOSONG");
                PbBuatPesan.setVisibility(View.GONE);
                btnBuatPesanan.setVisibility(View.VISIBLE);
            }else if (NohpPesan.equals("")) {
                EdtNopBeli.setError("Nomor Ponsel Tidak Boleh Kosong");
                PbBuatPesan.setVisibility(View.GONE);
                btnBuatPesanan.setVisibility(View.VISIBLE);
            }else if (lenNopBeli < 12) {
                EdtNopBeli.setError("Jumlah Nomor Ponsel Tidak Sesuai");
                PbBuatPesan.setVisibility(View.GONE);
                btnBuatPesanan.setVisibility(View.VISIBLE);
            }else if (tglKirimPesan.equals("")) {
                EdtTglBeli.setError("Tanggal TIDAK BOLEH KOSONG");
                PbBuatPesan.setVisibility(View.GONE);
                btnBuatPesanan.setVisibility(View.VISIBLE);
            }else if (MetodeBayarPesan != n_metode[0]){
                ApiRequestPembeli requestBuatPesanan = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                Call<ResponseBuatPesanan> SimpanBuatPesanan = requestBuatPesanan.SendBuatPesanan(
                        idPesanan,
                        namaPemesan,
                        NohpPesan,
                        alamatKirimPesan,
                        namaBarangPesan,
                        merkBarangPesan,
                        hargaBarangPesan,
                        minBelanjaPesan,
                        BeliSatuan,
                        BeliGambar,
                        tglKirimPesan,
                        ongkirPesan,
                        TotalHargaPesan,
                        MetodeBayarPesan,
                        "belum dibayar"
                );


                SimpanBuatPesanan.enqueue(new Callback<ResponseBuatPesanan>() {
                    @Override
                    public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {

                        if( response.isSuccessful()) {
                            //int kode = response.body().getKode();
                            //String pesan = response.body().getPesan();
                            startActivity(new Intent(getApplicationContext(), OrderanPembeliActivity.class));
                            Toast.makeText(BeliProdukActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                            /*if (kode == 200){

                                //Toast.makeText(FormDataPembeliActivity.this, "pesan : "+pesan, Toast.LENGTH_SHORT).show();
                            }*/
                        }else {
                            //Log.i("idpesanan", ""+idPesanan);
                            Toast.makeText(BeliProdukActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }

                        PbBuatPesan.setVisibility(View.GONE);
                        btnBuatPesanan.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {
                        Toast.makeText(BeliProdukActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                        PbBuatPesan.setVisibility(View.GONE);
                        btnBuatPesanan.setVisibility(View.VISIBLE);
                        //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                    }

                });
            }else {
                ApiRequestPembeli requestBuatPesanan = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                Call<ResponseBuatPesanan> SimpanBuatPesanan = requestBuatPesanan.SendBuatPesanan(
                        idPesanan,
                        namaPemesan,
                        NohpPesan,
                        alamatKirimPesan,
                        namaBarangPesan,
                        merkBarangPesan,
                        hargaBarangPesan,
                        minBelanjaPesan,
                        BeliSatuan,
                        BeliGambar,
                        tglKirimPesan,
                        ongkirPesan,
                        TotalHargaPesan,
                        MetodeBayarPesan,
                        Status
                );


                SimpanBuatPesanan.enqueue(new Callback<ResponseBuatPesanan>() {
                    @Override
                    public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {

                        if( response.isSuccessful()) {
                            //int kode = response.body().getKode();
                            //String pesan = response.body().getPesan();
                            startActivity(new Intent(getApplicationContext(), OrderanPembeliActivity.class));
                            Toast.makeText(BeliProdukActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                            /*if (kode == 200){

                                //Toast.makeText(FormDataPembeliActivity.this, "pesan : "+pesan, Toast.LENGTH_SHORT).show();
                            }*/
                        }else {
                            //Log.i("idpesanan", ""+idPesanan);
                            Toast.makeText(BeliProdukActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }

                        PbBuatPesan.setVisibility(View.GONE);
                        btnBuatPesanan.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {
                        Toast.makeText(BeliProdukActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                        PbBuatPesan.setVisibility(View.GONE);
                        btnBuatPesanan.setVisibility(View.VISIBLE);
                        //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                    }

                });

            }

        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbBuatPesan.setVisibility(View.GONE);
            btnBuatPesanan.setVisibility(View.VISIBLE);
        }catch (NullPointerException pointerException){
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            PbBuatPesan.setVisibility(View.GONE);
            btnBuatPesanan.setVisibility(View.VISIBLE);
        }


    }
}