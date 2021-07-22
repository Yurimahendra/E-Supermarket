package com.example.e_supermarket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterProdukPenjual extends RecyclerView.Adapter<AdapterProdukPenjual.MyViewHolder> {

    private HomeFragmentPenjual homeFragmentPenjual;
    private List<DataProduk> ProdukList;
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference myRef = database.getReference();
    int id_satuan;
    String Uid;
    private String n_satuan[] = {"Kg", "Gr", "Pcs", "Lusin", "Kodi", "Gross", "Pack"};


    public AdapterProdukPenjual(HomeFragmentPenjual homeFragmentPenjual, List<DataProduk> dataProdukList) {
        this.homeFragmentPenjual = homeFragmentPenjual;
        this.ProdukList = dataProdukList;

    }

    /**
     * public void UpdateData(int position){
     * DataProduk item = ProdukList.get(position);
     * Bundle bundle = new Bundle();
     * bundle.putString("uId", item.getId());
     * bundle.putString("uNama_Barang", item.getNama_barang());
     * bundle.putString("uMerk", item.getMerk());
     * bundle.putLong("uHarga", item.getHarga());
     * bundle.putLong("uStok", item.getStok());
     * bundle.putString("uSatuan", item.getSatuan());
     * bundle.putString("uDeskripsi", item.getDeskripsi());
     * Intent intent = new Intent(homeFragmentPenjual.getActivity(), Form_Edit_Produk_Activity.class);
     * intent.putExtras(bundle);
     * homeFragmentPenjual.startActivity(intent);
     * }
     **/

    @NonNull
    @Override
    public AdapterProdukPenjual.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(homeFragmentPenjual.getContext()).inflate(R.layout.item_produk_penjual, parent, false);


        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Nama_Barang.setText(ProdukList.get(position).getNama_barang());
        holder.Merk.setText(ProdukList.get(position).getMerk());
        holder.Harga.setText(ProdukList.get(position).getHarga().toString());
        holder.Stok.setText(ProdukList.get(position).getStok().toString());
        holder.Satuan.setText(ProdukList.get(position).getSatuan());
        holder.Deskripsi.setText(ProdukList.get(position).getDeskripsi());


        // holder.Harga.setText(String.valueOf(dataProduk.harga));


        holder.editProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** DataProduk item = ProdukList.get(position);
                 Bundle bundle = new Bundle();
                 bundle.putString("id", item.getId());
                 bundle.putString("nama_barang", item.getNama_barang());
                 bundle.putString("merk", item.getMerk());
                 bundle.putLong("harga", item.getHarga());
                 bundle.putLong("stok", item.getStok());
                 bundle.putString("satuan", item.getSatuan());
                 bundle.putString("deskripsi", item.getDeskripsi());
                 Intent intent = new Intent(homeFragmentPenjual.getActivity(), Form_Edit_Produk_Activity.class);
                 intent.putExtras(bundle);
                 homeFragmentPenjual.startActivity(intent);**/
                  DialogPlus dialogPlus = DialogPlus.newDialog(holder.Nama_Barang.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_form__edit__produk_))
                        .setExpanded(true, 1500)
                        .setGravity(Gravity.CENTER)
                        .create();


                View myview = dialogPlus.getHolderView();
                EditText nama_barang = myview.findViewById(R.id.UpdtNaBa);
                EditText merk = myview.findViewById(R.id.UpdtMerk);
                EditText harga = myview.findViewById(R.id.UpdtHarga);
                EditText stok = myview.findViewById(R.id.UpdtStok);
                Spinner satuan = myview.findViewById(R.id.UpdtSpSatuan);
                EditText deskripsi = myview.findViewById(R.id.UpdtDeskripsi);
                Button btnupdate = myview.findViewById(R.id.btnUpdtData);

                Uid = ProdukList.get(position).getId();
                nama_barang.setText(ProdukList.get(position).getNama_barang());
                merk.setText(ProdukList.get(position).getMerk());
                harga.setText(ProdukList.get(position).getHarga().toString());
                stok.setText(ProdukList.get(position).getStok().toString());

                if (ProdukList.get(position).getSatuan().equals("Kg")) id_satuan = 0;
                else if (ProdukList.get(position).getSatuan().equals("Gr")) id_satuan = 1;
                else if (ProdukList.get(position).getSatuan().equals("Pcs")) id_satuan = 2;
                else if (ProdukList.get(position).getSatuan().equals("Lusin")) id_satuan = 3;
                else if (ProdukList.get(position).getSatuan().equals("Kodi")) id_satuan = 4;
                else if (ProdukList.get(position).getSatuan().equals("Gross")) id_satuan = 5;
                else if (ProdukList.get(position).getSatuan().equals("Pack")) id_satuan = 6;
                satuan.setSelection(id_satuan);
                deskripsi.setText(ProdukList.get(position).getDeskripsi());


                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /**Map<String, Object> map = new HashMap<>();
                        map.put("nama_barang", nama_barang.getText().toString());
                        map.put("merk", merk.getText().toString());
                        map.put("harga", harga.getText().toString());
                        map.put("stok", stok.getText().toString());
                        map.put("satuan", satuan.getSelectedItem().toString());
                        map.put("deskripsi", deskripsi.getText().toString());**/

                        FirebaseFirestore.getInstance().collection("data_produk")
                                .document(Uid)
                                .update("nama_barang", nama_barang.getText().toString().trim(),  "merk", merk.getText().toString().trim(), "harga", harga.getText().toString().trim(), "stok", stok.getText().toString().trim(), "satuan", satuan.getSelectedItem().toString().trim(), "deskripsi", deskripsi.getText().toString().trim())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(homeFragmentPenjual.getContext(), "Update Berhasil !", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //dialogPlus.dismiss();
                                        Toast.makeText(homeFragmentPenjual.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                dialogPlus.show();
            }
        });

        holder.hapusProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Nama_Barang.getContext());
                builder.setTitle("Apakah yakin ingin dihapus ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseFirestore.getInstance().collection("data_produk")
                                .document().delete();
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProdukList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
       // String Uid;
        TextView Nama_Barang;
        TextView Merk;
        TextView Harga;
        TextView Stok;
        TextView Satuan;
        TextView Deskripsi;

        ImageView editProduk;
        ImageView hapusProduk;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Nama_Barang = itemView.findViewById(R.id.tvNamaBarang);
            Merk = itemView.findViewById(R.id.tvMerk);
            Harga = itemView.findViewById(R.id.tvHarga);
            Stok = itemView.findViewById(R.id.tvStok);
            Satuan = itemView.findViewById(R.id.tvSatuan);
            Deskripsi = itemView.findViewById(R.id.tvDeskripsi);

            editProduk = (ImageView) itemView.findViewById(R.id.ImgEdit);
            hapusProduk = (ImageView) itemView.findViewById(R.id.ImgHapus);

        }
    }
}
