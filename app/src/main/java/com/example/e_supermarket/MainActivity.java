package com.example.e_supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_supermarket.Kurir.Activity.FormDataKurirActivity;
import com.example.e_supermarket.Kurir.Activity.HalamanUtamaKurirActivity;
import com.example.e_supermarket.Kurir.Activity.SendOtpKurirActivity;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.SendOTPActivityPembeli;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.SendOTPActivityPenjual;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    int request_code = 1;
    Button BtnPenjual;
    Button BtnPembeli;
    Button BtnKurir;
    AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnPenjual = findViewById(R.id.btnpenjual);
        BtnPembeli = findViewById(R.id.btnpembeli);
        BtnKurir = findViewById(R.id.btnKurir);

        //appConfig = new AppConfig(this);

        BtnPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendOTPActivityPenjual.class));
            }
        });

        BtnPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendOTPActivityPembeli.class));
            }
        });

        BtnKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendOtpKurirActivity.class));
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Respon", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("Respon fcm : ", token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

    }
   /* public void onClickEventPenjual(View view){
        Intent i = new Intent(".SendOTPActivityPenjual");
        //  Intent ii = new Intent(".AnOtherActivity");
        startActivity(i);
        // startActivity(ii);

    }

    public void onClickEventPembeli(View view){
        Intent i = new Intent(".SendOTPActivityPembeli");
        //startActivityForResult(i, request_code);
        startActivity(i);
    }

    public void onClickEventKurir(View view){
        Intent i = new Intent(".SendOtpKurirActivity");
        //startActivityForResult(i, request_code);
        startActivity(i);
    }*/
}