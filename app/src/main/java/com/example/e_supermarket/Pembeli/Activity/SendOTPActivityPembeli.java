package com.example.e_supermarket.Pembeli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_supermarket.Pembeli.Adapter.AdapterProfilePembeli;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Activity.LupaNomorPonselActivity;
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

public class SendOTPActivityPembeli extends AppCompatActivity {
    String noponPemb;
    int lenNoponPemb;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_o_t_p_pembeli);

        final EditText inputMobileB = findViewById(R.id.inputMobilePemb);
        final Button buttonB = findViewById(R.id.btnGetOtpPemb);
        final ProgressBar progressBarB = findViewById(R.id.progressBarB);
        TextView TvLupanopon = findViewById(R.id.tvLupaNoPon);

        sharedPreferences = getSharedPreferences("myapp-data", MODE_PRIVATE);

        try {
            if (sharedPreferences.getString("pref_nopon", null) != null){
                Intent intent = new Intent(SendOTPActivityPembeli.this, HalamanUtamaPembeliActivity.class);
                startActivity(intent);
            }
        }catch (NullPointerException e){

        }


        TvLupanopon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendOTPActivityPembeli.this, LupaNomorPonselPembeliActivity.class);
                startActivity(intent);
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noponPemb = inputMobileB.getText().toString().trim();
                lenNoponPemb = noponPemb.length();
                if (inputMobileB.getText().toString().trim().isEmpty()){
                    //startActivity(new Intent(SendOTPActivityPembeli.this, HalamanUtamaPembeliActivity.class));
                    Toast.makeText(SendOTPActivityPembeli.this, "Masukan Nomor Ponsel", Toast.LENGTH_SHORT).show();
                    return;
                }else if (lenNoponPemb < 12){
                    Toast.makeText(SendOTPActivityPembeli.this, "Jumlah Nomor Tidak Sesuai", Toast.LENGTH_SHORT).show();
                }else {
                    progressBarB.setVisibility(View.VISIBLE);
                    buttonB.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+62" + inputMobileB.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            SendOTPActivityPembeli.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBarB.setVisibility(View.GONE);
                                    buttonB.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBarB.setVisibility(View.GONE);
                                    buttonB.setVisibility(View.VISIBLE);
                                    Toast.makeText(SendOTPActivityPembeli.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationIdB, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBarB.setVisibility(View.GONE);
                                    buttonB.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), VerifyOTPActivityPembeli.class);
                                    intent.putExtra("mobile", inputMobileB.getText().toString());
                                    intent.putExtra("verificationId", verificationIdB);
                                    startActivity(intent);
                                }
                            }
                    );
                }



            }
        });
    }


}