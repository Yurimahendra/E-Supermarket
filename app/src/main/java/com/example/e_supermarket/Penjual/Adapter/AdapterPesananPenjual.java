package com.example.e_supermarket.Penjual.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Activity.BayarPesananPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.DetailPesananActivity;
import com.example.e_supermarket.Pembeli.Activity.OrderanPembeliActivity;
import com.example.e_supermarket.Pembeli.Adapter.AdapterBuatPesanan;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.BuatPesanan;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Penjual.Activity.DetailPesananPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanNotifPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPesananPenjual extends RecyclerView.Adapter<AdapterPesananPenjual.MyViewHolder> {
    private HalamanNotifPenjualActivity halamanNotifPenjualActivity;
    private List<BuatPesanan> buatPesananListPenjual;

    public AdapterPesananPenjual(HalamanNotifPenjualActivity halamanNotifPenjualActivity, List<BuatPesanan> buatPesananListPenjual) {
        this.halamanNotifPenjualActivity = halamanNotifPenjualActivity;
        this.buatPesananListPenjual = buatPesananListPenjual;
    }

    @NonNull
    @Override
    public AdapterPesananPenjual.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(halamanNotifPenjualActivity).inflate(R.layout.item_pesanan_penjual, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPesananPenjual.MyViewHolder holder, int position) {
        holder.IdPesananPenjual.setText(String.valueOf(buatPesananListPenjual.get(position).getId()));
        holder.Nama_BarangPesananPenjual.setText(buatPesananListPenjual.get(position).getNama_barang());
        holder.MerkPesananPenjual.setText(buatPesananListPenjual.get(position).getMerk_barang());
        holder.HargaPesananPenjual.setText(buatPesananListPenjual.get(position).getHarga_barang());
        holder.StatusTerimaPesanan.setText(buatPesananListPenjual.get(position).getStatus_pesanan());
        holder.SatuanPesananPenjual.setText(buatPesananListPenjual.get(position).getSatuan());
        holder.JumlahPesananPenjual.setText("" + buatPesananListPenjual.get(position).getJumlah_pesanan());
        Glide.with(holder.imageProdukPesananPenjual.getContext())
                .load(buatPesananListPenjual.get(position).getGambar()).into(holder.imageProdukPesananPenjual);
        //holder.DeskripsiPembeli.setText(ProdukListPembeli.get(position).getDeskripsi());

        //String terima_pesanan = buatPesananListPenjual.get(position).getStatus_pesanan();
        if (holder.StatusTerimaPesanan.getText().toString().equals("terima")) {
            holder.UbahPenjual.setVisibility(View.VISIBLE);
            holder.TerimaPenjual.setVisibility(View.GONE);
            holder.TolakPenjual.setVisibility(View.GONE);
        }else if (holder.StatusTerimaPesanan.getText().toString().equals("tolak")) {
            holder.TerimaPenjual.setVisibility(View.GONE);
            holder.TolakPenjual.setVisibility(View.GONE);
            holder.statusTolakPesanan.setVisibility(View.VISIBLE);

        }

        holder.TerimaPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.pbBtnTerima.setVisibility(View.VISIBLE);
                holder.TerimaPenjual.setVisibility(View.INVISIBLE);
                try {
                    int id = buatPesananListPenjual.get(position).getId();
                    String id_pesanan = buatPesananListPenjual.get(position).getId_pesanan();
                    String nama = buatPesananListPenjual.get(position).getNama();
                    String no_hp = buatPesananListPenjual.get(position).getNo_hp();
                    String alamat = buatPesananListPenjual.get(position).getAlamat();
                    String nama_barang = buatPesananListPenjual.get(position).getNama_barang();
                    String merk_barang = buatPesananListPenjual.get(position).getMerk_barang();
                    String harga_barang = buatPesananListPenjual.get(position).getHarga_barang();
                    int jumlah_pesanan = buatPesananListPenjual.get(position).getJumlah_pesanan();
                    String satuan = buatPesananListPenjual.get(position).getSatuan();
                    String gambar = buatPesananListPenjual.get(position).getGambar();
                    String tanggal_pengiriman = buatPesananListPenjual.get(position).getTanggal_pengiriman();
                    String ongkir = buatPesananListPenjual.get(position).getOngkir();
                    String total_harga = buatPesananListPenjual.get(position).getTotal_harga();
                    String metode_pembayaran = buatPesananListPenjual.get(position).getMetode_pembayaran();
                    String status = buatPesananListPenjual.get(position).getStatus();
                    String status_pesanan = buatPesananListPenjual.get(position).getStatus_pesanan();
                    String bukti_transfer = buatPesananListPenjual.get(position).getBukti_transfer();
                    ApiRequestPembeli requestDataDetailOrder = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                    Call<ResponseBuatPesanan> UpdateDetailOrder = requestDataDetailOrder.UpdateDetailPesanan(
                            id,
                            "PUT",
                            RequestBody.create(MediaType.parse("text/plain"), id_pesanan),
                            RequestBody.create(MediaType.parse("text/plain"), nama),
                            RequestBody.create(MediaType.parse("text/plain"), no_hp),
                            RequestBody.create(MediaType.parse("text/plain"), alamat),
                            RequestBody.create(MediaType.parse("text/plain"), nama_barang),
                            RequestBody.create(MediaType.parse("text/plain"), merk_barang),
                            RequestBody.create(MediaType.parse("text/plain"), harga_barang),
                            jumlah_pesanan,
                            RequestBody.create(MediaType.parse("text/plain"), satuan),
                            RequestBody.create(MediaType.parse("text/plain"), gambar),
                            RequestBody.create(MediaType.parse("text/plain"), tanggal_pengiriman),
                            RequestBody.create(MediaType.parse("text/plain"), ongkir),
                            RequestBody.create(MediaType.parse("text/plain"), total_harga),
                            RequestBody.create(MediaType.parse("text/plain"), metode_pembayaran),
                            RequestBody.create(MediaType.parse("text/plain"), status),
                            RequestBody.create(MediaType.parse("text/plain"), "terima"),
                            null

                    );
                    UpdateDetailOrder.enqueue(new Callback<ResponseBuatPesanan>() {
                        @Override
                        public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(halamanNotifPenjualActivity, HalamanUtamaPenjualActivity.class);
                                halamanNotifPenjualActivity.startActivity(intent);
                                Toast.makeText(halamanNotifPenjualActivity.getApplicationContext(), "pesanan diterima", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(halamanNotifPenjualActivity.getApplicationContext(), "Data Gagal Tersimpan " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                            holder.pbBtnTerima.setVisibility(View.GONE);
                            holder.TerimaPenjual.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {

                            Toast.makeText(halamanNotifPenjualActivity.getApplicationContext(), "Gagal Menghubungi Server " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            holder.pbBtnTerima.setVisibility(View.GONE);
                            holder.TerimaPenjual.setVisibility(View.VISIBLE);
                            //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                        }

                    });

                } catch (NullPointerException pointerException) {
                    Toast.makeText(halamanNotifPenjualActivity.getApplicationContext(), "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    holder.pbBtnTerima.setVisibility(View.GONE);
                    holder.TerimaPenjual.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.TolakPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.pbBtnTolak.setVisibility(View.VISIBLE);
                holder.TolakPenjual.setVisibility(View.INVISIBLE);
                try {
                    int id = buatPesananListPenjual.get(position).getId();
                    String id_pesanan = buatPesananListPenjual.get(position).getId_pesanan();
                    String nama = buatPesananListPenjual.get(position).getNama();
                    String no_hp = buatPesananListPenjual.get(position).getNo_hp();
                    String alamat = buatPesananListPenjual.get(position).getAlamat();
                    String nama_barang = buatPesananListPenjual.get(position).getNama_barang();
                    String merk_barang = buatPesananListPenjual.get(position).getMerk_barang();
                    String harga_barang = buatPesananListPenjual.get(position).getHarga_barang();
                    int jumlah_pesanan = buatPesananListPenjual.get(position).getJumlah_pesanan();
                    String satuan = buatPesananListPenjual.get(position).getSatuan();
                    String gambar = buatPesananListPenjual.get(position).getGambar();
                    String tanggal_pengiriman = buatPesananListPenjual.get(position).getTanggal_pengiriman();
                    String ongkir = buatPesananListPenjual.get(position).getOngkir();
                    String total_harga = buatPesananListPenjual.get(position).getTotal_harga();
                    String metode_pembayaran = buatPesananListPenjual.get(position).getMetode_pembayaran();
                    String status = buatPesananListPenjual.get(position).getStatus();
                    String status_pesanan = buatPesananListPenjual.get(position).getStatus_pesanan();
                    String bukti_transfer = buatPesananListPenjual.get(position).getBukti_transfer();
                    ApiRequestPembeli requestDataDetailOrder = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                    Call<ResponseBuatPesanan> UpdateDetailOrder = requestDataDetailOrder.UpdateDetailPesanan(
                            id,
                            "PUT",
                            RequestBody.create(MediaType.parse("text/plain"), id_pesanan),
                            RequestBody.create(MediaType.parse("text/plain"), nama),
                            RequestBody.create(MediaType.parse("text/plain"), no_hp),
                            RequestBody.create(MediaType.parse("text/plain"), alamat),
                            RequestBody.create(MediaType.parse("text/plain"), nama_barang),
                            RequestBody.create(MediaType.parse("text/plain"), merk_barang),
                            RequestBody.create(MediaType.parse("text/plain"), harga_barang),
                            jumlah_pesanan,
                            RequestBody.create(MediaType.parse("text/plain"), satuan),
                            RequestBody.create(MediaType.parse("text/plain"), gambar),
                            RequestBody.create(MediaType.parse("text/plain"), tanggal_pengiriman),
                            RequestBody.create(MediaType.parse("text/plain"), ongkir),
                            RequestBody.create(MediaType.parse("text/plain"), total_harga),
                            RequestBody.create(MediaType.parse("text/plain"), metode_pembayaran),
                            RequestBody.create(MediaType.parse("text/plain"), status),
                            RequestBody.create(MediaType.parse("text/plain"), "tolak"),
                            null

                    );
                    UpdateDetailOrder.enqueue(new Callback<ResponseBuatPesanan>() {
                        @Override
                        public void onResponse(Call<ResponseBuatPesanan> call, Response<ResponseBuatPesanan> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(halamanNotifPenjualActivity, HalamanUtamaPenjualActivity.class);
                                halamanNotifPenjualActivity.startActivity(intent);
                                Toast.makeText(halamanNotifPenjualActivity.getApplicationContext(), "pesanan ditolak", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(halamanNotifPenjualActivity.getApplicationContext(), "Data Gagal Tersimpan " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                            holder.pbBtnTolak.setVisibility(View.VISIBLE);
                            holder.TolakPenjual.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<ResponseBuatPesanan> call, Throwable t) {

                            Toast.makeText(halamanNotifPenjualActivity.getApplicationContext(), "Gagal Menghubungi Server " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            holder.pbBtnTolak.setVisibility(View.VISIBLE);
                            holder.TolakPenjual.setVisibility(View.INVISIBLE);
                            //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                        }

                    });

                } catch (NullPointerException pointerException) {
                    Toast.makeText(halamanNotifPenjualActivity.getApplicationContext(), "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    holder.pbBtnTolak.setVisibility(View.VISIBLE);
                    holder.TolakPenjual.setVisibility(View.INVISIBLE);
                }
            }
        });


        holder.UbahPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuatPesanan item = buatPesananListPenjual.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                bundle.putString("id_pesanan", item.getId_pesanan());
                bundle.putString("nama", item.getNama());
                bundle.putString("no_hp", item.getNo_hp());
                bundle.putString("alamat", item.getAlamat());
                bundle.putString("nama_barang", item.getNama_barang());
                bundle.putString("merk", item.getMerk_barang());
                bundle.putString("harga", item.getHarga_barang());
                bundle.putInt("jumlah", item.getJumlah_pesanan());
                bundle.putString("satuan", item.getSatuan());
                bundle.putString("gambar", item.getGambar());
                bundle.putString("tanggal", item.getTanggal_pengiriman());
                bundle.putString("ongkir", item.getOngkir());
                bundle.putString("total", item.getTotal_harga());
                bundle.putString("metode", item.getMetode_pembayaran());
                bundle.putString("status", item.getStatus());
                bundle.putString("status_pesanan", item.getStatus_pesanan());
                bundle.putString("bukti_transfer", item.getBukti_transfer());
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(halamanNotifPenjualActivity, DetailPesananPenjualActivity.class);
                intent.putExtras(bundle);
                halamanNotifPenjualActivity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return buatPesananListPenjual.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView IdPesananPenjual;
        TextView Nama_BarangPesananPenjual;
        TextView MerkPesananPenjual;
        TextView HargaPesananPenjual;
        TextView StatusTerimaPesanan;
        TextView SatuanPesananPenjual;
        TextView JumlahPesananPenjual;


        // ImageView KeranjangProduk;
        Button UbahPenjual;
        Button TerimaPenjual;
        ProgressBar pbBtnTerima;
        ProgressBar pbBtnTolak;
        Button TolakPenjual;
        ImageView imageProdukPesananPenjual;

        TextView statusTolakPesanan;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            IdPesananPenjual = itemView.findViewById(R.id.tvIdBarangPesananPenjual);
            Nama_BarangPesananPenjual = itemView.findViewById(R.id.tvNamaBarangPesananPenjual);
            MerkPesananPenjual = itemView.findViewById(R.id.tvMerkPesananPenjual);
            HargaPesananPenjual = itemView.findViewById(R.id.tvHargaPesananPenjual);
            JumlahPesananPenjual = itemView.findViewById(R.id.tvJumlahPesananPenjual);
            StatusTerimaPesanan = itemView.findViewById(R.id.tvStatusterimaPesananPenjual);
            SatuanPesananPenjual = itemView.findViewById(R.id.tvSatuanPesananPenjual);
            imageProdukPesananPenjual = itemView.findViewById(R.id.ImgItemPesananPenjual);
            //DeskripsiPembeli = itemView.findViewById(R.id.tvDeskripsiPembeli);


            // KeranjangProduk = itemView.findViewById(R.id.ImgKeranjang);
            UbahPenjual = itemView.findViewById(R.id.btnUbahPenjual);
            TerimaPenjual = itemView.findViewById(R.id.BtnTerimaPesanan);
            pbBtnTerima = itemView.findViewById(R.id.PbBtnTerima);
            TolakPenjual = itemView.findViewById(R.id.btnTolakPesanan);
            pbBtnTolak = itemView.findViewById(R.id.PbBtnTolak);

            statusTolakPesanan = itemView.findViewById(R.id.tvTolakPesananPenjual);


        }

    }
}
