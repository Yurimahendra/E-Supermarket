package com.example.e_supermarket.Penjual.Activity;

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

import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiEmailPenjualActivity extends AppCompatActivity {
    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private String verificationId;
    int id;
    long nik;
    String nama;
    String jenis_kelamin;
    String NewPonS;
    String tempat_lahir;
    String tanggal_lahir;
    String alamat;
    String latitude;
    String longitude;
    String nama_toko;
    String nama_bank;
    long no_rekening;


    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    int indexLS;
    String noponselLS;
    String EnoponLS;
    int compareLS;

    ProgressBar progressBarNewPonVS;
    Button buttonNewponVS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_email_penjual);

        TextView textNewPonVS = findViewById(R.id.txtNomorBaruVS);
        textNewPonVS.setText(getIntent().getStringExtra("mobile")
        );
        NewPonS = textNewPonVS.getText().toString().trim();
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);

        setupOTPInputs();

        progressBarNewPonVS = findViewById(R.id.progressBarNomorBaruVS);
        buttonNewponVS = findViewById(R.id.btnVerifyNomorBaruS);

        verificationId = getIntent().getStringExtra("verificationId");

        buttonNewponVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCode1.getText().toString().trim().isEmpty()
                        || inputCode2.getText().toString().trim().isEmpty()
                        || inputCode3.getText().toString().trim().isEmpty()
                        || inputCode4.getText().toString().trim().isEmpty()
                        || inputCode5.getText().toString().trim().isEmpty()
                        || inputCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(VerifikasiEmailPenjualActivity.this, "harap masukkan kode yang valid", Toast.LENGTH_SHORT).show();
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
                    progressBarNewPonVS.setVisibility(View.VISIBLE);
                    buttonNewponVS.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBarNewPonVS.setVisibility(View.GONE);
                                    buttonNewponVS.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()) {
                                        ApiRequestDataProduk requestDataProfilPnjl = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                                        Call<ResponseDataPenjual> UpdateProfilePnjl = requestDataProfilPnjl.UpdateProfilePenjual(
                                                id,
                                                "PUT",
                                                nik,
                                                RequestBody.create(MediaType.parse("text/plain"), nama),
                                                RequestBody.create(MediaType.parse("text/plain"), jenis_kelamin),
                                                RequestBody.create(MediaType.parse("text/plain"), alamat),
                                                RequestBody.create(MediaType.parse("text/plain"), latitude),
                                                RequestBody.create(MediaType.parse("text/plain"), longitude),
                                                RequestBody.create(MediaType.parse("text/plain"), tempat_lahir),
                                                RequestBody.create(MediaType.parse("text/plain"), tanggal_lahir),
                                                RequestBody.create(MediaType.parse("text/plain"), NewPonS),
                                                RequestBody.create(MediaType.parse("text/plain"), nama_toko),
                                                RequestBody.create(MediaType.parse("text/plain"), nama_bank),
                                                no_rekening,
                                                null

                                        );
                                        UpdateProfilePnjl.enqueue(new Callback<ResponseDataPenjual>() {
                                            @Override
                                            public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                                                if (response.isSuccessful()) {
                                                    startActivity(new Intent(VerifikasiEmailPenjualActivity.this, HalamanUtamaPenjualActivity.class));
                                                    Toast.makeText(VerifikasiEmailPenjualActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(VerifikasiEmailPenjualActivity.this, "Data Gagal Tersimpan " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                                }

                                                progressBarNewPonVS.setVisibility(View.GONE);
                                                buttonNewponVS.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                                                Toast.makeText(VerifikasiEmailPenjualActivity.this, "Gagal Menghubungi Server " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                progressBarNewPonVS.setVisibility(View.GONE);
                                                buttonNewponVS.setVisibility(View.VISIBLE);
                                                //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                                            }

                                        });
                                        /*Intent intent = new Intent(getApplicationContext(), HalamanUtamaPenjualActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);*/
                                    } else {
                                        Toast.makeText(VerifikasiEmailPenjualActivity.this, "kode verifikasi yang dimasukkan tidak valid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        findViewById(R.id.txtKirimUlangNomorBaruVS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifikasiEmailPenjualActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifikasiEmailPenjualActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                verificationId = newVerificationId;
                                Toast.makeText(VerifikasiEmailPenjualActivity.this, "OTP Dikirim", Toast.LENGTH_SHORT).show();
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
        getProfilePenjual();
    }

    private void getProfilePenjual() {

        ApiRequestDataProduk requestDataPenjual = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataPenjual> tampilData = requestDataPenjual.RetrieveDataPenjual();

        tampilData.enqueue(new Callback<ResponseDataPenjual>() {
            @Override
            public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                if (response.isSuccessful()) {
                    try {
                        dataPenjualList = response.body().getDataPenjual();
                        id = dataPenjualList.get(indexLS).getId();
                        nik = dataPenjualList.get(indexLS).getNik();
                        nama = dataPenjualList.get(indexLS).getNama();
                        jenis_kelamin = dataPenjualList.get(indexLS).getJenis_kelamin();
                        tempat_lahir = dataPenjualList.get(indexLS).getTempat_lahir();
                        tanggal_lahir = dataPenjualList.get(indexLS).getTanggal_lahir();
                        alamat = dataPenjualList.get(indexLS).getAlamat();
                        nama_toko = dataPenjualList.get(indexLS).getNama_toko();
                        nama_toko = dataPenjualList.get(indexLS).getNama_bank();
                        no_rekening = dataPenjualList.get(indexLS).getNo_rekening();
                        //noponselLS = dataPenjualList.get(indexLS).getNo_ponsel();
                        //EnoponLS = noponselLS;
                    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        //Toast.makeText(SendOTPActivityPenjual.this, "", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                Toast.makeText(VerifikasiEmailPenjualActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();

            }
        });


    }

}