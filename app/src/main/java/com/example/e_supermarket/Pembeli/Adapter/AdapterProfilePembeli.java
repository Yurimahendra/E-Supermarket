package com.example.e_supermarket.Pembeli.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_supermarket.Pembeli.Activity.ProfilePembeliActivity;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Penjual.Adapter.AdapterProfilePenjual;
import com.example.e_supermarket.R;

import java.util.List;

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
        holder.Nama_pembeli.setText(dataPembeliList.get(position).getNama());
        holder.Nik_Pembeli.setText(String.valueOf(dataPembeliList.get(position).getNik()));
        holder.Tmptlahirpembeli.setText(dataPembeliList.get(position).getTempat_lahir());
        holder.Tgllahirpembeli.setText(dataPembeliList.get(position).getTanggal_lahir());
        holder.Jkpembeli.setText(dataPembeliList.get(position).getJenis_kelamin());
        holder.Alamatpembeli.setText(dataPembeliList.get(position).getAlamat());
        holder.Noponselpembeli.setText(dataPembeliList.get(position).getNo_ponsel());
    }

    @Override
    public int getItemCount() {
        return dataPembeliList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Nama_pembeli;
        TextView Nik_Pembeli;
        TextView Tmptlahirpembeli;
        TextView Tgllahirpembeli;
        TextView Jkpembeli;
        TextView Alamatpembeli;
        TextView Noponselpembeli;

        Button editProfilepembeli;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nama_pembeli = itemView.findViewById(R.id.tvnamapembeli);
            Nik_Pembeli = itemView.findViewById(R.id.tvnikpembeli);
            Tmptlahirpembeli = itemView.findViewById(R.id.tvtmptlahirpembeli);
            Tgllahirpembeli = itemView.findViewById(R.id.tvtgllahirpembeli);
            Jkpembeli = itemView.findViewById(R.id.tvjkpembeli);
            Alamatpembeli = itemView.findViewById(R.id.tvalamatpembeli);
            Noponselpembeli = itemView.findViewById(R.id.tvnoponselpembeli);

            editProfilepembeli = itemView.findViewById(R.id.btnEditProfilePembeli);
        }
    }
}
