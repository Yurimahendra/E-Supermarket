package com.example.e_supermarket.Kurir.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_supermarket.Kurir.Interface.ApiRequestDataKurir;
import com.example.e_supermarket.Kurir.Model.DataKurir;
import com.example.e_supermarket.Kurir.ResponseModel.ResponseDataKurir;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.VerifikasiNewNoPonselPembeliActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiNewNoponKurirActivity extends AppCompatActivity {
    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private String verificationId;

    private List<DataKurir> dataKurirList = new ArrayList<>();
    int indexLK;

    int id;
    long nik ;
    String nama ;
    String jenis_kelamin ;
    String NewPonK ;
    String tempat_lahir;
    String tanggal_lahir ;
    String alamat ;


    ProgressBar progressBarNewPonVK;
    Button buttonNewponVK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_new_nopon_kurir);

        TextView textNewPonVB = findViewById(R.id.txtNomorBaruVK);
        textNewPonVB.setText(getIntent().getStringExtra("mobile")
        );
        NewPonK = textNewPonVB.getText().toString().trim();
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);

        setupOTPInputs();

        progressBarNewPonVK = findViewById(R.id.progressBarNomorBaruVK);
        buttonNewponVK = findViewById(R.id.btnVerifyNomorBaruK);

        verificationId = getIntent().getStringExtra("verificationId");

        buttonNewponVK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCode1.getText().toString().trim().isEmpty()
                        || inputCode2.getText().toString().trim().isEmpty()
                        || inputCode3.getText().toString().trim().isEmpty()
                        || inputCode4.getText().toString().trim().isEmpty()
                        || inputCode5.getText().toString().trim().isEmpty()
                        || inputCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(VerifikasiNewNoponKurirActivity.this, "harap masukkan kode yang valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code =
                        inputCode1.getText().toString() +
                                inputCode2.getText().toString() +
                                inputCode3.getText().toString() +
                                inputCode4.getText().toString() +
                                inputCode5.getText().toString() +
                                inputCode6.getText().toString();

                if (verificationId != null) {
                    progressBarNewPonVK.setVisibility(View.VISIBLE);
                    buttonNewponVK.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBarNewPonVK.setVisibility(View.GONE);
                                    buttonNewponVK.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()) {
                                        ApiRequestDataKurir requestDataProfilKurir = RetroServer.konekRetrofit().create(ApiRequestDataKurir.class);
                                        Call<ResponseDataKurir> UpdateProfileKurir = requestDataProfilKurir.UpdateProfileKurir(
                                                id,
                                                "PUT",
                                                nik,
                                                RequestBody.create(MediaType.parse("text/plain"), nama),
                                                RequestBody.create(MediaType.parse("text/plain"), jenis_kelamin),
                                                RequestBody.create(MediaType.parse("text/plain"), alamat),
                                                RequestBody.create(MediaType.parse("text/plain"), tempat_lahir),
                                                RequestBody.create(MediaType.parse("text/plain"), tanggal_lahir),
                                                RequestBody.create(MediaType.parse("text/plain"), NewPonK),
                                                null

                                        );
                                        UpdateProfileKurir.enqueue(new Callback<ResponseDataKurir>() {
                                            @Override
                                            public void onResponse(Call<ResponseDataKurir> call, Response<ResponseDataKurir> response) {
                                                if( response.isSuccessful()) {
                                                    //int kode = response.body().getKode();
                                                    //String pesan = response.body().getPesan();
                                                    startActivity(new Intent(VerifikasiNewNoponKurirActivity.this, HalamanUtamaKurirActivity.class));
                                                    Toast.makeText(VerifikasiNewNoponKurirActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();

                                                }else {
                                                    Toast.makeText(VerifikasiNewNoponKurirActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                                }

                                                progressBarNewPonVK.setVisibility(View.GONE);
                                                buttonNewponVK.setVisibility(View.VISIBLE);
                                            }
                                            @Override
                                            public void onFailure(Call<ResponseDataKurir> call, Throwable t) {
                                                Toast.makeText(VerifikasiNewNoponKurirActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                                                progressBarNewPonVK.setVisibility(View.GONE);
                                                buttonNewponVK.setVisibility(View.VISIBLE);
                                                //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                                            }

                                        });
                                        /*Intent intent = new Intent(getApplicationContext(), HalamanUtamaPenjualActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);*/
                                    } else {
                                        Toast.makeText(VerifikasiNewNoponKurirActivity.this, "kode verifikasi yang dimasukkan tidak valid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        findViewById(R.id.txtKirimUlangNomorBaruVK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifikasiNewNoponKurirActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifikasiNewNoponKurirActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                verificationId = newVerificationId;
                                Toast.makeText(VerifikasiNewNoponKurirActivity.this, "OTP Dikirim", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void setupOTPInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputCode6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfileKurir();
    }

    private void getProfileKurir() {

        ApiRequestDataKurir requestDataKurir = RetroServer.konekRetrofit().create(ApiRequestDataKurir.class);
        Call<ResponseDataKurir> tampilData = requestDataKurir.RetrieveDataKurir();

        tampilData.enqueue(new Callback<ResponseDataKurir>() {
            @Override
            public void onResponse(Call<ResponseDataKurir> call, Response<ResponseDataKurir> response) {
                if (response.isSuccessful()){

                    try {
                        dataKurirList = response.body().getDataKurir();
                        id = dataKurirList.get(indexLK).getId();
                        nik = dataKurirList.get(indexLK).getNik();
                        nama = dataKurirList.get(indexLK).getNama();
                        jenis_kelamin = dataKurirList.get(indexLK).getJenis_kelamin();
                        tempat_lahir = dataKurirList.get(indexLK).getTempat_lahir();
                        tanggal_lahir = dataKurirList.get(indexLK).getTanggal_lahir();
                        alamat = dataKurirList.get(indexLK).getAlamat();
                        //ETnopon= noponsel;
                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        //Toast.makeText(SendOTPActivityPenjual.this, "", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<ResponseDataKurir> call, Throwable t) {

            }
        });
    }
}