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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LupaNomorPonselActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_nomor_ponsel);

        EditText inputNomoLamaS = findViewById(R.id.inputNomorLamaS);
        EditText inputNomorBaruS = findViewById(R.id.inputNomorBaruS);
        Button buttonEmaiS = findViewById(R.id.btnGetOtpENomorBaruS);
        ProgressBar progressBarEmailS = findViewById(R.id.progressBarNomorBaruS);

        buttonEmaiS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputNomoLamaS.getText().toString().trim().isEmpty() || inputNomorBaruS.getText().toString().trim().isEmpty()){
                    Intent intent = new Intent(LupaNomorPonselActivity.this, HalamanUtamaPenjualActivity.class);
                    startActivity(intent);
                    // Toast.makeText(SendOTPActivityPenjual.this, "Nomor Ponsel Lama Atau Baru Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }else if (inputNomorBaruS.getText().toString().trim().isEmpty()){
                    // Toast.makeText(SendOTPActivityPenjual.this, "Masukan Nomor Ponsel Baru", Toast.LENGTH_SHORT).show();
                    //buttonEmaiS.setVisibility(View.INVISIBLE);
                    //progressBarEmailS.setVisibility(View.VISIBLE);

                    /**PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+62" + inputNomorBaruS.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            LupaNomorPonselActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBarEmailS.setVisibility(View.GONE);
                                    buttonEmaiS.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBarEmailS.setVisibility(View.GONE);
                                    buttonEmaiS.setVisibility(View.VISIBLE);
                                    Toast.makeText(LupaNomorPonselActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBarEmailS.setVisibility(View.GONE);
                                    buttonEmaiS.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), VerifikasiEmailPenjualActivity.class);
                                    intent.putExtra("baru", inputNomorBaruS.getText().toString());
                                    intent.putExtra("verificationId", verificationId);
                                    startActivity(intent);
                                }
                            }
                    );**/
                }


            }

        });
    }
}