package com.example.e_supermarket.Kurir.Adapter;

import android.app.DatePickerDialog;
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
import com.example.e_supermarket.Kurir.Activity.DetailPesananKurirActivity;
import com.example.e_supermarket.Kurir.Activity.HalamanUtamaKurirActivity;
import com.example.e_supermarket.Pembeli.Model.BuatPesanan;
import com.example.e_supermarket.Penjual.Activity.DetailPesananPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanNotifPenjualActivity;
import com.example.e_supermarket.Penjual.Adapter.AdapterPesananPenjual;
import com.example.e_supermarket.R;

import java.util.Calendar;
import java.util.List;

public class AdapterPesananKurir extends RecyclerView.Adapter<AdapterPesananKurir.MyViewHolder>{
    private HalamanUtamaKurirActivity halamanUtamaKurirActivity;
    private List<BuatPesanan> buatPesananListKurir;


    public AdapterPesananKurir(HalamanUtamaKurirActivity halamanUtamaKurirActivity, List<BuatPesanan> buatPesananListKurir) {
        this.halamanUtamaKurirActivity = halamanUtamaKurirActivity;
        this.buatPesananListKurir = buatPesananListKurir;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(halamanUtamaKurirActivity).inflate(R.layout.item_pesanan_kurir, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        /*String tanggalKrm = buatPesananListKurir.get(position).getTanggal_pengiriman();
        Calendar calendar = Calendar.getInstance();
        String thnSekarang = String.valueOf(calendar.get(Calendar.YEAR));
        String bulanSekarang = String.valueOf(calendar.get(Calendar.MONTH));
        String tglSekarang = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String sekarang = thnSekarang+"-"+bulanSekarang+"-"+tglSekarang;
        int comprTgl = tanggalKrm.compareTo(sekarang);*/
        holder.IdPesananKurir.setText(String.valueOf(buatPesananListKurir.get(position).getId_pesanan()));
        holder.Nama_BarangPesananKurir.setText(buatPesananListKurir.get(position).getNama_barang());
        holder.MerkPesananKurir.setText(buatPesananListKurir.get(position).getMerk_barang());
        holder.HargaPesananKurir.setText(buatPesananListKurir.get(position).getHarga_barang());
        //holder.StokPembeli.setText(String.valueOf(ProdukListPembeli.get(position).getStok()));
        holder.SatuanPesananKurir.setText(buatPesananListKurir.get(position).getSatuan());
        holder.JumlahPesananKurir.setText(""+buatPesananListKurir.get(position).getJumlah_pesanan());
        Glide.with(holder.imageProdukPesananKurir.getContext())
                .load(buatPesananListKurir.get(position).getGambar()).into(holder.imageProdukPesananKurir);
        holder.LatKurir.setText(buatPesananListKurir.get(position).getLatitude());
        holder.LongKurir.setText(buatPesananListKurir.get(position).getLongitude());
        //holder.DeskripsiPembeli.setText(ProdukListPembeli.get(position).getDeskripsi());
        holder.UbahKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuatPesanan item = buatPesananListKurir.get(position);
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
                bundle.putString("latitude", item.getLatitude());
                bundle.putString("longitude", item.getLongitude());
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(halamanUtamaKurirActivity, DetailPesananKurirActivity.class);
                intent.putExtras(bundle);
                halamanUtamaKurirActivity.startActivity(intent);
            }
        });
        String status = buatPesananListKurir.get(position).getStatus_pesanan();
        String status1 = "terima";
        try {
            if (status.equalsIgnoreCase(status1)){
                holder.Nama_BarangPesananKurir.setVisibility(View.VISIBLE);
                holder.MerkPesananKurir.setVisibility(View.VISIBLE);
                holder.HargaPesananKurir.setVisibility(View.VISIBLE);
                holder.SatuanPesananKurir.setVisibility(View.VISIBLE);
                holder.JumlahPesananKurir.setVisibility(View.VISIBLE);
                holder.imageProdukPesananKurir.setVisibility(View.VISIBLE);
                holder.UbahKurir.setVisibility(View.VISIBLE);
            }
        }catch (NullPointerException e){
            
        }



    }

    @Override
    public int getItemCount() {
        return buatPesananListKurir.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView IdPesananKurir;
        TextView Nama_BarangPesananKurir;
        TextView MerkPesananKurir;
        TextView HargaPesananKurir;
        // TextView StokPesanan;
        TextView SatuanPesananKurir;
        TextView JumlahPesananKurir;
        TextView LatKurir;
        TextView LongKurir;


        // ImageView KeranjangProduk;
        Button UbahKurir;
        ImageView imageProdukPesananKurir;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            IdPesananKurir = itemView.findViewById(R.id.tvIdBarangPesananKurir);
            Nama_BarangPesananKurir = itemView.findViewById(R.id.tvNamaBarangPesananKurir);
            MerkPesananKurir = itemView.findViewById(R.id.tvMerkPesananKurir);
            HargaPesananKurir = itemView.findViewById(R.id.tvHargaPesananKurir);
            JumlahPesananKurir = itemView.findViewById(R.id.tvJumlahPesananKurir);
            //StokPembeli = itemView.findViewById(R.id.tvStokPembeli);
            SatuanPesananKurir = itemView.findViewById(R.id.tvSatuanPesananKurir);
            imageProdukPesananKurir = itemView.findViewById(R.id.ImgItemPesananKurir);
            //DeskripsiPembeli = itemView.findViewById(R.id.tvDeskripsiPembeli);


            // KeranjangProduk = itemView.findViewById(R.id.ImgKeranjang);
            UbahKurir = itemView.findViewById(R.id.btnUbahKurir);

            LatKurir = itemView.findViewById(R.id.tvLatKurir);
            LongKurir = itemView.findViewById(R.id.tvLongKurir);


        }

    }
}
