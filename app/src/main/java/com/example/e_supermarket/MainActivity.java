package com.example.e_supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void onClickEventPenjual(View view){
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
}