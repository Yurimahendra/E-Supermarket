package com.example.e_supermarket.Penjual.Adapter;

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
import com.example.e_supermarket.Pembeli.Activity.DetailPesananActivity;
import com.example.e_supermarket.Pembeli.Activity.OrderanPembeliActivity;
import com.example.e_supermarket.Pembeli.Adapter.AdapterBuatPesanan;
import com.example.e_supermarket.Pembeli.Model.BuatPesanan;
import com.example.e_supermarket.Penjual.Activity.DetailPesananPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanNotifPenjualActivity;
import com.example.e_supermarket.R;

import java.util.List;

public class AdapterPesananPenjual extends RecyclerView.Adapter<AdapterPesananPenjual.MyViewHolder>{
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
        holder.IdPesananPenjual.setText(String.valueOf(buatPesananListPenjual.get(position).getId_pesanan()));
        holder.Nama_BarangPesananPenjual.setText(buatPesananListPenjual.get(position).getNama_barang());
        holder.MerkPesananPenjual.setText(buatPesananListPenjual.get(position).getMerk_barang());
        holder.HargaPesananPenjual.setText(buatPesananListPenjual.get(position).getHarga_barang());
        //holder.StokPembeli.setText(String.valueOf(ProdukListPembeli.get(position).getStok()));
        holder.SatuanPesananPenjual.setText(buatPesananListPenjual.get(position).getSatuan());
        holder.JumlahPesananPenjual.setText(""+buatPesananListPenjual.get(position).getJumlah_pesanan());
        Glide.with(holder.imageProdukPesananPenjual.getContext())
                .load(buatPesananListPenjual.get(position).getGambar()).into(holder.imageProdukPesananPenjual);
        //holder.DeskripsiPembeli.setText(ProdukListPembeli.get(position).getDeskripsi());
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
        // TextView StokPesanan;
        TextView SatuanPesananPenjual;
        TextView JumlahPesananPenjual;


        // ImageView KeranjangProduk;
        Button UbahPenjual;
        ImageView imageProdukPesananPenjual;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            IdPesananPenjual = itemView.findViewById(R.id.tvIdBarangPesananPenjual);
            Nama_BarangPesananPenjual = itemView.findViewById(R.id.tvNamaBarangPesananPenjual);
            MerkPesananPenjual = itemView.findViewById(R.id.tvMerkPesananPenjual);
            HargaPesananPenjual = itemView.findViewById(R.id.tvHargaPesananPenjual);
            JumlahPesananPenjual = itemView.findViewById(R.id.tvJumlahPesananPenjual);
            //StokPembeli = itemView.findViewById(R.id.tvStokPembeli);
            SatuanPesananPenjual = itemView.findViewById(R.id.tvSatuanPesananPenjual);
            imageProdukPesananPenjual = itemView.findViewById(R.id.ImgItemPesananPenjual);
            //DeskripsiPembeli = itemView.findViewById(R.id.tvDeskripsiPembeli);


            // KeranjangProduk = itemView.findViewById(R.id.ImgKeranjang);
            UbahPenjual = itemView.findViewById(R.id.btnUbahPenjual);


        }

    }
}
