package com.example.e_supermarket.Pembeli.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Adapter.AdapterChat;
import com.example.e_supermarket.Pembeli.Model.Chat;
import com.example.e_supermarket.R;
import com.example.e_supermarket.databinding.ActivityChattinganBinding;
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

public class ChattinganActivity extends AppCompatActivity {
    ImageView backChatinga;
    CircleImageView fotoChtingan;
    TextView NamaChtingan;
    private String Ufoto;
    private String Unama;
    private String Upenerima;
    private String Upengirim;

    private DatabaseReference reference;

    private ActivityChattinganBinding chattinganBinding;
    private AdapterChat adapterChat;
    private List<Chat> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chattingan);
        chattinganBinding = DataBindingUtil.setContentView(this, R.layout.activity_chattingan);
        reference = FirebaseDatabase.getInstance().getReference();

        backChatinga = findViewById(R.id.imgBackChatinganPembeli);
        backChatinga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        NamaChtingan = findViewById(R.id.tvNamaChatinganPembeli);
        fotoChtingan = findViewById(R.id.imgProfilChtinganPembeli);

        Bundle bundle = getIntent().getExtras();
        Ufoto = bundle.getString("foto");
        Unama = bundle.getString("nama");
        Upenerima = bundle.getString("no_ponsel");
        Upengirim = bundle.getString("pengirim");

        NamaChtingan.setText(Unama);
        Glide.with(fotoChtingan.getContext())
                .load(Ufoto).into(fotoChtingan);

        chattinganBinding.btnKirimChatPemb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(chattinganBinding.edtTulisPesanPemb.getText().toString())){
                    kirimPesan(chattinganBinding.edtTulisPesanPemb.getText().toString());
                    chattinganBinding.edtTulisPesanPemb.setText("");
                }
            }
        });

        chatList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        chattinganBinding.recChatinganPembeli.setLayoutManager(layoutManager);
        
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
                        if (chat != null && chat.getPengirim().equals(Upengirim)
                                && chat.getPenerima().equals(Upenerima)
                                || chat.getPenerima().equals(Upengirim)
                                && chat.getPengirim().equals(Upenerima)){
                            chatList.add(chat);
                        }
                    }

                    if (adapterChat != null){
                        adapterChat.notifyDataSetChanged();
                    }else {
                        adapterChat = new AdapterChat(chatList, ChattinganActivity.this);
                        chattinganBinding.recChatinganPembeli.setAdapter(adapterChat);
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

        Chat chat = new Chat(text, Upengirim, Upenerima,
                hari);

        reference.child("Pesan").push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("pengiriman", "berhasil");
            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("daftar chat").
                child(Upengirim).child(Upenerima);
        reference1.child("IDChat").setValue(Upenerima);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("daftar chat").
                child(Upenerima).child(Upengirim);
        reference2.child("IDChat").setValue(Upenerima);

    }
}