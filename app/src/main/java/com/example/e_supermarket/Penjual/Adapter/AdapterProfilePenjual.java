package com.example.e_supermarket.Penjual.Adapter;

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
import com.example.e_supermarket.Penjual.Activity.FormEditProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProfilePenjual extends RecyclerView.Adapter<AdapterProfilePenjual.MyViewHolder>{

    private Context context;
    private List<DataPenjual> penjualList;

    FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public AdapterProfilePenjual(Context context, List<DataPenjual> penjualList) {
        this.context = context;
        this.penjualList = penjualList;
    }

    @NonNull
    @Override
    public AdapterProfilePenjual.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profil_penjual, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.idPnjl.setText(""+penjualList.get(position).getId());
        holder.Nama_penjual.setText(penjualList.get(position).getNama());
        holder.Nik.setText(String.valueOf(penjualList.get(position).getNik()));
        holder.Tmptlahirpenjual.setText(penjualList.get(position).getTempat_lahir());
        holder.Tgllahirpenjual.setText(penjualList.get(position).getTanggal_lahir());
        holder.Jkpenjual.setText(penjualList.get(position).getJenis_kelamin());
        holder.Alamatpenjual.setText(penjualList.get(position).getAlamat());
        holder.Noponselpenjual.setText(penjualList.get(position).getNo_ponsel());
        holder.Namatokopenjual.setText(penjualList.get(position).getNama_toko());
        Glide.with(holder.imgProfilePenjual.getContext())
                .load(RetroServer.imageURL + penjualList.get(position).getGambar()).into(holder.imgProfilePenjual);

        holder.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataPenjual item = penjualList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                bundle.putString("nama_penjual", item.getNama());
                bundle.putLong("nik", item.getNik());
                bundle.putString("tempat_lahir", item.getTempat_lahir());
                bundle.putString("tanggal_lahir", item.getTanggal_lahir());
                bundle.putString("jenis_kelamin", item.getJenis_kelamin());
                bundle.putString("alamat", item.getAlamat());
                bundle.putString("no_ponsel", item.getNo_ponsel());
                bundle.putString("nama_toko", item.getNama_toko());
                bundle.putString("gambar", RetroServer.imageURL + item.getGambar());

                Intent intent = new Intent(context, FormEditProfilePenjualActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return penjualList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idPnjl;
        TextView Nama_penjual;
        TextView Nik;
        TextView Tmptlahirpenjual;
        TextView Tgllahirpenjual;
        TextView Jkpenjual;
        TextView Alamatpenjual;
        TextView Noponselpenjual;
        TextView Namatokopenjual;
        CircleImageView imgProfilePenjual;

        Button editProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idPnjl = itemView.findViewById(R.id.tvIdPnjl);
            Nama_penjual = itemView.findViewById(R.id.tvnamapenjual);
            Nik = itemView.findViewById(R.id.tvnikpenjual);
            Tmptlahirpenjual = itemView.findViewById(R.id.tvtmptlahirpenjual);
            Tgllahirpenjual = itemView.findViewById(R.id.tvtgllahirpenjual);
            Jkpenjual = itemView.findViewById(R.id.tvjkpenjual);
            Alamatpenjual = itemView.findViewById(R.id.tvalamatpenjual);
            Noponselpenjual = itemView.findViewById(R.id.tvnoponselpenjual);
            Namatokopenjual = itemView.findViewById(R.id.tvnamatokopenjual);
            imgProfilePenjual = itemView.findViewById(R.id.ImgProfilePenjual);

            editProfile = itemView.findViewById(R.id.btnEditProfilePenjual);
        }
    }
}
