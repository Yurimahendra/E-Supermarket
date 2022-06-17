package com.example.e_supermarket.Penjual.Adapter;

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
import com.example.e_supermarket.Pembeli.Activity.ChattinganActivity;
import com.example.e_supermarket.Pembeli.Activity.HalamanKontakPembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.ProfilePembeliActivity;
import com.example.e_supermarket.Pembeli.Adapter.AdapterProfilePembeli;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Penjual.Activity.KontakChatPenjualActivity;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterKontakPenjual extends RecyclerView.Adapter<AdapterKontakPenjual.MyViewHolder>{
    private KontakChatPenjualActivity kontakChatPenjualActivity;
    private List<DataPembeli> dataPembeliList;

    public AdapterKontakPenjual(KontakChatPenjualActivity kontakChatPenjualActivity, List<DataPembeli> dataPembeliList) {
        this.kontakChatPenjualActivity = kontakChatPenjualActivity;
        this.dataPembeliList = dataPembeliList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(kontakChatPenjualActivity).inflate(R.layout.kontak_penjual, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.idPmbl.setText(String.valueOf(dataPembeliList.get(position).getId()));
        holder.Nama_pembeli.setText(dataPembeliList.get(position).getNama());
        holder.Noponselpembeli.setText(dataPembeliList.get(position).getNo_ponsel());
        Glide.with(holder.ImgProfilePmbl.getContext())
                .load(RetroServer.imageURL + dataPembeliList.get(position).getGambar()).into(holder.ImgProfilePmbl);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foto", RetroServer.imageURL + dataPembeliList.get(position).getGambar());
                bundle.putString("nama", dataPembeliList.get(position).getNama());
                bundle.putString("id", String.valueOf(dataPembeliList.get(position).getId()));
                bundle.putString("no_ponsel", dataPembeliList.get(position).getNo_ponsel());
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(kontakChatPenjualActivity, ChattinganActivity.class);
                intent.putExtras(bundle);
                kontakChatPenjualActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataPembeliList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idPmbl;
        TextView Nama_pembeli;
        TextView Noponselpembeli;
        CircleImageView ImgProfilePmbl;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idPmbl = itemView.findViewById(R.id.tvidKontakPenj);
            Nama_pembeli = itemView.findViewById(R.id.tvNamaKontakPenj);
            Noponselpembeli = itemView.findViewById(R.id.tvNoponKontakPenj);
            ImgProfilePmbl = itemView.findViewById(R.id.imgProfilKontakPenj);

        }
    }
}
