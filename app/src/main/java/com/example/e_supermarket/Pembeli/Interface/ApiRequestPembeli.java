package com.example.e_supermarket.Pembeli.Interface;

import androidx.annotation.Nullable;
import androidx.versionedparcelable.ParcelField;

import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;

import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequestPembeli {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/datapembeli")
    Call<ResponseDataPembeli> SendDataPembeli(
            @Field("nik") long nik,
            @Field("nama") String nama,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("alamat") String alamat,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("no_ponsel") String no_ponsel
    );

    @GET("api/datapembeli")
    Call<ResponseDataPembeli> RetrieveDataPembeli();

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/datapembeli/{datapembeli}")
    Call<ResponseDataPembeli> UpdateProfilePembeli(
            @Path("dataproduk") int id,
            @Query("_method") String _method,
            @Part("nik") long nik,
            @Part("nama") RequestBody nama,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("alamat") RequestBody alamat,
            @Part("tempat_lahir") RequestBody tempat_lahir,
            @Part("tanggal_lahir") RequestBody tanggal_lahir,
            @Part("no_ponsel") RequestBody no_ponsel,
            @Nullable @Part MultipartBody.Part gambar
    );
}
