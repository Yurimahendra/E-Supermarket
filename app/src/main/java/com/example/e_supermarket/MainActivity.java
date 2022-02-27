package com.example.e_supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    int request_code = 1;
    Button BtnPenjual;
    Button BtnPembeli;
    Button BtnKurir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnPenjual = findViewById(R.id.btnpenjual);
        BtnPembeli = findViewById(R.id.btnpembeli);
        BtnKurir = findViewById(R.id.btnKurir);

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