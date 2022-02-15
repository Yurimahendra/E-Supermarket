package com.example.e_supermarket.Pembeli.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Activity.AddDataActivityPenjual;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormDataPembeliActivity extends AppCompatActivity {

    EditText NikB;
    EditText NamaB;
    Spinner JkB;
    EditText editNopB;
    EditText TeLaB;
    EditText AlamatB;
    EditText EdtTalaB;
    DatePickerDialog datePickerDialogB;
    SimpleDateFormat dateFormatB;

    Button btnsimpanDataB;
    ProgressBar PbSimpanDataB;

    long nikB;
    String namaB;
    String jkB;
    String editnopB;
    String teLaB;
    String alamatB;
    String edttalaB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_pembeli);

        NikB = findViewById(R.id.EdtNikB);
        NamaB = findViewById(R.id.EdtNamaB);
        JkB = findViewById(R.id.SpJkB);
        TeLaB = findViewById(R.id.EdtTeLaB);
        EdtTalaB = findViewById(R.id.EdtTalaB);
        AlamatB = findViewById(R.id.EdtAlamatB);
        editNopB = findViewById(R.id.EdtNopB);
        btnsimpanDataB = findViewById(R.id.btnUpdateDataB);
        PbSimpanDataB = findViewById(R.id.pbDataPembeli);

        editNopB.setText(String.format(
                "%s", getIntent().getStringExtra("mobile")
        ));



        dateFormatB = new SimpleDateFormat("yyyy-MM-dd");

        EdtTalaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


        btnsimpanDataB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdatapembeli();
            }
        });

    }

    private void insertdatapembeli() {
        PbSimpanDataB.setVisibility(View.VISIBLE);
        btnsimpanDataB.setVisibility(View.INVISIBLE);
        try {
            nikB = Long.parseLong(NikB.getText().toString().trim());
            namaB = NamaB.getText().toString().trim();
            jkB = JkB.getSelectedItem().toString().trim();
            editnopB = editNopB.getText().toString().trim();
            teLaB = TeLaB.getText().toString().trim();
            edttalaB = EdtTalaB.getText().toString().trim();
            alamatB = AlamatB.getText().toString().trim();

            if (nikB <= 0) {
                NikB.setError("NIK TIDAK BOLEH KOSONG");
            } else if (namaB.equals("")) {
                NamaB.setError("NAMA TIDAK BOLEH KOSONG");
            } else if (teLaB.equals("")) {
                TeLaB.setError("TEMPAT LAHIR TIDAK BOLEH KOSONG");
            } else if (alamatB.equals("")) {
                AlamatB.setError("STOK TIDAK BOLEH KOSONG");
            } else {
                ApiRequestPembeli requestDataPembeli = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                Call<ResponseDataPembeli> SimpanDataPembeli = requestDataPembeli.SendDataPembeli(nikB, namaB, jkB, alamatB, teLaB, edttalaB, editnopB);


                SimpanDataPembeli.enqueue(new Callback<ResponseDataPembeli>() {
                    @Override
                    public void onResponse(Call<ResponseDataPembeli> call, Response<ResponseDataPembeli> response) {

                        if( response.isSuccessful()) {
                            int kode = response.body().getKode();
                            String pesan = response.body().getPesan();
                            if (kode == 200){
                                startActivity(new Intent(FormDataPembeliActivity.this, HalamanUtamaPembeliActivity.class));
                                Toast.makeText(FormDataPembeliActivity.this, "pesan : "+pesan, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(FormDataPembeliActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }

                        PbSimpanDataB.setVisibility(View.GONE);
                        btnsimpanDataB.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {
                        Toast.makeText(FormDataPembeliActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                        PbSimpanDataB.setVisibility(View.GONE);
                        btnsimpanDataB.setVisibility(View.VISIBLE);
                        //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                    }

                });

            }

        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbSimpanDataB.setVisibility(View.GONE);
            btnsimpanDataB.setVisibility(View.VISIBLE);
        }catch (NullPointerException pointerException){
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            PbSimpanDataB.setVisibility(View.GONE);
            btnsimpanDataB.setVisibility(View.VISIBLE);
        }


    }


    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialogB = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                EdtTalaB.setText(dateFormatB.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialogB.show();
    }
}