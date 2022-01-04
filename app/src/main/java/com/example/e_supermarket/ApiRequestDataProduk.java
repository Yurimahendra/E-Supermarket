package com.example.e_supermarket;

import androidx.annotation.Nullable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.errorprone.annotations.FormatMethod;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiRequestDataProduk {
    @GET("api/dataproduk")
    Call<ResponseDataProduk> RetrieveDataProduk();

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/dataproduk")
    Call<ResponseDataProduk> SendData(
            @Part("nama_barang") RequestBody nama_barang,
            @Part("merk") RequestBody merk,
            @Part("harga") int harga,
            @Part("satuan") RequestBody satuan,
            @Part("stok") int stok,
            @Nullable @Part MultipartBody.Part gambar,
            @Nullable @Part("deskripsi") RequestBody deskripsi
    );


    @DELETE("api/dataproduk/{dataproduk}")
    Call<ResponseDataProduk> hapusData(
            @Path("dataproduk") int id
    );

}

