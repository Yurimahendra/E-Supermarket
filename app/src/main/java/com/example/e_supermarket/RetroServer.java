package com.example.e_supermarket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseURL = "https://voluminous-contrast.000webhostapp.com";
    private static Retrofit retro;


    public static Retrofit konekRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();

        return retro;
    }

    public static ApiRequestDataProduk GetapiRequestDataProduk(){
        ApiRequestDataProduk requestDataProduk = konekRetrofit().create(ApiRequestDataProduk.class);
        return requestDataProduk;
    }
}
