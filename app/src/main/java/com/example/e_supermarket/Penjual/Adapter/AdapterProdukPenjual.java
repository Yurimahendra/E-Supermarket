package com.example.e_supermarket.Penjual.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Penjual.Activity.Form_Edit_Produk_Activity;
import com.example.e_supermarket.Penjual.Fragment.HomeFragmentPenjual;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.Model.DataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataProduk;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AdapterProdukPenjual extends RecyclerView.Adapter<AdapterProdukPenjual.MyViewHolder> implements onActivityResult {

    private HomeFragmentPenjual homeFragmentPenjual;
    private List<DataProduk> ProdukList;
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference myRef = database.getReference();
    int id_satuan;
    //FirebaseFirestore db;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String mediaPath;
    private String postPath;
    public Uri imageUri;

    CircleImageView EditImgProduk;
    //String id;
    private String n_satuan[] = {"Kg", "Gr", "Pcs", "Lusin", "Kodi", "Gross", "Pack"};


    public AdapterProdukPenjual(HomeFragmentPenjual homeFragmentPenjual, List<DataProduk> dataProdukList) {
        this.homeFragmentPenjual = homeFragmentPenjual;
        this.ProdukList = dataProdukList;

    }


    @NonNull
    @Override
    public AdapterProdukPenjual.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(homeFragmentPenjual.getContext()).inflate(R.layout.item_produk_penjual, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Id.setText(String.valueOf(ProdukList.get(position).getId()));
        holder.Nama_Barang.setText(ProdukList.get(position).getNama_barang());
        holder.Merk.setText(ProdukList.get(position).getMerk());
        holder.Harga.setText(ProdukList.get(position).getHarga());
        holder.Stok.setText(String.valueOf(ProdukList.get(position).getStok()));
        holder.Satuan.setText(ProdukList.get(position).getSatuan());
        Glide.with(holder.imageProduk.getContext())
                .load(RetroServer.imageURL + ProdukList.get(position).getGambar()).into(holder.imageProduk);
        holder.Deskripsi.setText(ProdukList.get(position).getDeskripsi());


        holder.editProduk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                 DataProduk item = ProdukList.get(position);
                 Bundle bundle = new Bundle();
                 bundle.putInt("id", item.getId());
                 bundle.putString("nama_barang", item.getNama_barang());
                 bundle.putString("merk", item.getMerk());
                 bundle.putString("harga", item.getHarga());
                 bundle.putInt("stok", item.getStok());
                 bundle.putString("satuan", item.getSatuan());
                 bundle.putString("gambar", RetroServer.imageURL + item.getGambar());
                 bundle.putString("deskripsi", item.getDeskripsi());
                 Intent intent = new Intent(homeFragmentPenjual.getActivity(), Form_Edit_Produk_Activity.class);
                 intent.putExtras(bundle);
                 homeFragmentPenjual.startActivity(intent);

                /*DialogPlus dialogPlus = DialogPlus.newDialog(holder.Id.getContext())
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
                EditImgProduk = myview.findViewById(R.id.GambarProdukEdit);
                EditImgProduk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pilihgambar();
                    }

                });

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
                //EditImgProduk.setImageURI(imageUri);

                Glide.with(myview.findViewById(R.id.GambarProdukEdit).getContext())
                        .load(imageUri).into(EditImgProduk);


                Glide.with(myview.findViewById(R.id.GambarProdukEdit).getContext())
                        .load(RetroServer.imageURL + ProdukList.get(position).getGambar()).into(EditImgProduk);

                deskripsi1.setText(ProdukList.get(position).getDeskripsi());


                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int idProduk = ProdukList.get(position).getId();
                        String nama_barang = nama_barang1.getText().toString().trim();
                        String merk = merk1.getText().toString().trim();
                        int harga = Integer.parseInt(harga1.getText().toString().trim());
                        int stok = Integer.parseInt(stok1.getText().toString().trim());
                        String satuan = satuan1.getSelectedItem().toString().trim();
                        String deskripsi = deskripsi1.getText().toString().trim();


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
                            dialogPlus.dismiss();
                        }


                    }
                });
                dialogPlus.show();*/
            }
        });

        holder.hapusProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idProduk = ProdukList.get(position).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Id.getContext());
                builder.setTitle("Apakah yakin ingin dihapus ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ApiRequestDataProduk haDataProduk = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                        Call<ResponseDataProduk> hapusDataProduk = haDataProduk.hapusData(idProduk);
                        hapusDataProduk.enqueue(new Callback<ResponseDataProduk>() {
                            @Override
                            public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {

                                try {
                                    int kode = response.body().getKode();
                                    String pesan = response.body().getPesan();
                                    if (kode == 200){
                                        Toast.makeText(homeFragmentPenjual.getContext(), "Pesan :"+pesan, Toast.LENGTH_SHORT).show();
                                    }
                                }catch (NullPointerException nullPointerException){
                                    Toast.makeText(homeFragmentPenjual.getContext(), "Data Gagal Terhapus "+nullPointerException.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDataProduk> call, Throwable t) {
                                Toast.makeText(homeFragmentPenjual.getContext(), "Gagal Menghubungi Server "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
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

    private void pilihgambar(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        homeFragmentPenjual.startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1){
                if (data!=null){
                    imageUri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    EditImgProduk.setImageURI(imageUri);
                    cursor.close();
                    postPath = mediaPath;
                }
            }
        }

    }
    private void requestPermission(){
        //PbSimpanData.setVisibility(View.VISIBLE);
        //btnAddData.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 786);
        }else {
            //insertdataproduk();
            //PbSimpanData.setVisibility(View.GONE);
            //btnAddData.setVisibility(View.VISIBLE);
        }

    }

    private void requestPermissions(String[] strings, int i) {
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 786 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //insertdataproduk();
            requestPermission();
        }
    }

    private ContentProvider getContentResolver() {
        return null;
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

       // CircleImageView EditImgProduk1;


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

           // EditImgProduk1 = (CircleImageView) itemView.findViewById(R.id.GambarProdukEdit);
            editProduk = (ImageView) itemView.findViewById(R.id.ImgEdit);
            hapusProduk = (ImageView) itemView.findViewById(R.id.ImgHapus);


        }
    }

}
