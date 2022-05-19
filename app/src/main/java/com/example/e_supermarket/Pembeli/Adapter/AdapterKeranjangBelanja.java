package com.example.e_supermarket.Pembeli.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.KeranjangBelanjaActivity;
import com.example.e_supermarket.Pembeli.Model.DataKeranjang;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.List;

public class AdapterKeranjangBelanja extends RecyclerView.Adapter<AdapterKeranjangBelanja.MyViewHolder>{
    private KeranjangBelanjaActivity keranjangBelanjaActivity;
    private List<DataKeranjang> dataKeranjangList;

    public AdapterKeranjangBelanja(KeranjangBelanjaActivity keranjangBelanjaActivity, List<DataKeranjang> dataKeranjangList) {
        this.keranjangBelanjaActivity = keranjangBelanjaActivity;
        this.dataKeranjangList = dataKeranjangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(keranjangBelanjaActivity).inflate(R.layout.item_produk_keranjang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.IdKeranjang.setText(String.valueOf(dataKeranjangList.get(position).getId()));
        holder.Nama_BarangKeranjang.setText(dataKeranjangList.get(position).getNama_barang());
        holder.MerkKeranjang.setText(dataKeranjangList.get(position).getMerk());
        holder.HargaKeranjang.setText(String.valueOf(dataKeranjangList.get(position).getHarga()));
        holder.StokKeranjang.setText(String.valueOf(dataKeranjangList.get(position).getStok()));
        holder.SatuanKeranjang.setText(dataKeranjangList.get(position).getSatuan());
        Glide.with(holder.imageProdukKeranjang.getContext())
                .load(RetroServer.imageURL + dataKeranjangList.get(position).getGambar()).into(holder.imageProdukKeranjang);
    }

    @Override
    public int getItemCount() {
        return dataKeranjangList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView IdKeranjang;
        TextView Nama_BarangKeranjang;
        TextView MerkKeranjang;
        TextView HargaKeranjang;
        TextView StokKeranjang;
        TextView SatuanKeranjang;
        TextView DeskripsiKeranjang;


        //ImageView KeranjangProduk;
        //Button BeliSekrang;
        ImageView imageProdukKeranjang;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            IdKeranjang = itemView.findViewById(R.id.tvIdBarangKeranjang);
            Nama_BarangKeranjang = itemView.findViewById(R.id.tvNamaBarangKeranjang);
            MerkKeranjang= itemView.findViewById(R.id.tvMerkKeranjang);
            HargaKeranjang = itemView.findViewById(R.id.tvHargaKeranjang);
            StokKeranjang = itemView.findViewById(R.id.tvStokKeranjang);
            SatuanKeranjang = itemView.findViewById(R.id.tvSatuanKeranjang);
            imageProdukKeranjang = itemView.findViewById(R.id.ImgItemKeranjang);
            //DeskripsiKeranjang = itemView.findViewById(R.id.tvDeskripsi);


           // KeranjangProduk = itemView.findViewById(R.id.ImgKeranjang);
            //BeliSekrang = itemView.findViewById(R.id.BtnBeliSekarang);


        }

    }
}
