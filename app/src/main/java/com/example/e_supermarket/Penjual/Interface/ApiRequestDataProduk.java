package com.example.e_supermarket.Penjual.Interface;

import androidx.annotation.Nullable;

import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequestDataProduk {
    @GET("api/dataproduk")
    Call<ResponseDataProduk> RetrieveDataProduk();

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/dataproduk")
    Call<ResponseDataProduk> SendData(
            @Part("nama_barang") RequestBody nama_barang,
            @Part("merk") RequestBody merk,
            @Part("harga") RequestBody harga,
            @Part("satuan") RequestBody satuan,
            @Part("min_belanja") int min_belanja,
            @Part("ongkir") RequestBody ongkir,
            @Nullable @Part MultipartBody.Part gambar,
            @Nullable @Part("deskripsi") RequestBody deskripsi
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/dataproduk/{dataproduk}")
    Call<ResponseDataProduk> UpdateData(
            @Path("dataproduk") int id,
            @Query("_method") String _method,
            @Part("nama_barang") RequestBody nama_barang,
            @Part("merk") RequestBody merk,
            @Part("harga") RequestBody harga,
            @Part("satuan") RequestBody satuan,
            @Part("min_belanja") int min_belanja,
            @Part("ongkir") RequestBody ongkir,
            @Nullable @Part MultipartBody.Part gambar,
            @Nullable @Part("deskripsi") RequestBody deskripsi
    );



    @DELETE("api/dataproduk/{dataproduk}")
    Call<ResponseDataProduk> hapusData(
            @Path("dataproduk") int id
    );

//datapenjual
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/datapenjual")
    Call<ResponseDataPenjual> SendDataPenjual(
            @Field("nik") long nik,
            @Field("nama") String nama,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("alamat") String alamat,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("no_ponsel") String no_ponsel,
            @Field("nama_toko") String nama_toko,
            @Field("nama_bank") String nama_bank,
            @Field("no_rekening") long no_rekening

    );

    @GET("api/datapenjual")
    Call<ResponseDataPenjual> RetrieveDataPenjual();

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/datapenjual/{datapenjual}")
    Call<ResponseDataPenjual> UpdateProfilePenjual(
            @Path("datapenjual") int id,
            @Query("_method") String _method,
            @Part("nik") long nik,
            @Part("nama") RequestBody nama,
            @Part("jenis_kelamin") RequestBody jenis_kelamin,
            @Part("alamat") RequestBody alamat,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("tempat_lahir") RequestBody tempat_lahir,
            @Part("tanggal_lahir") RequestBody tanggal_lahir,
            @Part("no_ponsel") RequestBody no_ponsel,
            @Part("nama_toko") RequestBody nama_toko,
            @Part("nama_bank") RequestBody nama_bank,
            @Part("no_rekening") long no_rekening,
            @Nullable @Part MultipartBody.Part gambar
    );



}

