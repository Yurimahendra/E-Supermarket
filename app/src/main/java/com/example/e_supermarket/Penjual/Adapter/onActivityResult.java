package com.example.e_supermarket.Penjual.Adapter;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface onActivityResult {
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

    void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults);
}
