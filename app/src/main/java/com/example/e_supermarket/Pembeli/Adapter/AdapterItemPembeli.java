package com.example.e_supermarket.Pembeli.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Activity.BeliProdukActivity;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataKeranjang;
import com.example.e_supermarket.Penjual.Activity.AddDataActivityPenjual;
import com.example.e_supermarket.Penjual.Activity.Form_Edit_Produk_Activity;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Adapter.AdapterProdukPenjualHU;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterItemPembeli extends RecyclerView.Adapter<AdapterItemPembeli.MyViewHolder> {
    private HalamanUtamaPembeliActivity halamanUtamaPembeliActivity;
    private List<DataProduk> ProdukListPembeli;

    //String satuan;
    public AdapterItemPembeli(HalamanUtamaPembeliActivity halamanUtamaPembeliActivity, List<DataProduk> produkListPembeli) {
        this.halamanUtamaPembeliActivity = halamanUtamaPembeliActivity;
        ProdukListPembeli = produkListPembeli;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(halamanUtamaPembeliActivity).inflate(R.layout.item_produk_pembeli, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //satuan = ProdukListPembeli.get(position).getSatuan();
        holder.IdPembeli.setText(String.valueOf(ProdukListPembeli.get(position).getId()));
        holder.Nama_BarangPembeli.setText(ProdukListPembeli.get(position).getNama_barang());
        holder.MerkPembeli.setText(ProdukListPembeli.get(position).getMerk());
        holder.HargaPembeli.setText(String.valueOf(ProdukListPembeli.get(position).getHarga()));
        holder.MinBelanja.setText(String.valueOf(ProdukListPembeli.get(position).getMin_belanja()));
        holder.SatuanPembeli.setText(ProdukListPembeli.get(position).getSatuan());
        Glide.with(holder.imageProdukPembeli.getContext())
                .load(RetroServer.imageURL + ProdukListPembeli.get(position).getGambar()).into(holder.imageProdukPembeli);
        holder.DeskripsiPembeli.setText(ProdukListPembeli.get(position).getDeskripsi());
        holder.KeranjangProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiRequestPembeli requestDataKeranjang = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                Call<ResponseDataKeranjang> SimpanDataKeranjang = requestDataKeranjang.SendDataKeranjang(
                        RequestBody.create(MediaType.parse("text/plain"), ProdukListPembeli.get(position).getNama_barang()),
                        RequestBody.create(MediaType.parse("text/plain"), ProdukListPembeli.get(position).getMerk()),
                        RequestBody.create(MediaType.parse("text/plain"), ProdukListPembeli.get(position).getHarga()),
                        RequestBody.create(MediaType.parse("text/plain"), ProdukListPembeli.get(position).getSatuan()),
                        ProdukListPembeli.get(position).getMin_belanja(),
                        RequestBody.create(MediaType.parse("text/plain"), ProdukListPembeli.get(position).getGambar()),
                        RequestBody.create(MediaType.parse("text/plain"), ProdukListPembeli.get(position).getDeskripsi())
                );


                SimpanDataKeranjang.enqueue(new Callback<ResponseDataKeranjang>() {
                    @Override
                    public void onResponse(Call<ResponseDataKeranjang> call, Response<ResponseDataKeranjang> response) {
                        if (response.isSuccessful()){
                            //int kode = response.body().getKode();
                            //String pesan = response.body().getPesan();
                            //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                            Toast.makeText(halamanUtamaPembeliActivity.getApplicationContext(), "berhasil menambahkan ke keranjang", Toast.LENGTH_SHORT).show();
                            /*if( kode == 200) {

                            }*/
                        }
                        else {
                            Toast.makeText(halamanUtamaPembeliActivity.getApplicationContext(), "Gagal  "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                    @Override
                    public void onFailure(Call<ResponseDataKeranjang> call, Throwable t) {
                        Toast.makeText(halamanUtamaPembeliActivity.getApplicationContext(), "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                    }

                });
            }
        });

        holder.BeliSekrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataProduk item = ProdukListPembeli.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                bundle.putString("nama_barang", item.getNama_barang());
                bundle.putString("merk", item.getMerk());
                bundle.putString("harga", item.getHarga());
                bundle.putInt("min_belanja", item.getMin_belanja());
                bundle.putString("ongkir", item.getOngkir());
                bundle.putString("satuan", item.getSatuan());
                bundle.putString("gambar", RetroServer.imageURL + item.getGambar());
                bundle.putString("deskripsi", item.getDeskripsi());
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(halamanUtamaPembeliActivity, BeliProdukActivity.class);
                intent.putExtras(bundle);
                halamanUtamaPembeliActivity.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return ProdukListPembeli.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView IdPembeli;
        TextView Nama_BarangPembeli;
        TextView MerkPembeli;
        TextView HargaPembeli;
        TextView MinBelanja;
        TextView SatuanPembeli;
        TextView DeskripsiPembeli;


        ImageView KeranjangProduk;
        Button BeliSekrang;
        ImageView imageProdukPembeli;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            IdPembeli = itemView.findViewById(R.id.tvIdBarangPembeli);
            Nama_BarangPembeli = itemView.findViewById(R.id.tvNamaBarangPembeli);
            MerkPembeli = itemView.findViewById(R.id.tvMerkPembeli);
            HargaPembeli = itemView.findViewById(R.id.tvHargaPembeli);
            MinBelanja = itemView.findViewById(R.id.tvStokPembeli);
            SatuanPembeli = itemView.findViewById(R.id.tvSatuanPembeli);
            imageProdukPembeli = itemView.findViewById(R.id.ImgItemPembeli);
            DeskripsiPembeli = itemView.findViewById(R.id.tvDeskripsiPembeli);


            KeranjangProduk = itemView.findViewById(R.id.ImgKeranjang);
            BeliSekrang = itemView.findViewById(R.id.BtnBeliSekarang);


        }

    }
}
