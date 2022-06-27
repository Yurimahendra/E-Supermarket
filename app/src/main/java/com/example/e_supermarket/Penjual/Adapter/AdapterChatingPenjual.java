package com.example.e_supermarket.Penjual.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_supermarket.Pembeli.Adapter.AdapterChat;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.Chat;
import com.example.e_supermarket.Pembeli.Model.DataPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataPenjual;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterChatingPenjual extends RecyclerView.Adapter<AdapterChatingPenjual.ViewHolder>{
    private List<DataPembeli> dataPembeliList = new ArrayList<>();
    private int index;
    private String no_ponsel ;

    private List<DataPenjual> dataPenjualList = new ArrayList<>();
    private int index1;
    private String no_ponsel1 ;


    private List<Chat> chatList;
    private Context context;

    public static final int pesanKirim = 0;
    public static final int pesanTerima = 1;

    public AdapterChatingPenjual(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == pesanKirim){
            View view = LayoutInflater.from(context).inflate(R.layout.pesan_kirim_penjual, parent, false);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.pesan_terima_penjual, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gabung(chatList.get(position));
        getProfilePembeli();
        getProfilePenjual();
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView isiChat;
        //TextView isiChatTerima;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            isiChat = itemView.findViewById(R.id.isiPesanKirimPenj);
            //isiChatTerima = itemView.findViewById(R.id.isiPesanTerimaPenj);
        }

        public void gabung(Chat chat) {
            isiChat.setText(chat.getIsiPesan());
            //isiChatTerima.setText(chat.getIsiPesan());
        }
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if (chatList.get(position).getPengirim().equals(no_ponsel1)){
            return pesanKirim;
        }else {
            return pesanTerima;
        }
    }

    private void getProfilePembeli() {
        //pbProfPembeli.setVisibility(View.VISIBLE);
        ApiRequestPembeli requestDataPembeli = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseDataPembeli> tampilData = requestDataPembeli.RetrieveDataPembeli();

        tampilData.enqueue(new Callback<ResponseDataPembeli>() {
            @Override
            public void onResponse(Call<ResponseDataPembeli> call, Response<ResponseDataPembeli> response) {
                if (response.isSuccessful()){
                    try {
                        dataPembeliList = response.body().getDataPembeli();
                        no_ponsel = dataPembeliList.get(index).getNo_ponsel();


                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){

                    }


                        /*adapterProfilePembeli = new AdapterProfilePembeli(ProfilePembeliActivity.this, dataPembeliList);
                        recyclerView.setAdapter(adapterProfilePembeli);
                        adapterProfilePembeli.notifyDataSetChanged();*/

                }

                //pbProfPembeli.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {
                //Toast.makeText(ProfilePembeliActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                //pbProfPembeli.setVisibility(View.GONE);
            }
        });


    }

    private void getProfilePenjual() {
        //pbDataPenjual.setVisibility(View.VISIBLE);
        ApiRequestDataProduk requestDataPenjual = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataPenjual> tampilData = requestDataPenjual.RetrieveDataPenjual();

        tampilData.enqueue(new Callback<ResponseDataPenjual>() {
            @Override
            public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                if (response.isSuccessful()){
                    try {
                        dataPenjualList = response.body().getDataPenjual();

                        no_ponsel1 = dataPenjualList.get(index1).getNo_ponsel();

                    }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                        // Log.i("tes", ""+nik);
                    }


                    /*adapterProfilePenjual = new AdapterProfilePenjual(HalamanProfilePenjualActivity.this, dataPenjualList);
                    recyclerView.setAdapter(adapterProfilePenjual);
                    adapterProfilePenjual.notifyDataSetChanged();*/

                }

                //pbDataPenjual.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                //Toast.makeText(HalamanProfilePenjualActivity.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                // pbDataPenjual.setVisibility(View.GONE);
            }
        });

    }
}
