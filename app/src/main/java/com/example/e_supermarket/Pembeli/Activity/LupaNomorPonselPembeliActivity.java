package com.example.e_supermarket.Pembeli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.LupaNomorPonselActivity;
import com.example.e_supermarket.Penjual.Activity.VerifikasiEmailPenjualActivity;
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

public class LupaNomorPonselPembeliActivity extends AppCompatActivity {
    private List<DataPembeli> dataPembeliList = new ArrayList<>();
    int indexLB;
    String noponselLB;
    String ETnoponLB;
    int compareLB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_nomor_ponsel_pembeli);

        EditText inputNomoLamaB = findViewById(R.id.inputNomorLamaB);
        EditText inputNomorBaruB = findViewById(R.id.inputNomorBaruB);
        Button buttonOtpNewPonB = findViewById(R.id.btnGetOtpENomorBaruB);
        ProgressBar progressBarNewPonB = findViewById(R.id.progressBarNomorBaruB);

        buttonOtpNewPonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ETnoponLB = inputNomoLamaB.getText().toString().trim();
                    compareLB = ETnoponLB.compareTo(noponselLB);
                    if (inputNomoLamaB.getText().toString().trim().isEmpty() ){
                        //Intent intent = new Intent(LupaNomorPonselPembeliActivity.this, HalamanUtamaPembeliActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNomorPonselPembeliActivity.this, "Masukan Nomor Ponsel Lama", Toast.LENGTH_SHORT).show();
                        //return;
                    }else if (inputNomorBaruB.getText().toString().trim().isEmpty()){
                        //Intent intent = new Intent(LupaNomorPonselPembeliActivity.this, HalamanUtamaPembeliActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNomorPonselPembeliActivity.this, "Masukan Nomor Ponsel Baru", Toast.LENGTH_SHORT).show();
                    } else if (compareLB != 0){

                        Toast.makeText(LupaNomorPonselPembeliActivity.this, "Nomor Ponsel Lama Belum Terdaftar", Toast.LENGTH_SHORT).show();

                    }else {
                        progressBarNewPonB.setVisibility(View.VISIBLE);
                        buttonOtpNewPonB.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+62" + inputNomorBaruB.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                LupaNomorPonselPembeliActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBarNewPonB.setVisibility(View.GONE);
                                        buttonOtpNewPonB.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBarNewPonB.setVisibility(View.GONE);
                                        buttonOtpNewPonB.setVisibility(View.VISIBLE);
                                        Toast.makeText(LupaNomorPonselPembeliActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBarNewPonB.setVisibility(View.GONE);
                                        buttonOtpNewPonB.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerifikasiNewNoPonselPembeliActivity.class);
                                        intent.putExtra("mobile", inputNomorBaruB.getText().toString());
                                        intent.putExtra("verificationId", verificationId);
                                        startActivity(intent);
                                    }
                                }
                        );

                    }
                }catch (NullPointerException exception){
                    if (inputNomoLamaB.getText().toString().trim().isEmpty()){
                        //Intent intent = new Intent(LupaNomorPonselPembeliActivity.this, HalamanUtamaPembeliActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNomorPonselPembeliActivity.this, "Masukan Nomor Ponsel Lama" , Toast.LENGTH_SHORT).show();
                        //return;
                    }else if (inputNomorBaruB.getText().toString().trim().isEmpty()){
                        //Intent intent = new Intent(LupaNomorPonselPembeliActivity.this, HalamanUtamaPembeliActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNomorPonselPembeliActivity.this, "Masukan Nomor Ponsel Baru", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LupaNomorPonselPembeliActivity.this, "Nomor Ponsel Lama Belum Terdaftar", Toast.LENGTH_SHORT).show();
                    }

                }
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
                        noponselLB = dataPembeliList.get(indexLB).getNo_ponsel();
                        //ETnopon= noponsel;
                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        //Toast.makeText(SendOTPActivityPenjual.this, "", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {
                Toast.makeText(LupaNomorPonselPembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                //pbProfPembeli.setVisibility(View.GONE);
            }
        });


    }
}