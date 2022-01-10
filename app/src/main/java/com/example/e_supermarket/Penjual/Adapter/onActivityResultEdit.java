package com.example.e_supermarket.Penjual.Adapter;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface onActivityResultEdit {
    void onActivityResultEdit(int requestCode, int resultCode, @Nullable Intent data);

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
