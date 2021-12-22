package com.example.e_supermarket;

import androidx.annotation.Nullable;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequestDataProduk {
    @GET("/api/dataproduk")
    Call<ResponseDataProduk> RetrieveDataProduk();

    /**@Headers({"Accept: application/json"})
    @POST("/api/dataproduk")
    Call<ResponseDataProduk> SendData(@Body DataProduk produk);*/

    @Headers({"Content-Type: application/json"})
    //@Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("/api/dataproduk")
    Call<ResponseDataProduk> SendData(
            @Field("nama_barang") String nama_barang,
            @Field("merk") String merk,
            @Field("harga") int harga,
            @Field("satuan") String satuan,
            @Field("stok") int stok,
            @Nullable @Field("gambar") String gambar,
            @Nullable @Field("deskripsi") String deskripsi
    );
}

