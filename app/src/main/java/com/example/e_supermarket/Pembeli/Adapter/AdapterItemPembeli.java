package com.example.e_supermarket.Pembeli.Adapter;

import android.app.AlertDialog;
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
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Penjual.Activity.Form_Edit_Produk_Activity;
import com.example.e_supermarket.Penjual.Adapter.AdapterProdukPenjualHU;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterItemPembeli extends RecyclerView.Adapter<AdapterItemPembeli.MyViewHolder>{
    private HalamanUtamaPembeliActivity halamanUtamaPembeliActivity;
    private List<DataProduk> ProdukListPembeli;

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
        holder.IdPembeli.setText(String.valueOf(ProdukListPembeli.get(position).getNama_barang()));
        holder.Nama_BarangPembeli.setText(ProdukListPembeli.get(position).getNama_barang());
        holder.MerkPembeli.setText(ProdukListPembeli.get(position).getMerk());
        holder.HargaPembeli.setText(String.valueOf(ProdukListPembeli.get(position).getHarga()));
        holder.StokPembeli.setText(String.valueOf(ProdukListPembeli.get(position).getStok()));
        holder.SatuanPembeli.setText(ProdukListPembeli.get(position).getSatuan());
        Glide.with(holder.imageProdukPembeli.getContext())
                .load(RetroServer.imageURL + ProdukListPembeli.get(position).getGambar()).into(holder.imageProdukPembeli);
        holder.DeskripsiPembeli.setText(ProdukListPembeli.get(position).getDeskripsi());

        holder.KeranjangProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.BeliSekrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        TextView StokPembeli;
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
            StokPembeli = itemView.findViewById(R.id.tvStokPembeli);
            SatuanPembeli = itemView.findViewById(R.id.tvSatuanPembeli);
            imageProdukPembeli = itemView.findViewById(R.id.ImgItemPembeli);
            DeskripsiPembeli = itemView.findViewById(R.id.tvDeskripsiPembeli);

            KeranjangProduk = itemView.findViewById(R.id.ImgKeranjang);
            BeliSekrang = itemView.findViewById(R.id.BtnBeliSekarang);


        }

    }
}
