package com.example.e_supermarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterProfilePenjual extends RecyclerView.Adapter<AdapterProfilePenjual.MyViewHolder>{

    private ProfileFragmentPenjual profileFragmentPenjual;
    private List<DataPenjual> penjualList;
    private FormDataPenjualActivity formDataPenjualActivity;

    FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public AdapterProfilePenjual(ProfileFragmentPenjual profileFragmentPenjual, List<DataPenjual> penjualList) {
        this.profileFragmentPenjual = profileFragmentPenjual;
        this.penjualList = penjualList;
    }

    @NonNull
    @Override
    public AdapterProfilePenjual.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(profileFragmentPenjual.getContext()).inflate(R.layout.profil_penjual, parent, false);
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Nama_penjual.setText(penjualList.get(position).getNama());
        holder.Nik.setText(String.valueOf(penjualList.get(position).getNik()));
        holder.Tmptlahirpenjual.setText(penjualList.get(position).getTempat_lahir());
        holder.Tgllahirpenjual.setText(penjualList.get(position).getTanggal_lahir());
        holder.Jkpenjual.setText(penjualList.get(position).getJenis_kelamin());
        holder.Alamatpenjual.setText(penjualList.get(position).getAlamat());
        holder.Noponselpenjual.setText(penjualList.get(position).getNo_ponsel());
        holder.Namatokopenjual.setText(penjualList.get(position).getNama_toko());
    }

    @Override
    public int getItemCount() {
        return penjualList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Nama_penjual;
        TextView Nik;
        TextView Tmptlahirpenjual;
        TextView Tgllahirpenjual;
        TextView Jkpenjual;
        TextView Alamatpenjual;
        TextView Noponselpenjual;
        TextView Namatokopenjual;

        Button editProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nama_penjual = itemView.findViewById(R.id.tvnamapenjual);
            Nik = itemView.findViewById(R.id.tvnikpenjual);
            Tmptlahirpenjual = itemView.findViewById(R.id.tvtmptlahirpenjual);
            Tgllahirpenjual = itemView.findViewById(R.id.tvtgllahirpenjual);
            Jkpenjual = itemView.findViewById(R.id.tvjkpenjual);
            Alamatpenjual = itemView.findViewById(R.id.tvalamatpenjual);
            Noponselpenjual = itemView.findViewById(R.id.tvnoponselpenjual);
            Namatokopenjual = itemView.findViewById(R.id.tvnamatokopenjual);

            editProfile = itemView.findViewById(R.id.btnEditProfilePenjual);
        }
    }
}
