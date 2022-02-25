package com.example.e_supermarket.Penjual.Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseURL = "http://192.168.100.6/esupermarket-api23/public/"; //192.168.45.192
    private static Retrofit retro;
    public static final String imageURL = "http://192.168.100.6/esupermarket-api23/public/storage/gambar/";


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

    /*public static ApiRequestDataProduk GetapiRequestDataProduk(){
        ApiRequestDataProduk requestDataProduk = konekRetrofit().create(ApiRequestDataProduk.class);
        return requestDataProduk;
    }*/
}
