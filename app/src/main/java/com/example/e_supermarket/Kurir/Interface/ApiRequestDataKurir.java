package com.example.e_supermarket.Kurir.Interface;

import com.example.e_supermarket.Kurir.ResponseModel.ResponseDataKurir;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
}
