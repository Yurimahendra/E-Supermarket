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

import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.LupaNomorPonselPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.SendOTPActivityPembeli;
import com.example.e_supermarket.Pembeli.Activity.VerifyOTPActivityPembeli;
import com.example.e_supermarket.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOtpKurirActivity extends AppCompatActivity {

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
                if (inputMobileK.getText().toString().trim().isEmpty()){
                    startActivity(new Intent(SendOtpKurirActivity.this, FormDataKurirActivity.class));
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
                                Intent intent = new Intent(getApplicationContext(), VerifyOTPActivityPembeli.class);
                                intent.putExtra("mobile", inputMobileK.getText().toString());
                                intent.putExtra("verificationId", verificationIdB);
                                startActivity(intent);
                            }
                        }
                );

            }
        });
    }
}