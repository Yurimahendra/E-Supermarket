package com.example.e_supermarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragmentPenjual#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragmentPenjual extends Fragment {

    private RecyclerView recyclerView;
    private List<DataProduk> dataProdukList;
    private AdapterProdukPenjual adapterProdukPenjual;
    private FirebaseFirestore db;


    private FirebaseStorage storage;
    private StorageReference storageReference;

    public Uri imageUri;
    ImageView imageProduk;

    //private ImageView editProduk;
    //private ImageView hapusProduk;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragmentPenjual() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragmentPenjual.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragmentPenjual newInstance(String param1, String param2) {
        HomeFragmentPenjual fragment = new HomeFragmentPenjual();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_penjual, container, false);

       // editProduk = (ImageView)view.findViewById(R.id.ImgEdit);
        //hapusProduk = (ImageView)view.findViewById(R.id.ImgHapus);



        recyclerView = (RecyclerView)view.findViewById(R.id.recItem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // FirebaseDatabase.getInstance().getReference().child("data_produk");
        //storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();
        dataProdukList = new ArrayList<>();
        adapterProdukPenjual = new AdapterProdukPenjual(HomeFragmentPenjual.this, dataProdukList);

        recyclerView.setAdapter(adapterProdukPenjual);
        EventChangeListener();
        /**imageProduk = (ImageView) view.findViewById(R.id.ImgProduk);
        imageProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });**/

        return view;



    }



    /** private void pilihGambar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            imageProduk.setImageURI(imageUri);
            uploadGambar();
        }
    }

    private void uploadGambar() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Upload gambar....");
        pd.show();

        final String id = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + id);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(getActivity().findViewById(R.id.dialogplus_content_container), "image uploaded", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "gagal upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Proses" + (int) progressPercent + "%");
                    }
                });

    }**/

    private void EventChangeListener() {


        db.collection("data_produk").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        dataProdukList.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            DataProduk dataProduk = new DataProduk(snapshot.getString("id"), snapshot.getString("nama_barang"), snapshot.getString("merk"), snapshot.getLong("harga"), snapshot.getLong("stok"), snapshot.getString("satuan"), snapshot.getString("gambar"), snapshot.getString("deskripsi"));
                            dataProdukList.add(dataProduk);
                        }
                        adapterProdukPenjual.notifyDataSetChanged();
                         }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

