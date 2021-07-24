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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.w3c.dom.Text;

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
    FirebaseFirestore db;
    //String id;
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
        db = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Nama_Barang.setText(ProdukList.get(position).getNama_barang());
        holder.Merk.setText(ProdukList.get(position).getMerk());
        holder.Harga.setText(String.valueOf(ProdukList.get(position).getHarga()));
        holder.Stok.setText(String.valueOf(ProdukList.get(position).getStok()));
        holder.Satuan.setText(ProdukList.get(position).getSatuan());
        holder.Deskripsi.setText(ProdukList.get(position).getDeskripsi());


        db = FirebaseFirestore.getInstance();
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
                EditText nama_barang1 = myview.findViewById(R.id.UpdtNaBa);
                EditText merk1 = myview.findViewById(R.id.UpdtMerk);
                EditText harga1 = myview.findViewById(R.id.UpdtHarga);
                EditText stok1 = myview.findViewById(R.id.UpdtStok);
                Spinner satuan1 = myview.findViewById(R.id.UpdtSpSatuan);
                EditText deskripsi1 = myview.findViewById(R.id.UpdtDeskripsi);
                Button btnupdate = myview.findViewById(R.id.btnUpdtData);


                nama_barang1.setText(ProdukList.get(position).getNama_barang());
                merk1.setText(ProdukList.get(position).getMerk());
                harga1.setText(String.valueOf(ProdukList.get(position).getHarga()));
                stok1.setText(String.valueOf(ProdukList.get(position).getStok()));

                if (ProdukList.get(position).getSatuan().equals("Kg")) id_satuan = 0;
                else if (ProdukList.get(position).getSatuan().equals("Gr")) id_satuan = 1;
                else if (ProdukList.get(position).getSatuan().equals("Pcs")) id_satuan = 2;
                else if (ProdukList.get(position).getSatuan().equals("Lusin")) id_satuan = 3;
                else if (ProdukList.get(position).getSatuan().equals("Kodi")) id_satuan = 4;
                else if (ProdukList.get(position).getSatuan().equals("Gross")) id_satuan = 5;
                else if (ProdukList.get(position).getSatuan().equals("Pack")) id_satuan = 6;
                satuan1.setSelection(id_satuan);
                deskripsi1.setText(ProdukList.get(position).getDeskripsi());


                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = ProdukList.get(position).getId();
                        String nama_barang = nama_barang1.getText().toString().trim();
                        String merk = merk1.getText().toString().trim();
                        long harga = Long.parseLong(harga1.getText().toString().trim());
                        long stok = Long.parseLong(stok1.getText().toString().trim());
                        String satuan = satuan1.getSelectedItem().toString().trim();
                        String deskripsi = deskripsi1.getText().toString().trim();

                        //updatetofirestore(id, nama_barang, merk, harga, stok, satuan, deskripsi );

                        if ( nama_barang.isEmpty()) {
                            Toast.makeText(homeFragmentPenjual.getContext(), "NAMA BARANG TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        } else if (merk.isEmpty()) {
                            Toast.makeText(homeFragmentPenjual.getContext(), "MERK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        } else if (harga <= 0  ) {
                            Toast.makeText(homeFragmentPenjual.getContext(), "HARGA TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        } else if (stok <= 0  ) {
                            Toast.makeText(homeFragmentPenjual.getContext(), "STOK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        } else if (satuan.isEmpty()) {
                            Toast.makeText(homeFragmentPenjual.getContext(), "SATUAN TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        }else {
                            //DataProduk dataProduk = new DataProduk(id, nama_barang, merk, harga, stok, satuan, deskripsi);
                            updatetofirestore(id, nama_barang, merk, harga, stok, satuan, deskripsi );
                            dialogPlus.dismiss();
                        }


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
                        db.collection("data_produk")
                                .document(ProdukList.get(position).getId())
                                .delete();
                        Toast.makeText(homeFragmentPenjual.getContext(), "Data Berhasil Dihapus !", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });
    }


    private void updatetofirestore(String id, String  nama_barang, String merk, long harga, long stok, String satuan, String deskripsi) {
       /** Map<String, Object> map = new HashMap<>();
        map.put("nama_barang", nama_barang);
        map.put("merk", merk);
        map.put("harga", harga);
        map.put("stok", stok);
        map.put("satuan", satuan);
        map.put("deskripsi", deskripsi);**/

        db.collection("data_produk")
                .document(id)
                .update("nama_barang", nama_barang, "merk", merk, "harga", harga, "stok", stok, "satuan", satuan, "deskripsi", deskripsi)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(homeFragmentPenjual.getContext(), "Update Berhasil !", Toast.LENGTH_SHORT).show();
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
