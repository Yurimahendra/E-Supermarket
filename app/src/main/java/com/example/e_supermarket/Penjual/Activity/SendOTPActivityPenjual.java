package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_supermarket.Penjual.Adapter.AdapterProfilePenjual;
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

public class SendOTPActivityPenjual extends AppCompatActivity {

    private DataPenjual dataPenjual = new DataPenjual();
    private List<DataPenjual> dataPenjualList = new ArrayList<>();

    int index;
    String noponsel;
    String Enopon;
    int compare;

    int lenNopon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_o_t_p_penjual);

        EditText inputMobileS = findViewById(R.id.inputMobileS);
        Button buttonS = findViewById(R.id.btnGetOtpS);
        TextView TvemailS = findViewById(R.id.txtEmail);
        ProgressBar progressBarS = findViewById(R.id.progressBarS);



        TvemailS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendOTPActivityPenjual.this, LupaNomorPonselActivity.class);
                startActivity(intent);
            }
        });

        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Enopon = inputMobileS.getText().toString().trim();
                    compare = Enopon.compareTo(noponsel);
                    lenNopon = Enopon.length();
                    if (inputMobileS.getText().toString().trim().isEmpty()){
                        Intent intent = new Intent(SendOTPActivityPenjual.this, HalamanUtamaPenjualActivity.class);
                        startActivity(intent);
                        //Toast.makeText(SendOTPActivityPenjual.this, "Masukan Nomor Ponsel", Toast.LENGTH_SHORT).show();
                        //return;
                    }else if (lenNopon < 12){
                        Toast.makeText(SendOTPActivityPenjual.this, "Jumlah Nomor Tidak Sesuai", Toast.LENGTH_SHORT).show();
                    }
                    else if (compare != 0){
                        Toast.makeText(SendOTPActivityPenjual.this, "Penjual Hanya Boleh Satu User", Toast.LENGTH_SHORT).show();
                    }else {
                        progressBarS.setVisibility(View.VISIBLE);
                        buttonS.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+62" + inputMobileS.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                SendOTPActivityPenjual.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBarS.setVisibility(View.GONE);
                                        buttonS.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBarS.setVisibility(View.GONE);
                                        buttonS.setVisibility(View.VISIBLE);
                                        Toast.makeText(SendOTPActivityPenjual.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBarS.setVisibility(View.GONE);
                                        buttonS.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), VerifyOTPActivityPenjual.class);
                                        intent.putExtra("mobile", inputMobileS.getText().toString());
                                        intent.putExtra("verificationId", verificationId);
                                        startActivity(intent);
                                    }
                                }
                        );

                    }
                }catch (NullPointerException exception){
                    if (inputMobileS.getText().toString().trim().isEmpty()){
                        Intent intent = new Intent(SendOTPActivityPenjual.this, HalamanUtamaPenjualActivity.class);
                        startActivity(intent);
                        //Toast.makeText(SendOTPActivityPenjual.this, "Masukan Nomor Ponsel", Toast.LENGTH_SHORT).show();
                        //return;
                    }
                    progressBarS.setVisibility(View.VISIBLE);
                    buttonS.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+62" + inputMobileS.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            SendOTPActivityPenjual.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBarS.setVisibility(View.GONE);
                                    buttonS.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBarS.setVisibility(View.GONE);
                                    buttonS.setVisibility(View.VISIBLE);
                                    Toast.makeText(SendOTPActivityPenjual.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBarS.setVisibility(View.GONE);
                                    buttonS.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), VerifyOTPActivityPenjual.class);
                                    intent.putExtra("mobile", inputMobileS.getText().toString());
                                    intent.putExtra("verificationId", verificationId);
                                    startActivity(intent);
                                }
                            }
                    );
                }

                // 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 token github


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
                        noponsel = dataPenjualList.get(index).getNo_ponsel();
                        //Enopon = noponsel;
                        //Log.i("no", ""+noponsel);
                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        //Toast.makeText(SendOTPActivityPenjual.this, "", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                Toast.makeText(SendOTPActivityPenjual.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();

            }
        });


    }
}