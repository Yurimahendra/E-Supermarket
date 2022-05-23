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
import com.example.e_supermarket.Pembeli.Activity.FormDataPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.VerifyOTPActivityPembeli;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpKurirActivity extends AppCompatActivity {
    private EditText inputCode1K, inputCode2K, inputCode3K, inputCode4K, inputCode5K, inputCode6K;
    private String verificationIdK;

    private List<DataKurir> dataKurirList = new ArrayList<>();
    int index;
    String noponsel;
    String ETnopon;
    int compare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_kurir);

        TextView textMobile = findViewById(R.id.txtMobileVK);
        textMobile.setText(getIntent().getStringExtra("mobile")
        );

        inputCode1K = findViewById(R.id.inputCode1K);
        inputCode2K = findViewById(R.id.inputCode2K);
        inputCode3K = findViewById(R.id.inputCode3K);
        inputCode4K = findViewById(R.id.inputCode4K);
        inputCode5K = findViewById(R.id.inputCode5K);
        inputCode6K = findViewById(R.id.inputCode6K);

        setupOTPInputs();

        final ProgressBar progressBarVK = findViewById(R.id.progressBarVK);
        final Button buttonVK = findViewById(R.id.btnVerifyK);

        verificationIdK = getIntent().getStringExtra("verificationId");

        buttonVK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCode1K.getText().toString().trim().isEmpty()
                        || inputCode2K.getText().toString().trim().isEmpty()
                        || inputCode3K.getText().toString().trim().isEmpty()
                        || inputCode4K.getText().toString().trim().isEmpty()
                        || inputCode5K.getText().toString().trim().isEmpty()
                        || inputCode6K.getText().toString().trim().isEmpty()){
                    Toast.makeText(VerifyOtpKurirActivity.this, "harap masukkan kode yang valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                String codeK =
                        inputCode1K.getText().toString() +
                                inputCode2K.getText().toString() +
                                inputCode3K.getText().toString() +
                                inputCode4K.getText().toString() +
                                inputCode5K.getText().toString() +
                                inputCode6K.getText().toString();

                if (verificationIdK != null){
                    progressBarVK.setVisibility(View.VISIBLE);
                    buttonVK.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationIdK,
                            codeK
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    try {
                                        progressBarVK.setVisibility(View.GONE);
                                        buttonVK.setVisibility(View.VISIBLE);
                                        ETnopon = textMobile.getText().toString().trim();
                                        compare = ETnopon.compareTo(noponsel);
                                        if (task.isSuccessful()){
                                            if (compare == 0){
                                                Intent intent = new Intent(VerifyOtpKurirActivity.this, HalamanUtamaKurirActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }else {
                                                Intent intent = new Intent(VerifyOtpKurirActivity.this, FormDataKurirActivity.class);
                                                intent.putExtra("mobile", textMobile.getText().toString());
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }

                                        } else {
                                            Toast.makeText(VerifyOtpKurirActivity.this, "kode verifikasi yang dimasukkan tidak valid", Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (NullPointerException exception){
                                        progressBarVK.setVisibility(View.GONE);
                                        buttonVK.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(VerifyOtpKurirActivity.this, FormDataKurirActivity.class);
                                        intent.putExtra("mobile", textMobile.getText().toString());
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }


                                }
                            });
                }
            }
        });

        findViewById(R.id.txtKirimUlangVK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOtpKurirActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyOtpKurirActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationIdK, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                verificationIdK = newVerificationIdK;
                                Toast.makeText(VerifyOtpKurirActivity.this, "OTP Dikirim", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void setupOTPInputs(){
        inputCode1K.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode2K.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2K.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode3K.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3K.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode4K.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4K.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode5K.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode5K.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode6K.requestFocus();
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
                        noponsel = dataKurirList.get(index).getNo_ponsel();
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