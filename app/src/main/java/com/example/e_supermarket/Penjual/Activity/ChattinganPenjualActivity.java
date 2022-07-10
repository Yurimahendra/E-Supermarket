package com.example.e_supermarket.Penjual.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Activity.ChattinganActivity;
import com.example.e_supermarket.Pembeli.Adapter.AdapterChat;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.Model.Chat;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseNotifOrder;
import com.example.e_supermarket.Penjual.Adapter.AdapterChatingPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;
import com.example.e_supermarket.databinding.ActivityChattinganBinding;
import com.example.e_supermarket.databinding.ActivityChattinganPenjualBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChattinganPenjualActivity extends AppCompatActivity {
    ImageView backChatinga;
    CircleImageView fotoChtinganPembeli;
    TextView NamaChtinganPembeli;

    private String UfotoPenjual;
    private String UnamaPenjual;
    private String Unoponpenjual;

    private String UfotoPembeli;
    private String UnamaPembeli;
    private String UnoponPembeli;

    private DatabaseReference reference;

    private ActivityChattinganPenjualBinding chattinganPenjualBinding;
    private AdapterChatingPenjual adapterChat;
    private List<Chat> chatList;

    private SwipeRefreshLayout swChatPenjual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chattingan_penjual);
        chattinganPenjualBinding = DataBindingUtil.setContentView(this, R.layout.activity_chattingan_penjual);
        reference = FirebaseDatabase.getInstance().getReference();

        backChatinga = findViewById(R.id.imgBackChatinganPenjual);
        backChatinga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        NamaChtinganPembeli = findViewById(R.id.tvNamaChatinganPembeli);
        fotoChtinganPembeli = findViewById(R.id.imgProfilChtinganPembeli);

        Bundle bundle = getIntent().getExtras();
        UfotoPenjual = bundle.getString("foto_penjual");
        UnamaPenjual = bundle.getString("nama_penjual");
        Unoponpenjual = bundle.getString("no_ponsel_penjual");

        UfotoPembeli = bundle.getString("foto_pembeli");
        UnamaPembeli = bundle.getString("nama_pembeli");
        UnoponPembeli = bundle.getString("no_ponsel_pembeli");

        NamaChtinganPembeli.setText(UnamaPembeli);
        Glide.with(fotoChtinganPembeli.getContext())
                .load(UfotoPembeli).into(fotoChtinganPembeli);

        chattinganPenjualBinding.btnKirimChatPenj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(chattinganPenjualBinding.edtTulisPesanPenj.getText().toString())){
                    kirimPesan(chattinganPenjualBinding.edtTulisPesanPenj.getText().toString());
                    chattinganPenjualBinding.edtTulisPesanPenj.setText("");
                }
            }
        });

        chatList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        chattinganPenjualBinding.recChatinganPenjual.setLayoutManager(layoutManager);

        bacaPesan();

    }

    @Override
    protected void onResume() {
        super.onResume();
        bacaPesan();
    }

    private void bacaPesan() {
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("Pesan").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chatList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Chat chat = snapshot.getValue(Chat.class);
                        if (chat != null && chat.getNo_pembeli().equals(UnoponPembeli)
                                && chat.getNo_penjual().equals(Unoponpenjual)
                                || chat.getNo_penjual().equals(UnoponPembeli)
                                && chat.getNo_pembeli().equals(Unoponpenjual)){
                            chatList.add(chat);
                        }
                    }

                    if (adapterChat != null){
                        adapterChat.notifyDataSetChanged();
                    }else {
                        adapterChat = new AdapterChatingPenjual(chatList, ChattinganPenjualActivity.this);
                        chattinganPenjualBinding.recChatinganPenjual.setAdapter(adapterChat);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void kirimPesan(String text) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat tanggal = new SimpleDateFormat("yyyy-MM-dd");
        String hari = tanggal.format(date);

        /*Calendar calendar = Calendar.getInstance();
        SimpleDateFormat pukul = new SimpleDateFormat("hh:mm");
        String jam = pukul.format(calendar.getTime());*/

        Chat chat = new Chat(text, Unoponpenjual, UnamaPembeli, UnoponPembeli, UfotoPembeli,
                UnamaPenjual, Unoponpenjual, UfotoPenjual, hari);

        reference.child("Pesan").push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("pengiriman", "berhasil");
            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("daftar chat").
                child(UnoponPembeli).child(Unoponpenjual);
        reference1.child("IDChat").setValue(Unoponpenjual);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("daftar chat").
                child(Unoponpenjual).child(UnoponPembeli);
        reference2.child("IDChat").setValue(Unoponpenjual);

        ApiRequestPembeli notifOrder = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
        Call<ResponseNotifOrder> SimpannotifOrder = notifOrder.SendNotifOrder(
                "Notifikasi",
                "Ada Chat Dari Penjual"
        );

        SimpannotifOrder.enqueue(new Callback<ResponseNotifOrder>() {
            @Override
            public void onResponse(Call<ResponseNotifOrder> call, Response<ResponseNotifOrder> response) {
                if( response.isSuccessful()) {
                    //Toast.makeText(BeliProdukActivity.this, "notif berhasil", Toast.LENGTH_SHORT).show();

                }else {
                    //Toast.makeText(BeliProdukActivity.this, "notif gagal"+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
                //PbBuatPesan.setVisibility(View.GONE);
                //btnBuatPesanan.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseNotifOrder> call, Throwable t) {
                //Toast.makeText(BeliProdukActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                //PbBuatPesan.setVisibility(View.GONE);
                //btnBuatPesanan.setVisibility(View.VISIBLE);
            }
        });

    }


}