package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaNomorPonselActivity extends AppCompatActivity {
    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    int indexLS;
    String noponselLS;
    String EnoponLS;
    int compareLS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_nomor_ponsel);

        EditText inputNomoLamaS = findViewById(R.id.inputNomorLamaS);
        EditText inputNomorBaruS = findViewById(R.id.inputNomorBaruS);
        Button buttonOtpNewPonS = findViewById(R.id.btnGetOtpENomorBaruS);
        ProgressBar progressBarNewPonS = findViewById(R.id.progressBarNomorBaruS);

        buttonOtpNewPonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    EnoponLS = inputNomoLamaS.getText().toString().trim();
                    compareLS = EnoponLS.compareTo(noponselLS);
                    if (inputNomoLamaS.getText().toString().trim().isEmpty() ){
                        //Intent intent = new Intent(LupaNomorPonselActivity.this, HalamanUtamaPenjualActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNomorPonselActivity.this, "Masukan Nomor Ponsel Lama", Toast.LENGTH_SHORT).show();
                        //return;
                    }else if (inputNomorBaruS.getText().toString().trim().isEmpty()){
                        //Intent intent = new Intent(LupaNomorPonselActivity.this, HalamanUtamaPenjualActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNomorPonselActivity.this, "Masukan Nomor Ponsel Baru", Toast.LENGTH_SHORT).show();
                    } else if (compareLS != 0){

                        Toast.makeText(LupaNomorPonselActivity.this, "Nomor Ponsel Lama Belum Terdaftar", Toast.LENGTH_SHORT).show();

                    }else {
                        progressBarNewPonS.setVisibility(View.VISIBLE);
                        buttonOtpNewPonS.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+62" + inputNomorBaruS.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                LupaNomorPonselActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBarNewPonS.setVisibility(View.GONE);
                                        buttonOtpNewPonS.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBarNewPonS.setVisibility(View.GONE);
                                        buttonOtpNewPonS.setVisibility(View.VISIBLE);
                                        Toast.makeText(LupaNomorPonselActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBarNewPonS.setVisibility(View.GONE);
                                        buttonOtpNewPonS.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerifikasiEmailPenjualActivity.class);
                                        intent.putExtra("mobile", inputNomorBaruS.getText().toString());
                                        intent.putExtra("verificationId", verificationId);
                                        startActivity(intent);
                                    }
                                }
                        );

                    }
                }catch (NullPointerException exception){
                    if (inputNomoLamaS.getText().toString().trim().isEmpty()){
                        //Intent intent = new Intent(LupaNomorPonselActivity.this, HalamanUtamaPenjualActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNomorPonselActivity.this, "Masukan Nomor Ponsel Lama" , Toast.LENGTH_SHORT).show();
                        //return;
                    }else if (inputNomorBaruS.getText().toString().trim().isEmpty()){
                        //Intent intent = new Intent(LupaNomorPonselActivity.this, HalamanUtamaPenjualActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNomorPonselActivity.this, "Masukan Nomor Ponsel Baru", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LupaNomorPonselActivity.this, "Nomor Ponsel Lama Belum Terdaftar", Toast.LENGTH_SHORT).show();
                    }

                }
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
                if (response.isSuccessful()){

                    try {
                        dataPenjualList = response.body().getDataPenjual();
                        noponselLS = dataPenjualList.get(indexLS).getNo_ponsel();
                        //EnoponLS = noponselLS;
                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        //Toast.makeText(SendOTPActivityPenjual.this, "", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                Toast.makeText(LupaNomorPonselActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();

            }
        });


    }
}