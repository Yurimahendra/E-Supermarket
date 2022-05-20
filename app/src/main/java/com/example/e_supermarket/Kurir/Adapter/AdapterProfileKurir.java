package com.example.e_supermarket.Kurir.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Kurir.Activity.FormEditProfileKurirActivity;
import com.example.e_supermarket.Kurir.Activity.HalamanProfilKurirActivity;
import com.example.e_supermarket.Kurir.Model.DataKurir;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Penjual.Activity.FormEditProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProfileKurir extends RecyclerView.Adapter<AdapterProfileKurir.MyViewHolderK>{
    private HalamanProfilKurirActivity halamanProfilKurirActivity;
    private List<DataKurir> dataKurirList;

    public AdapterProfileKurir(HalamanProfilKurirActivity halamanProfilKurirActivity, List<DataKurir> dataKurirList) {
        this.halamanProfilKurirActivity = halamanProfilKurirActivity;
        this.dataKurirList = dataKurirList;
    }

    @NonNull
    @Override
    public MyViewHolderK onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(halamanProfilKurirActivity).inflate(R.layout.profile_kurir, parent, false);
        return new MyViewHolderK(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderK holder, int position) {
        holder.idKurir.setText(String.valueOf(dataKurirList.get(position).getId()));
        holder.nama_kurir.setText(dataKurirList.get(position).getNama());
        holder.nik_kurir.setText(String.valueOf(dataKurirList.get(position).getNik()));
        holder.TeLa_kurir.setText(dataKurirList.get(position).getTempat_lahir());
        holder.Tala_Kurir.setText(dataKurirList.get(position).getTanggal_lahir());
        holder.jkKurir.setText(dataKurirList.get(position).getJenis_kelamin());
        holder.alamat_kurir.setText(dataKurirList.get(position).getAlamat());
        holder.nopon_kurir.setText(dataKurirList.get(position).getNo_ponsel());
        Glide.with(holder.ImgProfileKurir.getContext())
                .load(RetroServer.imageURL + dataKurirList.get(position).getGambar()).into(holder.ImgProfileKurir);

        holder.btnEditProfKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataKurir item = dataKurirList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                bundle.putString("nama_penjual", item.getNama());
                bundle.putLong("nik", item.getNik());
                bundle.putString("tempat_lahir", item.getTempat_lahir());
                bundle.putString("tanggal_lahir", item.getTanggal_lahir());
                bundle.putString("jenis_kelamin", item.getJenis_kelamin());
                bundle.putString("alamat", item.getAlamat());
                bundle.putString("no_ponsel", item.getNo_ponsel());
                bundle.putString("gambar", RetroServer.imageURL + item.getGambar());

                Intent intent = new Intent(halamanProfilKurirActivity, FormEditProfileKurirActivity.class);
                intent.putExtras(bundle);
                halamanProfilKurirActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataKurirList.size();
    }

    public static class MyViewHolderK extends RecyclerView.ViewHolder {
        TextView idKurir;
        TextView nama_kurir;
        TextView nik_kurir;
        TextView TeLa_kurir;
        TextView Tala_Kurir;
        TextView jkKurir;
        TextView alamat_kurir;
        TextView nopon_kurir;
        CircleImageView ImgProfileKurir;

        Button btnEditProfKurir;

        public MyViewHolderK(@NonNull View itemView) {
            super(itemView);

            idKurir = itemView.findViewById(R.id.tvIdKurir);
            nama_kurir = itemView.findViewById(R.id.tvnamaKurir);
            nik_kurir = itemView.findViewById(R.id.tvnikKurir);
            TeLa_kurir = itemView.findViewById(R.id.tvtmptlahirKurir);
            Tala_Kurir = itemView.findViewById(R.id.tvtgllahirKurir);
            jkKurir = itemView.findViewById(R.id.tvjkKurir);
            alamat_kurir = itemView.findViewById(R.id.tvalamatKurir);
            nopon_kurir = itemView.findViewById(R.id.tvnoponselKurir);
            ImgProfileKurir = itemView.findViewById(R.id.ImgProfileK);

            btnEditProfKurir = itemView.findViewById(R.id.btnEditProfileKurir);

        }
    }
}
