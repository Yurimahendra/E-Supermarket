package com.example.e_supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTPActivityPenjual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_o_t_p_penjual);

        final EditText inputMobileS = findViewById(R.id.inputMobileS);
        final Button buttonS = findViewById(R.id.btnGetOtpS);

        final ProgressBar progressBarS = findViewById(R.id.progressBarS);

        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputMobileS.getText().toString().trim().isEmpty()){
                    Intent intent = new Intent(SendOTPActivityPenjual.this, HalamanUtamaPenjualActivity.class);
                    startActivity(intent);
                   // Toast.makeText(SendOTPActivityPenjual.this, "Masukan Nomor Ponsel", Toast.LENGTH_SHORT).show();
                    return;
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

        });
    }
}