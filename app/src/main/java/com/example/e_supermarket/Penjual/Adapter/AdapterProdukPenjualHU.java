package com.example.e_supermarket.Penjual.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Penjual.Activity.Form_Edit_Produk_Activity;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukPenjualHU extends RecyclerView.Adapter<AdapterProdukPenjualHU.MyViewHolder>{
    private HalamanUtamaPenjualActivity halamanUtamaPenjualActivity;
    private List<DataProduk> ProdukList;
    private String n_satuan[] = {"Kg", "Gr", "Pcs", "Lusin", "Kodi", "Gross", "Pack"};
    int id_satuan;

    public AdapterProdukPenjualHU(HalamanUtamaPenjualActivity halamanUtamaPenjualActivity, List<DataProduk> produkList) {
        this.halamanUtamaPenjualActivity = halamanUtamaPenjualActivity;
        this.ProdukList = produkList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(halamanUtamaPenjualActivity).inflate(R.layout.item_produk_penjual, parent, false);

        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Id.setText(String.valueOf(ProdukList.get(position).getNama_barang()));
        holder.Nama_Barang.setText(ProdukList.get(position).getNama_barang());
        holder.Merk.setText(ProdukList.get(position).getMerk());
        holder.Harga.setText(String.valueOf(ProdukList.get(position).getHarga()));
        holder.Stok.setText(String.valueOf(ProdukList.get(position).getStok()));
        holder.Satuan.setText(ProdukList.get(position).getSatuan());
        Glide.with(holder.imageProduk.getContext())
                .load(RetroServer.imageURL + ProdukList.get(position).getGambar()).into(holder.imageProduk);
        holder.Deskripsi.setText(ProdukList.get(position).getDeskripsi());

        holder.editProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataProduk item = ProdukList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                bundle.putString("nama_barang", item.getNama_barang());
                bundle.putString("merk", item.getMerk());
                bundle.putInt("harga", item.getHarga());
                bundle.putInt("stok", item.getStok());
                bundle.putString("satuan", item.getSatuan());
                bundle.putString("gambar", RetroServer.imageURL + item.getGambar());
                bundle.putString("deskripsi", item.getDeskripsi());
                Intent intent = new Intent(halamanUtamaPenjualActivity, Form_Edit_Produk_Activity.class);
                intent.putExtras(bundle);
                halamanUtamaPenjualActivity.startActivity(intent);
            }
        });

        holder.hapusProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idProduk = ProdukList.get(position).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Id.getContext());
                builder.setTitle("Apakah yakin ingin dihapus ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ApiRequestDataProduk haDataProduk = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                        Call<ResponseDataProduk> hapusDataProduk = haDataProduk.hapusData(idProduk);
                        hapusDataProduk.enqueue(new Callback<ResponseDataProduk>() {
                            @Override
                            public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {
                                try {
                                    int kode = response.body().getKode();
                                    String pesan = response.body().getPesan();
                                    if (kode == 200){
                                        Toast.makeText(halamanUtamaPenjualActivity.getApplicationContext(), "Pesan :"+pesan, Toast.LENGTH_SHORT).show();
                                    }
                                }catch (NullPointerException nullPointerException){
                                    Toast.makeText(halamanUtamaPenjualActivity.getApplicationContext(), "Data Gagal Terhapus "+nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDataProduk> call, Throwable t) {
                                Toast.makeText(halamanUtamaPenjualActivity.getApplicationContext(), "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return ProdukList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Id;
        TextView Nama_Barang;
        TextView Merk;
        TextView Harga;
        TextView Stok;
        TextView Satuan;
        TextView Deskripsi;

        ImageView editProduk;
        ImageView hapusProduk;
        CircleImageView imageProduk;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Id = itemView.findViewById(R.id.tvIdBarang);
            Nama_Barang = itemView.findViewById(R.id.tvNamaBarang);
            Merk = itemView.findViewById(R.id.tvMerk);
            Harga = itemView.findViewById(R.id.tvHarga);
            Stok = itemView.findViewById(R.id.tvStok);
            Satuan = itemView.findViewById(R.id.tvSatuan);
            imageProduk = (CircleImageView) itemView.findViewById(R.id.ImgProdukView);
            Deskripsi = itemView.findViewById(R.id.tvDeskripsi);

            editProduk = (ImageView) itemView.findViewById(R.id.ImgEdit);
            hapusProduk = (ImageView) itemView.findViewById(R.id.ImgHapus);


        }

    }
}
