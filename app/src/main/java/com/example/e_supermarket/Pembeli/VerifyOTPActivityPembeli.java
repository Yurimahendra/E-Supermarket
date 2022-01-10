package com.example.e_supermarket.Pembeli;

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

import com.example.e_supermarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivityPembeli extends AppCompatActivity {

    private EditText inputCode1B, inputCode2B, inputCode3B, inputCode4B, inputCode5B, inputCode6B;
    private String verificationIdB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p_pembeli);

        TextView textMobile = findViewById(R.id.txtMobilePemb);
        textMobile.setText(String.format(
                "+62-%s", getIntent().getStringExtra("mobile")
        ));

        inputCode1B = findViewById(R.id.inputCode1p);
        inputCode2B = findViewById(R.id.inputCode2p);
        inputCode3B = findViewById(R.id.inputCode3p);
        inputCode4B = findViewById(R.id.inputCode4p);
        inputCode5B = findViewById(R.id.inputCode5p);
        inputCode6B = findViewById(R.id.inputCode6p);

        setupOTPInputs();

        final ProgressBar progressBarVB = findViewById(R.id.progressBarVB);
        final Button buttonVB = findViewById(R.id.btnVerifyPemb);

        verificationIdB = getIntent().getStringExtra("verificationId");

        buttonVB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCode1B.getText().toString().trim().isEmpty()
                        || inputCode2B.getText().toString().trim().isEmpty()
                        || inputCode3B.getText().toString().trim().isEmpty()
                        || inputCode4B.getText().toString().trim().isEmpty()
                        || inputCode5B.getText().toString().trim().isEmpty()
                        || inputCode6B.getText().toString().trim().isEmpty()){
                    Toast.makeText(VerifyOTPActivityPembeli.this, "harap masukkan kode yang valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                String codeB =
                        inputCode1B.getText().toString() +
                                inputCode2B.getText().toString() +
                                inputCode3B.getText().toString() +
                                inputCode4B.getText().toString() +
                                inputCode5B.getText().toString() +
                                inputCode6B.getText().toString();

                if (verificationIdB != null){
                    progressBarVB.setVisibility(View.VISIBLE);
                    buttonVB.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationIdB,
                            codeB
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBarVB.setVisibility(View.GONE);
                                    buttonVB.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(), FormDataPembeliActivity.class);
                                        intent.putExtra("mobile", textMobile.getText().toString());
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(VerifyOTPActivityPembeli.this, "kode verifikasi yang dimasukkan tidak valid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        findViewById(R.id.kirimUlangOTPB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOTPActivityPembeli.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyOTPActivityPembeli.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationIdB, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                verificationIdB = newVerificationIdB;
                                Toast.makeText(VerifyOTPActivityPembeli.this, "OTP Dikirim", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void setupOTPInputs(){
        inputCode1B.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode2B.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2B.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode3B.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3B.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode4B.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4B.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode5B.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode5B.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode6B.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}