package com.example.e_supermarket.Kurir.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_supermarket.Kurir.Adapter.AdapterProfileKurir;
import com.example.e_supermarket.Kurir.Interface.ApiRequestDataKurir;
import com.example.e_supermarket.Kurir.Model.DataKurir;
import com.example.e_supermarket.Kurir.ResponseModel.ResponseDataKurir;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.LupaNomorPonselPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.SendOTPActivityPembeli;
import com.example.e_supermarket.Pembeli.Activity.VerifyOTPActivityPembeli;
import com.example.e_supermarket.Penjual.Activity.SendOTPActivityPenjual;
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

public class SendOtpKurirActivity extends AppCompatActivity {
    private List<DataKurir> dataKurirList = new ArrayList<>();
    int index;
    String noponsel;
    String ETnopon;
    int compare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp_kurir);

        final EditText inputMobileK = findViewById(R.id.inputMobileKur);
        final Button buttonK = findViewById(R.id.btnGetOtpKur);
        final ProgressBar progressBarK = findViewById(R.id.progressBarK);
        TextView TvLupanoponK = findViewById(R.id.tvLupaNoPonKur);

        TvLupanoponK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendOtpKurirActivity.this, LupaNoponKurirActivity.class);
                startActivity(intent);
            }
        });

        buttonK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ETnopon = inputMobileK.getText().toString().trim();
                    compare = ETnopon.compareTo(noponsel);
                    if (inputMobileK.getText().toString().trim().isEmpty()){
                        startActivity(new Intent(SendOtpKurirActivity.this, HalamanUtamaKurirActivity.class));
                        //Toast.makeText(SendOtpKurirActivity.this, "Masukan Nomor Ponsel", Toast.LENGTH_SHORT).show();
                        return;
                    }else if (compare != 0){
                        Toast.makeText(SendOtpKurirActivity.this, "Kurir Hanya Boleh Satu User", Toast.LENGTH_SHORT).show();
                    }else {
                        progressBarK.setVisibility(View.VISIBLE);
                        buttonK.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+62" + inputMobileK.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                SendOtpKurirActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBarK.setVisibility(View.GONE);
                                        buttonK.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBarK.setVisibility(View.GONE);
                                        buttonK.setVisibility(View.VISIBLE);
                                        Toast.makeText(SendOtpKurirActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationIdB, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBarK.setVisibility(View.GONE);
                                        buttonK.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerifyOTPActivityPembeli.class);
                                        intent.putExtra("mobile", inputMobileK.getText().toString());
                                        intent.putExtra("verificationId", verificationIdB);
                                        startActivity(intent);
                                    }
                                }
                        );
                    }



                }catch (NullPointerException exception){
                    if (inputMobileK.getText().toString().trim().isEmpty()){
                        startActivity(new Intent(SendOtpKurirActivity.this, HalamanUtamaKurirActivity.class));
                        //Toast.makeText(SendOtpKurirActivity.this, "Masukan Nomor Ponsel", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressBarK.setVisibility(View.VISIBLE);
                    buttonK.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+62" + inputMobileK.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            SendOtpKurirActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBarK.setVisibility(View.GONE);
                                    buttonK.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBarK.setVisibility(View.GONE);
                                    buttonK.setVisibility(View.VISIBLE);
                                    Toast.makeText(SendOtpKurirActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationIdB, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBarK.setVisibility(View.GONE);
                                    buttonK.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(SendOtpKurirActivity.this, VerifyOtpKurirActivity.class);
                                    intent.putExtra("mobile", inputMobileK.getText().toString());
                                    intent.putExtra("verificationId", verificationIdB);
                                    startActivity(intent);
                                }
                            }
                    );
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
                            noponsel = dataKurirList.get(index).getNo_ponsel();
                           // ETnopon= noponsel;
                        }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                            //Toast.makeText(SendOTPActivityPenjual.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }

            @Override
            public void onFailure(Call<ResponseDataKurir> call, Throwable t) {

            }
        });
    }
}