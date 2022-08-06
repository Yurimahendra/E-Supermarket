package com.example.e_supermarket;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
    private Context context;
    private SharedPreferences sharedPreferences;

    public AppConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("file kunci", context.MODE_PRIVATE);
    }

    public boolean UserLogin(){
        return sharedPreferences.getBoolean("userlogin", false);
    }

    public void UpdateUserLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("userlogin", status);
        editor.apply();
    }

    public void SavedUserName (String nameuser){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nama_user", nameuser);
        editor.apply();
    }

    public String getNameofUser(){
        return sharedPreferences.getString("nama_user", "Unknown");
    }
}
