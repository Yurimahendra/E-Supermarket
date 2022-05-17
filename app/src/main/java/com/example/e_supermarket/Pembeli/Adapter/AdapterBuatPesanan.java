package com.example.e_supermarket.Pembeli.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Activity.BeliProdukActivity;
import com.example.e_supermarket.Pembeli.Activity.DetailPesananActivity;
import com.example.e_supermarket.Pembeli.Activity.OrderanPembeliActivity;
import com.example.e_supermarket.Pembeli.Model.BuatPesanan;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.List;

public class AdapterBuatPesanan extends RecyclerView.Adapter<AdapterBuatPesanan.MyViewHolder> {
    private OrderanPembeliActivity orderanPembeliActivity;
    private List<BuatPesanan> buatPesananList;

    public AdapterBuatPesanan(OrderanPembeliActivity orderanPembeliActivity, List<BuatPesanan> buatPesananList) {
        this.orderanPembeliActivity = orderanPembeliActivity;
        this.buatPesananList = buatPesananList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(orderanPembeliActivity).inflate(R.layout.item_produk_pesanan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.IdPesanan.setText(String.valueOf(buatPesananList.get(position).getId()));
        holder.Nama_BarangPesanan.setText(buatPesananList.get(position).getNama_barang());
        holder.MerkPesanan.setText(buatPesananList.get(position).getMerk_barang());
        holder.HargaPesanan.setText(buatPesananList.get(position).getHarga_barang());
        //holder.StokPembeli.setText(String.valueOf(ProdukListPembeli.get(position).getStok()));
        holder.SatuanPesanan.setText(buatPesananList.get(position).getSatuan());
        holder.JumlahPesanan.setText(""+buatPesananList.get(position).getJumlah_pesanan());
        Glide.with(holder.imageProdukPesanan.getContext())
                .load(buatPesananList.get(position).getGambar()).into(holder.imageProdukPesanan);
        //holder.DeskripsiPembeli.setText(ProdukListPembeli.get(position).getDeskripsi());
        holder.Ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuatPesanan item = buatPesananList.get(position);
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
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(orderanPembeliActivity, DetailPesananActivity.class);
                intent.putExtras(bundle);
                orderanPembeliActivity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return buatPesananList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView IdPesanan;
        TextView Nama_BarangPesanan;
        TextView MerkPesanan;
        TextView HargaPesanan;
       // TextView StokPesanan;
        TextView SatuanPesanan;
        TextView JumlahPesanan;


       // ImageView KeranjangProduk;
        Button Ubah;
        ImageView imageProdukPesanan;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            IdPesanan = itemView.findViewById(R.id.tvIdBarangPesanan);
            Nama_BarangPesanan = itemView.findViewById(R.id.tvNamaBarangPesanan);
            MerkPesanan = itemView.findViewById(R.id.tvMerkPesanan);
            HargaPesanan = itemView.findViewById(R.id.tvHargaPesanan);
            JumlahPesanan = itemView.findViewById(R.id.tvJumlahPesanan);
            //StokPembeli = itemView.findViewById(R.id.tvStokPembeli);
            SatuanPesanan = itemView.findViewById(R.id.tvSatuanPesanan);
            imageProdukPesanan = itemView.findViewById(R.id.ImgItemPesanan);
            //DeskripsiPembeli = itemView.findViewById(R.id.tvDeskripsiPembeli);


           // KeranjangProduk = itemView.findViewById(R.id.ImgKeranjang);
            Ubah = itemView.findViewById(R.id.btnUbah);


        }

    }
}
