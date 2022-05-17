package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
import com.example.e_supermarket.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailPesananActivity extends AppCompatActivity {
    TextView Nama_BarangDetail;
    TextView MerkDetail;
    TextView HargaDetail;
    //TextView StokDetail;
    TextView SatuanDetail;
    //TextView DeskripsiDetail;
    TextView JumlahDetail;
    TextView OngkirDetail;
    TextView tvIdPesananDetail;
    TextView tvTotalHarga;
    ImageView BeliImgProduk1Detail ;
    ImageView backDetail;

    Spinner MetodePembayaranDetail;
    int index;
    private String n_metode[] = {"COD", "DEBET SALDO"};

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
    private String UNohpPesan;
    private String UtglKirimPesan;
    private String UMetodeBayarPesan;
    private String UStatus;

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

    Button Bayar;
    Button UpdateDetail;
    Button BatalDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        tvIdPesananDetail = findViewById(R.id.tvNoPesanan);
        Nama_BarangDetail = findViewById(R.id.tvNamaBarangOrder);
        MerkDetail = findViewById(R.id.tvMerkOrder);
        HargaDetail = findViewById(R.id.tvHargaOrder);
        SatuanDetail = findViewById(R.id.tvSatuanOrder);
        JumlahDetail = findViewById(R.id.tvAngkaJumlahOrder);
        OngkirDetail = findViewById(R.id.RpongkirOrder);
        BeliImgProduk1Detail = findViewById(R.id.ImgItemOrder);
        MetodePembayaranDetail = findViewById(R.id.SpMetodebayarOrder);
        tvTotalHarga = findViewById(R.id.TotalBayarOrder);

        EdtNamaDetail = findViewById(R.id.EdtNamaOrder);
        EdtAlamatDetail = findViewById(R.id.EdtAlamatOrder);
        EdtNopDetail = findViewById(R.id.EdtNopOrder);
        EdtTglDetail = findViewById(R.id.EdtTabeOrder);
        dateFormatDetail = new SimpleDateFormat("yyyy-MM-dd");

        EdtTglDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        PbDetailBatal = findViewById(R.id.progressDataBatalOrder1);
        PbDetailUpdate = findViewById(R.id.progressDataUpdateOrder);
        Bayar = findViewById(R.id.btnbuatPesananOrder);
        UpdateDetail = findViewById(R.id.UpdateOrder);
        BatalDetail = findViewById(R.id.BatalOrder1);

        backDetail = findViewById(R.id.imgBackOrderProduk);
        backDetail.setOnClickListener(new View.OnClickListener() {
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

        Bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 0){
                    Toast.makeText(DetailPesananActivity.this, "Pembelian ini menggunakan metode COD", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailPesananActivity.this, "Bisa Bayar Debet", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
}