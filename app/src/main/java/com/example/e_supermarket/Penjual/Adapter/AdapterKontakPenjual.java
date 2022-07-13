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
import com.example.e_supermarket.Penjual.Activity.ChattinganPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.KontakChatPenjualActivity;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterKontakPenjual extends RecyclerView.Adapter<AdapterKontakPenjual.MyViewHolder>{
    private KontakChatPenjualActivity kontakChatPenjualActivity;
    private List<DataPembeli> dataPembeliList;

    private String nama ;
    private String no_ponsel ;
    private String gambar;
    private String alamat;
    private String nama_toko;

    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    private int index;

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
        //holder.alamatpembeli.setText(dataPembeliList.get(position).g);
        Glide.with(holder.ImgProfilePmbl.getContext())
                .load(RetroServer.imageURL + dataPembeliList.get(position).getGambar()).into(holder.ImgProfilePmbl);

        getProfilePenjual();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foto_pembeli", RetroServer.imageURL + dataPembeliList.get(position).getGambar());
                bundle.putString("nama_pembeli", dataPembeliList.get(position).getNama());
                bundle.putString("id", String.valueOf(dataPembeliList.get(position).getId()));
                bundle.putString("no_ponsel_pembeli", dataPembeliList.get(position).getNo_ponsel());
                bundle.putString("alamat", dataPembeliList.get(position).getAlamat());

                bundle.putString("foto_penjual", RetroServer.imageURL + gambar);
                bundle.putString("nama_penjual", nama);
                bundle.putString("no_ponsel_penjual", no_ponsel);
                //bundle.putInt("jumlah", count);
                Intent intent = new Intent(kontakChatPenjualActivity, ChattinganPenjualActivity.class);
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
        TextView alamatpembeli;
        CircleImageView ImgProfilePmbl;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idPmbl = itemView.findViewById(R.id.tvidKontakPenj);
            Nama_pembeli = itemView.findViewById(R.id.tvNamaKontakPenj);
            Noponselpembeli = itemView.findViewById(R.id.tvNoponKontakPenj);
            ImgProfilePmbl = itemView.findViewById(R.id.imgProfilKontakPenj);
            alamatpembeli = itemView.findViewById(R.id.tvalamatPenjToPemb);

        }
    }

    private void getProfilePenjual() {
        //pbKontakPemb.setVisibility(View.VISIBLE);
        ApiRequestDataProduk requestDataPenjual = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataPenjual> tampilData = requestDataPenjual.RetrieveDataPenjual();

        tampilData.enqueue(new Callback<ResponseDataPenjual>() {
            @Override
            public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                if (response.isSuccessful()){
                    try {
                        dataPenjualList = response.body().getDataPenjual();

                        nama = dataPenjualList.get(index).getNama();
                        no_ponsel = dataPenjualList.get(index).getNo_ponsel();
                        gambar = dataPenjualList.get(index).getGambar();

                        // Log.i("tes", ""+nik);

                        //tvNamaKontakPenj.setText(nama);
                        //tvNoponKontakPenj.setText(no_ponsel);
                        //Glide.with(proflKontkPenj.getContext())
                        // .load(RetroServer.imageURL + gambar).into(proflKontkPenj);

                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        // Log.i("tes", ""+nik);
                    }


                    /*adapterProfilePenjual = new AdapterProfilePenjual(HalamanProfilePenjualActivity.this, dataPenjualList);
                    recyclerView.setAdapter(adapterProfilePenjual);
                    adapterProfilePenjual.notifyDataSetChanged();*/

                }

                //pbKontakPemb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                //Toast.makeText(HalamanKontakPembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                //pbKontakPemb.setVisibility(View.GONE);
            }
        });

    }
}
