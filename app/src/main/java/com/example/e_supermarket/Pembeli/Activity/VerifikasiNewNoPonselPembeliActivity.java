package com.example.e_supermarket.Pembeli.Activity;

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

import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.VerifikasiEmailPenjualActivity;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiNewNoPonselPembeliActivity extends AppCompatActivity {
    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    private String verificationId;

    int id;
    long nik ;
    String nama ;
    String jenis_kelamin ;
    String NewPonB ;
    String tempat_lahir;
    String tanggal_lahir ;
    String alamat ;

    private List<DataPembeli> dataPembeliList = new ArrayList<>();
    int indexLB;


    ProgressBar progressBarNewPonVB;
    Button buttonNewponVB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_new_no_ponsel_pembeli);


        TextView textNewPonVB = findViewById(R.id.txtNomorBaruVB);
        textNewPonVB.setText(getIntent().getStringExtra("mobile")
        );
        NewPonB = textNewPonVB.getText().toString().trim();
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);

        setupOTPInputs();

        progressBarNewPonVB = findViewById(R.id.progressBarNomorBaruVB);
        buttonNewponVB = findViewById(R.id.btnVerifyNomorBaruB);

        verificationId = getIntent().getStringExtra("verificationId");

        buttonNewponVB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCode1.getText().toString().trim().isEmpty()
                        || inputCode2.getText().toString().trim().isEmpty()
                        || inputCode3.getText().toString().trim().isEmpty()
                        || inputCode4.getText().toString().trim().isEmpty()
                        || inputCode5.getText().toString().trim().isEmpty()
                        || inputCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(VerifikasiNewNoPonselPembeliActivity.this, "harap masukkan kode yang valid", Toast.LENGTH_SHORT).show();
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
                    progressBarNewPonVB.setVisibility(View.VISIBLE);
                    buttonNewponVB.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBarNewPonVB.setVisibility(View.GONE);
                                    buttonNewponVB.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()) {
                                        ApiRequestPembeli requestDataProfilPmbl = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                                        Call<ResponseDataPembeli> UpdateProfilePmbl = requestDataProfilPmbl.UpdateProfilePembeli(
                                                id,
                                                "PUT",
                                                nik,
                                                RequestBody.create(MediaType.parse("text/plain"), nama),
                                                RequestBody.create(MediaType.parse("text/plain"), jenis_kelamin),
                                                RequestBody.create(MediaType.parse("text/plain"), alamat),
                                                RequestBody.create(MediaType.parse("text/plain"), tempat_lahir),
                                                RequestBody.create(MediaType.parse("text/plain"), tanggal_lahir),
                                                RequestBody.create(MediaType.parse("text/plain"), NewPonB),
                                                null

                                        );
                                        UpdateProfilePmbl.enqueue(new Callback<ResponseDataPembeli>() {
                                            @Override
                                            public void onResponse(Call<ResponseDataPembeli> call, Response<ResponseDataPembeli> response) {
                                                if( response.isSuccessful()) {

                                                    startActivity(new Intent(VerifikasiNewNoPonselPembeliActivity.this, HalamanUtamaPembeliActivity.class));
                                                    Toast.makeText(VerifikasiNewNoPonselPembeliActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();


                                                }else {
                                                    Toast.makeText(VerifikasiNewNoPonselPembeliActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                                }

                                                progressBarNewPonVB.setVisibility(View.GONE);
                                                buttonNewponVB.setVisibility(View.VISIBLE);
                                            }
                                            @Override
                                            public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {

                                                Toast.makeText(VerifikasiNewNoPonselPembeliActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                                                progressBarNewPonVB.setVisibility(View.GONE);
                                                buttonNewponVB.setVisibility(View.VISIBLE);
                                                //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                                            }

                                        });
                                        /*Intent intent = new Intent(getApplicationContext(), HalamanUtamaPenjualActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);*/
                                    } else {
                                        Toast.makeText(VerifikasiNewNoPonselPembeliActivity.this, "kode verifikasi yang dimasukkan tidak valid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        findViewById(R.id.txtKirimUlangNomorBaruVB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifikasiNewNoPonselPembeliActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifikasiNewNoPonselPembeliActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                verificationId = newVerificationId;
                                Toast.makeText(VerifikasiNewNoPonselPembeliActivity.this, "OTP Dikirim", Toast.LENGTH_SHORT).show();
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
        getProfilePembeli();
    }

    private void getProfilePembeli() {

        ApiRequestPembeli requestDataPembeli = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseDataPembeli> tampilData = requestDataPembeli.RetrieveDataPembeli();

        tampilData.enqueue(new Callback<ResponseDataPembeli>() {
            @Override
            public void onResponse(Call<ResponseDataPembeli> call, Response<ResponseDataPembeli> response) {
                if (response.isSuccessful()){
                    try {
                        dataPembeliList = response.body().getDataPembeli();
                        id = dataPembeliList.get(indexLB).getId();
                        nik = dataPembeliList.get(indexLB).getNik();
                        nama = dataPembeliList.get(indexLB).getNama();
                        jenis_kelamin = dataPembeliList.get(indexLB).getJenis_kelamin();
                        tempat_lahir = dataPembeliList.get(indexLB).getTempat_lahir();
                        tanggal_lahir = dataPembeliList.get(indexLB).getTanggal_lahir();
                        alamat = dataPembeliList.get(indexLB).getAlamat();

                        //ETnopon= noponsel;
                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        //Toast.makeText(SendOTPActivityPenjual.this, "", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {
                //Toast.makeText(ProfilePembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                //pbProfPembeli.setVisibility(View.GONE);
            }
        });


    }
}