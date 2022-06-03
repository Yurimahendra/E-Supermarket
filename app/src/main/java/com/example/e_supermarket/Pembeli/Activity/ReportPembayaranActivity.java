package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.R;

public class ReportPembayaranActivity extends AppCompatActivity {
    EditText no_pesanan;
    EditText Total_harga;
    ImageView Bukti_tf;
    private String UidPesanan;
    private String UTotalHargaPesan;
    private String UBukti_transfer;
    private ImageView BackBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pembayaran);
        
        no_pesanan = findViewById(R.id.EdtNoPesananReport);
        Total_harga = findViewById(R.id.EdtTotalReport);
        Bukti_tf = findViewById(R.id.ImgBuktiBayarReport);
        BackBayar = findViewById(R.id.imgBackReportbayar);
        BackBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        UidPesanan = bundle.getString("id_pesanan");
        UTotalHargaPesan = bundle.getString("total");
        UBukti_transfer = bundle.getString("bukti_transfer");
        
        no_pesanan.setText(UidPesanan);
        Total_harga.setText(UTotalHargaPesan);
        Glide.with(Bukti_tf.getContext())
                .load(UBukti_transfer).into(Bukti_tf);
        
    }
}