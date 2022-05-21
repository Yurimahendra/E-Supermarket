package com.example.e_supermarket.Pembeli.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.KeranjangBelanjaActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.DataKeranjang;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseBuatPesanan;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataKeranjang;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        holder.hapusitmKeranjg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idOrderan = dataKeranjangList.get(position).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.IdKeranjang.getContext());
                builder.setTitle("Apakah yakin ingin Di hapus ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ApiRequestPembeli haDataKeranjang= RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                        Call<ResponseDataKeranjang> hapusItmKeranjg = haDataKeranjang.hapusDataKeranjang(idOrderan);
                        hapusItmKeranjg.enqueue(new Callback<ResponseDataKeranjang>() {
                            @Override
                            public void onResponse(Call<ResponseDataKeranjang> call, Response<ResponseDataKeranjang> response) {
                                try {
                                    //startActivity(new Intent(OrderanPembeliActivity.this, OrderanPembeliActivity.class));
                                    Toast.makeText(keranjangBelanjaActivity.getApplicationContext(), "Produk Berhasil Dihapus", Toast.LENGTH_SHORT).show();

                                }catch (NullPointerException nullPointerException){
                                    Toast.makeText(keranjangBelanjaActivity.getApplicationContext(), "Error "+nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDataKeranjang> call, Throwable t) {
                                Toast.makeText(keranjangBelanjaActivity.getApplicationContext(), "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialogInterface.dismiss();

                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });

        holder.pilihItmKeranjng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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



        ImageView imageProdukKeranjang;
        ImageView hapusitmKeranjg;
        ImageView tambahJumlah;
        ImageView KurangJumlah;
        CheckBox pilihItmKeranjng;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            IdKeranjang = itemView.findViewById(R.id.tvIdBarangKeranjang);
            Nama_BarangKeranjang = itemView.findViewById(R.id.tvNamaBarangKeranjang);
            MerkKeranjang= itemView.findViewById(R.id.tvMerkKeranjang);
            HargaKeranjang = itemView.findViewById(R.id.tvHargaKeranjang);
            StokKeranjang = itemView.findViewById(R.id.tvStokKeranjang);
            SatuanKeranjang = itemView.findViewById(R.id.tvSatuanKeranjang);
            imageProdukKeranjang = itemView.findViewById(R.id.ImgItemKeranjang);
            hapusitmKeranjg = itemView.findViewById(R.id.DeleteItmKeranjng);
            pilihItmKeranjng = itemView.findViewById(R.id.CbItemKeranjng);
            tambahJumlah = itemView.findViewById(R.id.imgTambahiKeranjang);
            KurangJumlah = itemView.findViewById(R.id.imgKurangiKeranjang);
            //DeskripsiKeranjang = itemView.findViewById(R.id.tvDeskripsi);


           // KeranjangProduk = itemView.findViewById(R.id.ImgKeranjang);
            //BeliSekrang = itemView.findViewById(R.id.BtnBeliSekarang);


        }

    }
}
