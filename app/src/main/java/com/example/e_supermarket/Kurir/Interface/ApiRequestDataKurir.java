package com.example.e_supermarket.Kurir.Interface;

import androidx.annotation.Nullable;

import com.example.e_supermarket.Kurir.ResponseModel.ResponseDataKurir;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;

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

public interface ApiRequestDataKurir {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/datakurir")
    Call<ResponseDataKurir> SendDataKurir(
            @Field("nik") long nikK,
            @Field("nama") String namaK,
            @Field("jenis_kelamin") String jenis_kelaminK,
            @Field("alamat") String alamatK,
            @Field("tempat_lahir") String tempat_lahirK,
            @Field("tanggal_lahir") String tanggal_lahirK,
            @Field("no_ponsel") String no_ponselK
    );

    @GET("api/datakurir")
    Call<ResponseDataKurir> RetrieveDataKurir();

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/datakurir/{datakurir}")
    Call<ResponseDataKurir> UpdateProfileKurir(
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
