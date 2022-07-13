package com.example.e_supermarket.Penjual.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Activity.ChatPembeliActivity;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Penjual.Activity.ChattinganPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanChatPenjualActivity;
import com.example.e_supermarket.Penjual.Activity.KontakChatPenjualActivity;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDaftarChatPenjual extends RecyclerView.Adapter<AdapterDaftarChatPenjual.MyViewHolder>{
    private HalamanChatPenjualActivity halamanChatPenjualActivity;
    private List<DataPembeli> dataPembeliList;

    private String nama ;
    private String no_ponsel ;
    private String gambar;

    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    private int index;
    private int index1;
    private String no_ponsel1 ;

    private ArrayList<String> daftarID = new ArrayList<>();;
    private String ID;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    public AdapterDaftarChatPenjual(HalamanChatPenjualActivity halamanChatPenjualActivity, List<DataPembeli> dataPembeliList) {
        this.halamanChatPenjualActivity = halamanChatPenjualActivity;
        this.dataPembeliList = dataPembeliList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(halamanChatPenjualActivity).inflate(R.layout.daftar_chat_penjual, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Nama_pembeli.setText(dataPembeliList.get(position).getNama());
        Glide.with(holder.ImgProfilePmbl.getContext())
                .load(RetroServer.imageURL + dataPembeliList.get(position).getGambar()).into(holder.ImgProfilePmbl);

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

                        reference.child("daftar chat").child(dataPembeliList.get(position).getNo_ponsel()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                daftarID.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    ID = Objects.requireNonNull(snapshot.child("IDChat").getValue().toString());
                                    if (ID.equals(no_ponsel)){
                                        holder.Nama_pembeli.setVisibility(View.VISIBLE);
                                        holder.ImgProfilePmbl.setVisibility(View.VISIBLE);
                                    }
                                    daftarID.add(ID);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                //Toast.makeText(ChatPembeliActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                            }
                        });


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
                Intent intent = new Intent(halamanChatPenjualActivity, ChattinganPenjualActivity.class);
                intent.putExtras(bundle);
                halamanChatPenjualActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataPembeliList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Nama_pembeli;
        CircleImageView ImgProfilePmbl;
        LinearLayout lnChatPenj;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nama_pembeli = itemView.findViewById(R.id.tvNamahalChatPemb);
            ImgProfilePmbl = itemView.findViewById(R.id.imgProfilhalChatPemb);
            lnChatPenj = itemView.findViewById(R.id.lnChatPenj);

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

                        reference.child("daftar chat").child(no_ponsel).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                daftarID.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    ID = Objects.requireNonNull(snapshot.child("IDChat").getValue().toString());
                                    if (ID.equalsIgnoreCase(no_ponsel1)){
                                        //holder.Nama_pembeli.setVisibility(View.VISIBLE);
                                        //holder.ImgProfilePmbl.setVisibility(View.VISIBLE);
                                    }
                                    daftarID.add(ID);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                //Toast.makeText(ChatPembeliActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                            }
                        });


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
