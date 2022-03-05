package com.example.e_supermarket.Kurir.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_supermarket.Kurir.Activity.HalamanProfilKurirActivity;
import com.example.e_supermarket.Kurir.Model.DataKurir;
import com.example.e_supermarket.R;

import java.util.List;

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
        holder.idKurir.setText(String.valueOf(dataKurirList.get(position).getIdKurir()));
        holder.nama_kurir.setText(dataKurirList.get(position).getNama());
        holder.nik_kurir.setText(String.valueOf(dataKurirList.get(position).getNik()));
        holder.TeLa_kurir.setText(dataKurirList.get(position).getTempat_lahir());
        holder.Tala_Kurir.setText(dataKurirList.get(position).getTanggal_lahir());
        holder.jkKurir.setText(dataKurirList.get(position).getJenis_kelamin());
        holder.alamat_kurir.setText(dataKurirList.get(position).getAlamat());
        holder.nopon_kurir.setText(dataKurirList.get(position).getNo_ponsel());
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

            btnEditProfKurir = itemView.findViewById(R.id.btnEditProfileKurir);

        }
    }
}
