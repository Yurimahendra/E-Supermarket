package com.example.e_supermarket.Kurir.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_supermarket.Kurir.Interface.ApiRequestDataKurir;
import com.example.e_supermarket.Kurir.ResponseModel.ResponseDataKurir;
import com.example.e_supermarket.Pembeli.Activity.FormDataPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormDataKurirActivity extends AppCompatActivity {
    EditText NikK;
    EditText NamaK;
    Spinner JkK;
    EditText editNopK;
    EditText TeLaK;
    EditText AlamatK;
    EditText EdtTalaK;
    DatePickerDialog datePickerDialogK;
    SimpleDateFormat dateFormatK;

    Button btnsimpanDataK;
    ProgressBar PbSimpanDataK;

    long nikK;
    String nikKS;
    int lenNikK;
    String namaK;
    String jkK;
    String editnopK;
    String teLaK;
    String alamatK;
    String edttalaK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_kurir);
        NikK = findViewById(R.id.EdtNikK);
        NamaK = findViewById(R.id.EdtNamaK);
        JkK = findViewById(R.id.SpJkK);
        TeLaK = findViewById(R.id.EdtTeLaK);
        EdtTalaK = findViewById(R.id.EdtTalaK);
        AlamatK = findViewById(R.id.EdtAlamatK);
        editNopK = findViewById(R.id.EdtNopK);

        btnsimpanDataK = findViewById(R.id.btnSimpanDataK);
        PbSimpanDataK = findViewById(R.id.pbDataKurir);

        editNopK.setText(String.format(
                "%s", getIntent().getStringExtra("mobile")
        ));



        dateFormatK = new SimpleDateFormat("yyyy-MM-dd");

        EdtTalaK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


        btnsimpanDataK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdatakurir();
            }
        });
    }

    private void insertdatakurir() {
        PbSimpanDataK.setVisibility(View.VISIBLE);
        btnsimpanDataK.setVisibility(View.INVISIBLE);
        try {
            nikK = Long.parseLong(NikK.getText().toString().trim());
            namaK = NamaK.getText().toString().trim();
            jkK = JkK.getSelectedItem().toString().trim();
            editnopK = editNopK.getText().toString().trim();
            teLaK = TeLaK.getText().toString().trim();
            edttalaK = EdtTalaK.getText().toString().trim();
            alamatK = AlamatK.getText().toString().trim();

            nikKS = NikK.getText().toString().trim();
            lenNikK = nikKS.length();

            if (nikK <= 0){
                NikK.setError("NIK TIDAK BOLEH KOSONG");
                PbSimpanDataK.setVisibility(View.GONE);
                btnsimpanDataK.setVisibility(View.VISIBLE);
            } else if (lenNikK < 16) {
                NikK.setError("JUMLAH NIK TIDAK SESUAI");
                PbSimpanDataK.setVisibility(View.GONE);
                btnsimpanDataK.setVisibility(View.VISIBLE);
            } else if (namaK.equals("")) {
                NamaK.setError("NAMA TIDAK BOLEH KOSONG");
                PbSimpanDataK.setVisibility(View.GONE);
                btnsimpanDataK.setVisibility(View.VISIBLE);
            } else if (teLaK.equals("")) {
                TeLaK.setError("TEMPAT LAHIR TIDAK BOLEH KOSONG");
                PbSimpanDataK.setVisibility(View.GONE);
                btnsimpanDataK.setVisibility(View.VISIBLE);
            } else if (alamatK.equals("")) {
                AlamatK.setError("STOK TIDAK BOLEH KOSONG");
                PbSimpanDataK.setVisibility(View.GONE);
                btnsimpanDataK.setVisibility(View.VISIBLE);
            } else {
                ApiRequestDataKurir requestDataKurir = RetroServer.konekRetrofit().create(ApiRequestDataKurir.class);
                Call<ResponseDataKurir> SimpanDataKurir = requestDataKurir.SendDataKurir(nikK, namaK, jkK, alamatK, teLaK, edttalaK, editnopK);


                SimpanDataKurir.enqueue(new Callback<ResponseDataKurir>() {
                    @Override
                    public void onResponse(Call<ResponseDataKurir> call, Response<ResponseDataKurir> response) {

                        if( response.isSuccessful()) {
                            //int kode = response.body().getKode();
                            //String pesan = response.body().getPesan();
                            startActivity(new Intent(getApplicationContext(), HalamanUtamaKurirActivity.class));
                            Toast.makeText(FormDataKurirActivity.this, "berhasil disimpan", Toast.LENGTH_SHORT).show();
                            /*if (kode == 200){

                                //Toast.makeText(FormDataKurirActivity.this, "pesan : "+pesan, Toast.LENGTH_SHORT).show();
                            }*/
                        }else {
                            Toast.makeText(FormDataKurirActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }

                        PbSimpanDataK.setVisibility(View.GONE);
                        btnsimpanDataK.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<ResponseDataKurir> call, Throwable t) {
                        Toast.makeText(FormDataKurirActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                        PbSimpanDataK.setVisibility(View.GONE);
                        btnsimpanDataK.setVisibility(View.VISIBLE);
                        //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                    }

                });

            }

        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbSimpanDataK.setVisibility(View.GONE);
            btnsimpanDataK.setVisibility(View.VISIBLE);
        }catch (NullPointerException pointerException){
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            PbSimpanDataK.setVisibility(View.GONE);
            btnsimpanDataK.setVisibility(View.VISIBLE);
        }


    }


    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialogK = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                if (year > calendar.get(Calendar.YEAR)){
                    Toast.makeText(FormDataKurirActivity.this, "Tidak bisa tahun depan", Toast.LENGTH_SHORT).show();
                }else {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    EdtTalaK.setText(dateFormatK.format(newDate.getTime()));
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialogK.show();
    }
}