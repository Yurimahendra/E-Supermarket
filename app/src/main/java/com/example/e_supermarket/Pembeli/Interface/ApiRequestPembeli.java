package com.example.e_supermarket.Pembeli.Interface;

import androidx.annotation.Nullable;
import androidx.versionedparcelable.ParcelField;

import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataKeranjang;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;

import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
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
            @Path("datapembeli") int id,
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

    //buat pesanan
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/dataorderan")
    Call<ResponseBuatPesanan> SendBuatPesanan(
            @Field("id_pesanan") String id_pesanan,
            @Field("nama") String nama,
            @Field("no_hp") String no_hp,
            @Field("alamat") String alamat,
            @Field("nama_barang") String nama_barang,
            @Field("merk_barang") String merk_barang,
            @Field("harga_barang") String harga_barang,
            @Field("jumlah_pesanan") int jumlah_pesanan,
            @Field("satuan") String satuan,
            @Field("gambar") String gambar,
            @Field("tanggal_pengiriman") String tanggal_pengiriman,
            @Nullable @Field("ongkir") String ongkir,
            @Field("total_harga") String total_harga,
            @Field("metode_pembayaran") String metode_pembayaran,
            @Nullable @Field("status") String status,
            @Nullable @Field("status_pesanan") String status_pesanan,
            @Nullable @Field("bukti_transfer") String bukti_transfer
    );

    //orderan
    @GET("api/dataorderan")
    Call<ResponseBuatPesanan> RetrieveDataOrderan();

    //updateDetail
    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/dataorderan/{dataorderan}")
    Call<ResponseBuatPesanan> UpdateDetailPesanan(
            @Path("dataorderan") int id,
            @Query("_method") String _method,
            @Part("id_pesanan") RequestBody id_pesanan,
            @Part("nama") RequestBody nama,
            @Part("no_hp") RequestBody no_hp,
            @Part("alamat") RequestBody alamat,
            @Part("nama_barang") RequestBody nama_barang,
            @Part("merk_barang") RequestBody merk_barang,
            @Part("harga_barang") RequestBody harga_barang,
            @Part("jumlah_pesanan") int jumlah_pesanan,
            @Part("satuan") RequestBody satuan,
            @Part("gambar") RequestBody gambar,
            @Part("tanggal_pengiriman") RequestBody tanggal_pengiriman,
            @Part("ongkir") RequestBody ongkir,
            @Part("total_harga") RequestBody total_harga,
            @Part("metode_pembayaran") RequestBody metode_pembayaran,
            @Part("status") RequestBody status,
            @Part("status_pesanan") RequestBody status_pesanan,
            @Nullable @Part MultipartBody.Part bukti_transfer
    );

    @DELETE("api/dataorderan/{dataorderan}")
    Call<ResponseBuatPesanan> BatalDataOrderan(
            @Path("dataorderan") int id
    );

    //data_keranjang
    @GET("api/datakeranjang")
    Call<ResponseDataKeranjang> RetrieveDataKeranjang();

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/datakeranjang")
    Call<ResponseDataKeranjang> SendDataKeranjang(
            @Part("nama_barang") RequestBody nama_barang,
            @Part("merk") RequestBody merk,
            @Part("harga") RequestBody harga,
            @Part("satuan") RequestBody satuan,
            @Part("stok") int stok,
            @Part("gambar") RequestBody gambar,
            //@Nullable @Part MultipartBody.Part gambar,
            @Nullable @Part("deskripsi") RequestBody deskripsi
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/datakeranjang/{datakeranjang}")
    Call<ResponseDataKeranjang> UpdateDataKeranjang(
            @Path("datakeranjang") int id,
            @Query("_method") String _method,
            @Part("nama_barang") RequestBody nama_barang,
            @Part("merk") RequestBody merk,
            @Part("harga") RequestBody harga,
            @Part("satuan") RequestBody satuan,
            @Part("stok") int stok,
            @Nullable @Part MultipartBody.Part gambar,
            @Nullable @Part("deskripsi") RequestBody deskripsi
    );



    @DELETE("api/datakeranjang/{datakeranjang}")
    Call<ResponseDataKeranjang> hapusDataKeranjang(
            @Path("datakeranjang") int id
    );
}
