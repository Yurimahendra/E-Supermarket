package com.example.e_supermarket.Pembeli.Interface;

import androidx.annotation.Nullable;
import androidx.versionedparcelable.ParcelField;

import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;

import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequestPembeli {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/datapembeli")
    Call<ResponseDataPembeli> SendDataPembeli(
            @Field("nik") long nik,
            @Field("nama") String nama,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("alamat") String satuan,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("no_ponsel") String no_ponsel
    );
}
