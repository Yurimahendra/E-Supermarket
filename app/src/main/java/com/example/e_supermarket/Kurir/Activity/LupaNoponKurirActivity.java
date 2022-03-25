package com.example.e_supermarket.Kurir.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_supermarket.Kurir.Interface.ApiRequestDataKurir;
import com.example.e_supermarket.Kurir.Model.DataKurir;
import com.example.e_supermarket.Kurir.ResponseModel.ResponseDataKurir;
import com.example.e_supermarket.Pembeli.Activity.LupaNomorPonselPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.VerifikasiNewNoPonselPembeliActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
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

public class LupaNoponKurirActivity extends AppCompatActivity {

    private List<DataKurir> dataKurirList = new ArrayList<>();
    int indexLK;
    String noponselLK;
    String ETnoponLK;
    int compareLK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_nopon_kurir);

        EditText inputNomoLamaK = findViewById(R.id.inputNomorLamaK);
        EditText inputNomorBaruK = findViewById(R.id.inputNomorBaruK);
        Button buttonOtpNewPonK = findViewById(R.id.btnGetOtpENomorBaruK);
        ProgressBar progressBarNewPonK = findViewById(R.id.progressBarNomorBaruK);

        buttonOtpNewPonK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ETnoponLK = inputNomoLamaK.getText().toString().trim();
                    compareLK = ETnoponLK.compareTo(noponselLK);
                    if (inputNomoLamaK.getText().toString().trim().isEmpty() ){
                        Intent intent = new Intent(LupaNoponKurirActivity.this, HalamanUtamaKurirActivity.class);
                        startActivity(intent);
                        //Toast.makeText(SendOTPActivityPenjual.this, "Masukan Nomor Ponsel Lama", Toast.LENGTH_SHORT).show();
                        //return;
                    }else if (inputNomorBaruK.getText().toString().trim().isEmpty()){
                        Intent intent = new Intent(LupaNoponKurirActivity.this, HalamanUtamaKurirActivity.class);
                        startActivity(intent);
                        //Toast.makeText(SendOTPActivityPenjual.this, "Masukan Nomor Ponsel Baru", Toast.LENGTH_SHORT).show();
                    } else if (compareLK != 0){

                        Toast.makeText(LupaNoponKurirActivity.this, "Nomor Ponsel Lama Belum Terdaftar", Toast.LENGTH_SHORT).show();

                    }else {
                        progressBarNewPonK.setVisibility(View.VISIBLE);
                        buttonOtpNewPonK.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+62" + inputNomorBaruK.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                LupaNoponKurirActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBarNewPonK.setVisibility(View.GONE);
                                        buttonOtpNewPonK.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBarNewPonK.setVisibility(View.GONE);
                                        buttonOtpNewPonK.setVisibility(View.VISIBLE);
                                        Toast.makeText(LupaNoponKurirActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBarNewPonK.setVisibility(View.GONE);
                                        buttonOtpNewPonK.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerifikasiNewNoponKurirActivity.class);
                                        intent.putExtra("mobile", inputNomorBaruK.getText().toString());
                                        intent.putExtra("verificationId", verificationId);
                                        startActivity(intent);
                                    }
                                }
                        );

                    }
                }catch (NullPointerException exception){
                    if (inputNomoLamaK.getText().toString().trim().isEmpty()){
                        //Intent intent = new Intent(LupaNoponKurirActivity.this, HalamanUtamaKurirActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNoponKurirActivity.this, "Masukan Nomor Ponsel Lama" , Toast.LENGTH_SHORT).show();
                        //return;
                    }else if (inputNomorBaruK.getText().toString().trim().isEmpty()){
                        //Intent intent = new Intent(LupaNoponKurirActivity.this, HalamanUtamaKurirActivity.class);
                        //startActivity(intent);
                        Toast.makeText(LupaNoponKurirActivity.this, "Masukan Nomor Ponsel Baru", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LupaNoponKurirActivity.this, "Nomor Ponsel Lama Belum Terdaftar", Toast.LENGTH_SHORT).show();
                    }

                }
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
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    if (kode == 200){
                        try {
                            dataKurirList = response.body().getDataKurir();
                            noponselLK = dataKurirList.get(indexLK).getNo_ponsel();
                            //ETnopon= noponsel;
                        }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                            //Toast.makeText(SendOTPActivityPenjual.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }

            @Override
            public void onFailure(Call<ResponseDataKurir> call, Throwable t) {
                Toast.makeText(LupaNoponKurirActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}