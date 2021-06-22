package com.example.e_supermarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterProdukPenjual extends RecyclerView.Adapter<AdapterProdukPenjual.MyViewHolder> {

    private HomeFragmentPenjual homeFragmentPenjual;
    private List<DataProduk> ProdukList;

    public AdapterProdukPenjual(HomeFragmentPenjual homeFragmentPenjual, List<DataProduk> dataProdukList) {
        this.homeFragmentPenjual = homeFragmentPenjual;
        this.ProdukList = dataProdukList;
    }

    @NonNull
    @Override
        public AdapterProdukPenjual.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(homeFragmentPenjual.getContext()).inflate(R.layout.item_produk_penjual,parent ,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProdukPenjual.MyViewHolder holder, int position) {
        holder.Nama_Barang.setText(ProdukList.get(position).getNama_barang());
        holder.Merk.setText(ProdukList.get(position).getMerk());
        holder.Harga.setText(ProdukList.get(position).getHarga());
        holder.Stok.setText(ProdukList.get(position).getStok());
        holder.Satuan.setText(ProdukList.get(position).getSatuan());

       // holder.Harga.setText(String.valueOf(dataProduk.harga));
    }

    @Override
    public int getItemCount() {
        return ProdukList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Nama_Barang ;
        TextView Merk ;
        TextView Harga ;
        TextView Stok ;
        TextView Satuan ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Nama_Barang = itemView.findViewById(R.id.tvNamaBarang);
            Merk = itemView.findViewById(R.id.tvMerk);
            Harga = itemView.findViewById(R.id.tvHarga);
            Stok = itemView.findViewById(R.id.tvStok);
            Satuan = itemView.findViewById(R.id.tvSatuan);
        }
    }
}
