package com.example.e_supermarket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProdukPenjualHU extends RecyclerView.Adapter<AdapterProdukPenjualHU.MyViewHolder>{
    private HalamanUtamaPenjualActivity halamanUtamaPenjualActivity;
    private List<DataProduk> ProdukList;
    private String n_satuan[] = {"Kg", "Gr", "Pcs", "Lusin", "Kodi", "Gross", "Pack"};
    int id_satuan;

    public AdapterProdukPenjualHU(HalamanUtamaPenjualActivity halamanUtamaPenjualActivity, List<DataProduk> produkList) {
        this.halamanUtamaPenjualActivity = halamanUtamaPenjualActivity;
        this.ProdukList = produkList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(halamanUtamaPenjualActivity).inflate(R.layout.item_produk_penjual, parent, false);

        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Id.setText(String.valueOf(ProdukList.get(position).getNama_barang()));
        holder.Nama_Barang.setText(ProdukList.get(position).getNama_barang());
        holder.Merk.setText(ProdukList.get(position).getMerk());
        holder.Harga.setText(String.valueOf(ProdukList.get(position).getHarga()));
        holder.Stok.setText(String.valueOf(ProdukList.get(position).getStok()));
        holder.Satuan.setText(ProdukList.get(position).getSatuan());
        Glide.with(holder.imageProduk.getContext()).load(ProdukList.get(position).getGambar()).into(holder.imageProduk);
        holder.Deskripsi.setText(ProdukList.get(position).getDeskripsi());

        holder.editProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        int id = ProdukList.get(position).getId();
                        String nama_barang = nama_barang1.getText().toString().trim();
                        String merk = merk1.getText().toString().trim();
                        int harga = Integer.parseInt(harga1.getText().toString().trim());
                        int stok = Integer.parseInt(stok1.getText().toString().trim());
                        String satuan = satuan1.getSelectedItem().toString().trim();
                        String deskripsi = deskripsi1.getText().toString().trim();

                        //updatetofirestore(id, nama_barang, merk, harga, stok, satuan, deskripsi );

                        if ( nama_barang.isEmpty()) {
                            Toast.makeText(halamanUtamaPenjualActivity.getApplicationContext(), "NAMA BARANG TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        } else if (merk.isEmpty()) {
                            Toast.makeText(halamanUtamaPenjualActivity.getApplicationContext(), "MERK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        } else if (harga <= 0  ) {
                            Toast.makeText(halamanUtamaPenjualActivity.getApplicationContext(), "HARGA TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        } else if (stok <= 0  ) {
                            Toast.makeText(halamanUtamaPenjualActivity.getApplicationContext(), "STOK TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        } else if (satuan.isEmpty()) {
                            Toast.makeText(halamanUtamaPenjualActivity.getApplicationContext(), "SATUAN TIDAK BOLEH KOSONG", Toast.LENGTH_SHORT).show();
                        }else {

                            //updatetofirestore(id, nama_barang, merk, harga, stok, satuan, deskripsi );
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
                        /**db.collection("data_produk")
                         .document(String.valueOf(ProdukList.get(position).getId()))
                         .delete();**/
                        Toast.makeText(halamanUtamaPenjualActivity.getApplicationContext(), "Data Berhasil Dihapus !", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return ProdukList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Id;
        TextView Nama_Barang;
        TextView Merk;
        TextView Harga;
        TextView Stok;
        TextView Satuan;
        TextView Deskripsi;

        ImageView editProduk;
        ImageView hapusProduk;
        CircleImageView imageProduk;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Id = itemView.findViewById(R.id.tvIdBarang);
            Nama_Barang = itemView.findViewById(R.id.tvNamaBarang);
            Merk = itemView.findViewById(R.id.tvMerk);
            Harga = itemView.findViewById(R.id.tvHarga);
            Stok = itemView.findViewById(R.id.tvStok);
            Satuan = itemView.findViewById(R.id.tvSatuan);
            imageProduk = (CircleImageView) itemView.findViewById(R.id.ImgProdukView);
            Deskripsi = itemView.findViewById(R.id.tvDeskripsi);

            editProduk = (ImageView) itemView.findViewById(R.id.ImgEdit);
            hapusProduk = (ImageView) itemView.findViewById(R.id.ImgHapus);


        }

    }
}
