package com.example.e_supermarket.Pembeli.Adapter;

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
import com.example.e_supermarket.Pembeli.Activity.FormEditProfilePembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.ProfilePembeliActivity;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Penjual.Activity.FormEditProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Adapter.AdapterProfilePenjual;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProfilePembeli extends RecyclerView.Adapter<AdapterProfilePembeli.MyViewHolder>{
    private ProfilePembeliActivity profilePembeliActivity;
    private List<DataPembeli> dataPembeliList;

    public AdapterProfilePembeli(ProfilePembeliActivity profilePembeliActivity, List<DataPembeli> dataPembeliList) {
        this.profilePembeliActivity = profilePembeliActivity;
        this.dataPembeliList = dataPembeliList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(profilePembeliActivity).inflate(R.layout.profile_pembeli, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.idPmbl.setText(String.valueOf(dataPembeliList.get(position).getId()));
        holder.Nama_pembeli.setText(dataPembeliList.get(position).getNama());
        holder.Nik_Pembeli.setText(String.valueOf(dataPembeliList.get(position).getNik()));
        holder.Tmptlahirpembeli.setText(dataPembeliList.get(position).getTempat_lahir());
        holder.Tgllahirpembeli.setText(dataPembeliList.get(position).getTanggal_lahir());
        holder.Jkpembeli.setText(dataPembeliList.get(position).getJenis_kelamin());
        holder.Alamatpembeli.setText(dataPembeliList.get(position).getAlamat());
        holder.Noponselpembeli.setText(dataPembeliList.get(position).getNo_ponsel());
        Glide.with(holder.ImgProfilePmbl.getContext())
                .load(RetroServer.imageURL + dataPembeliList.get(position).getGambar()).into(holder.ImgProfilePmbl);

        holder.editProfilepembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataPembeli item = dataPembeliList.get(position);
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

                Intent intent = new Intent(profilePembeliActivity, FormEditProfilePembeliActivity.class);
                intent.putExtras(bundle);
                profilePembeliActivity.startActivity(intent);
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
        TextView Nik_Pembeli;
        TextView Tmptlahirpembeli;
        TextView Tgllahirpembeli;
        TextView Jkpembeli;
        TextView Alamatpembeli;
        TextView Noponselpembeli;
        CircleImageView ImgProfilePmbl;

        Button editProfilepembeli;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idPmbl = itemView.findViewById(R.id.tvIdPmbl);
            Nama_pembeli = itemView.findViewById(R.id.tvnamapembeli);
            Nik_Pembeli = itemView.findViewById(R.id.tvnikpembeli);
            Tmptlahirpembeli = itemView.findViewById(R.id.tvtmptlahirpembeli);
            Tgllahirpembeli = itemView.findViewById(R.id.tvtgllahirpembeli);
            Jkpembeli = itemView.findViewById(R.id.tvjkpembeli);
            Alamatpembeli = itemView.findViewById(R.id.tvalamatpembeli);
            Noponselpembeli = itemView.findViewById(R.id.tvnoponselpembeli);
            ImgProfilePmbl = itemView.findViewById(R.id.ImgProfilePembeli);

            editProfilepembeli = itemView.findViewById(R.id.btnEditProfilePembeli);
        }
    }
}
