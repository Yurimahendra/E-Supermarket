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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragmentPenjual#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragmentPenjual extends Fragment {

    private RecyclerView recyclerView;
    private List<DataProduk> dataProdukList = new ArrayList<>();
    private AdapterProdukPenjual adapterProdukPenjual;
    private SwipeRefreshLayout srlDataProduk;
    private ProgressBar pbDataProduk;
   // private FirebaseFirestore db;


    //private FirebaseStorage storage;
    //private StorageReference storageReference;

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

        srlDataProduk = view.findViewById(R.id.sw_dataproduk);
        pbDataProduk = view.findViewById(R.id.pb_dataproduk);

        recyclerView = (RecyclerView)view.findViewById(R.id.recItem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
       // retrieveData();
        srlDataProduk.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataProduk.setRefreshing(true);
                retrieveData();
                srlDataProduk.setRefreshing(false);
            }
        });

        return view;



    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveData();
    }


    public void retrieveData(){
        pbDataProduk.setVisibility(View.VISIBLE);
        ApiRequestDataProduk requestDataProduk = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
        Call<ResponseDataProduk> tampilData = requestDataProduk.RetrieveDataProduk();

        tampilData.enqueue(new Callback<ResponseDataProduk>() {
            @Override
            public void onResponse(Call<ResponseDataProduk> call, Response<ResponseDataProduk> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                //Toast.makeText(getContext(), "kode : "+kode+" | pesan : "+pesan, Toast.LENGTH_SHORT).show();

                dataProdukList = response.body().getData();

                adapterProdukPenjual = new AdapterProdukPenjual(HomeFragmentPenjual.this, dataProdukList);
                recyclerView.setAdapter(adapterProdukPenjual);
                adapterProdukPenjual.notifyDataSetChanged();
                pbDataProduk.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseDataProduk> call, Throwable t) {
                Toast.makeText(getContext(), "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbDataProduk.setVisibility(View.INVISIBLE);
            }
        });
    }


}

