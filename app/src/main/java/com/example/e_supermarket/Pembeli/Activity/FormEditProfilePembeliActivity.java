package com.example.e_supermarket.Pembeli.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
import com.example.e_supermarket.Penjual.Activity.FormEditProfilePenjualActivity;
import com.example.e_supermarket.Penjual.Activity.HalamanUtamaPenjualActivity;
import com.example.e_supermarket.Penjual.Interface.ApiRequestDataProduk;
import com.example.e_supermarket.Penjual.ResponseModel.ResponseDataPenjual;
import com.example.e_supermarket.Penjual.Server.RetroServer;
import com.example.e_supermarket.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormEditProfilePembeliActivity extends AppCompatActivity implements onRequestPermissionResultPmbl {
    EditText NikPmbl;
    EditText NamaPmbl;
    Spinner JkPmbl;
    EditText NoPonsPmbl;
    EditText TeLaPmbl;
    EditText TalaPmbl;
    EditText AlamatPmbl;
    CircleImageView eFtoPmbl;
    Button BtnUpdateProfilePmbl;
    ProgressBar PbUpdteProfilPmbl;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

    int index_jk;
    private String jk[] = {"Pria", "Wanita"};
    private int uIdPmbl;
    private long uNikPmbl;
    private String uNamaPmbl;
    private String uJkPmbl;
    private String uNoponsPmbl;
    private String uTelaPmbl;
    private String uTalaPmbl;
    private String uAlamatPmbl;
    private String uFtoPmbl;

    private String mediaPathPmbl;
    private String postPathPmbl;
    public Uri imageUriPmbl;


    long nik_Pmbl;
    String nik_PmblS;
    int lenNik_Pmbl;
    String nama_Pmbl;
    String jk_Pmbl;
    String nopons_Pmbl;
    String tela_Pmbl;
    String tala_Pmbl;
    String alamat_Pmbl;
    MultipartBody.Part foto_Pmbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit_profile_pembeli);

        NikPmbl = findViewById(R.id.EdtNikPmbl);
        NamaPmbl = findViewById(R.id.EdtNamaPmbl);
        JkPmbl = findViewById(R.id.EdtSpJkPmbl);
        NoPonsPmbl = findViewById(R.id.EdtNopPmbl);
        TeLaPmbl = findViewById(R.id.EdtTeLaPmbl);
        TalaPmbl = findViewById(R.id.EdtTalaPmbl);
        AlamatPmbl = findViewById(R.id.EdtAlamatPmbl);
        eFtoPmbl = findViewById(R.id.EdtImgProfilePmbl);
        eFtoPmbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }
        });

        TalaPmbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        BtnUpdateProfilePmbl = findViewById(R.id.btnSimpanEditDataPmbl);
        BtnUpdateProfilePmbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfilPmbl();
            }
        });

        PbUpdteProfilPmbl = findViewById(R.id.progressProfilePmbl);

        Bundle bundle = getIntent().getExtras();
        uIdPmbl = bundle.getInt("id");
        uNikPmbl = bundle.getLong("nik");
        uNamaPmbl = bundle.getString("nama_penjual");
        uJkPmbl = bundle.getString("jenis_kelamin");
        uNoponsPmbl = bundle.getString("no_ponsel");
        uTelaPmbl = bundle.getString("tempat_lahir");
        uTalaPmbl = bundle.getString("tanggal_lahir");
        uAlamatPmbl = bundle.getString("alamat");
        uFtoPmbl = bundle.getString("gambar");

        NikPmbl.setText(""+uNikPmbl);
        NamaPmbl.setText(uNamaPmbl);
        if (uJkPmbl.equals(jk[0])) index_jk = 0;
        else if (uJkPmbl.equals(jk[1])) index_jk = 1;
        JkPmbl.setSelection(index_jk);
        NoPonsPmbl.setText(uNoponsPmbl);
        TeLaPmbl.setText(uTelaPmbl);
        TalaPmbl.setText(uTalaPmbl);
        AlamatPmbl.setText(uAlamatPmbl);
        /*Glide.with(eFtoPmbl.getContext())
                .load(uFtoPmbl).into(eFtoPmbl);*/
    }

    private void UpdateProfilPmbl() {
        PbUpdteProfilPmbl.setVisibility(View.VISIBLE);
        BtnUpdateProfilePmbl.setVisibility(View.INVISIBLE);
        try {
            nik_Pmbl = Long.parseLong(NikPmbl.getText().toString().trim());
            nama_Pmbl = NamaPmbl.getText().toString().trim();
            jk_Pmbl = JkPmbl.getSelectedItem().toString().trim();
            nopons_Pmbl = NoPonsPmbl.getText().toString().trim();
            tela_Pmbl = TeLaPmbl.getText().toString().trim();
            tala_Pmbl = TalaPmbl.getText().toString().trim();
            alamat_Pmbl = AlamatPmbl.getText().toString().trim();
            if (mediaPathPmbl != null){
                File imgFile = new File(mediaPathPmbl);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
                foto_Pmbl = MultipartBody.Part.createFormData("gambar", imgFile.getName(), reqBody);
            }else {
                foto_Pmbl = null;
            }

            nik_PmblS = NikPmbl.getText().toString().trim();
            lenNik_Pmbl = nik_PmblS.length();

            if (nik_Pmbl <= 0){
                NikPmbl.setError("NIK TIDAK BOLEH KOSONG");
                PbUpdteProfilPmbl.setVisibility(View.GONE);
                BtnUpdateProfilePmbl.setVisibility(View.VISIBLE);
            } else if (lenNik_Pmbl < 16) {
                NikPmbl.setError("JUMLAH NIK TIDAK SESUAI");
                PbUpdteProfilPmbl.setVisibility(View.GONE);
                BtnUpdateProfilePmbl.setVisibility(View.VISIBLE);
            } else if (nama_Pmbl.equals("")) {
                NamaPmbl.setError("NAMA TIDAK BOLEH KOSONG");
                PbUpdteProfilPmbl.setVisibility(View.GONE);
                BtnUpdateProfilePmbl.setVisibility(View.VISIBLE);
            } else if (nopons_Pmbl.equals("")) {
                NoPonsPmbl.setError("NOMOR PONSEL TIDAK BOLEH KOSONG");
                PbUpdteProfilPmbl.setVisibility(View.GONE);
                BtnUpdateProfilePmbl.setVisibility(View.VISIBLE);
            } else if (tela_Pmbl.equals("")) {
                TeLaPmbl.setError("TEMPAT LAHIR TIDAK BOLEH KOSONG");
                PbUpdteProfilPmbl.setVisibility(View.GONE);
                BtnUpdateProfilePmbl.setVisibility(View.VISIBLE);
            }else if (alamat_Pmbl.equals("")){
                AlamatPmbl.setError("ALAMAT TIDAK BOLEH KOSONG ");
                PbUpdteProfilPmbl.setVisibility(View.GONE);
                BtnUpdateProfilePmbl.setVisibility(View.VISIBLE);
            }else {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    int id = uIdPmbl;
                    ApiRequestPembeli requestDataProfilPmbl = RetroServer.konekRetrofit().create(ApiRequestPembeli.class);
                    Call<ResponseDataPembeli> UpdateProfilePmbl = requestDataProfilPmbl.UpdateProfilePembeli(
                            id,
                            "PUT",
                            nik_Pmbl,
                            RequestBody.create(MediaType.parse("text/plain"), nama_Pmbl),
                            RequestBody.create(MediaType.parse("text/plain"), jk_Pmbl),
                            RequestBody.create(MediaType.parse("text/plain"), alamat_Pmbl),
                            RequestBody.create(MediaType.parse("text/plain"), tela_Pmbl),
                            RequestBody.create(MediaType.parse("text/plain"), tala_Pmbl),
                            RequestBody.create(MediaType.parse("text/plain"), nopons_Pmbl),
                            foto_Pmbl

                    );
                    UpdateProfilePmbl.enqueue(new Callback<ResponseDataPembeli>() {
                        @Override
                        public void onResponse(Call<ResponseDataPembeli> call, Response<ResponseDataPembeli> response) {
                            if( response.isSuccessful()) {
                                int kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                if (kode == 200){
                                    startActivity(new Intent(FormEditProfilePembeliActivity.this, HalamanUtamaPembeliActivity.class));
                                    Toast.makeText(FormEditProfilePembeliActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();

                                }

                            }else {
                                Toast.makeText(FormEditProfilePembeliActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                            PbUpdteProfilPmbl.setVisibility(View.GONE);
                            BtnUpdateProfilePmbl.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onFailure(Call<ResponseDataPembeli> call, Throwable t) {
                            Toast.makeText(FormEditProfilePembeliActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                            PbUpdteProfilPmbl.setVisibility(View.GONE);
                            BtnUpdateProfilePmbl.setVisibility(View.VISIBLE);
                            //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                        }

                    });
                }
            }
        }catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Produk Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbUpdteProfilPmbl.setVisibility(View.GONE);
            BtnUpdateProfilePmbl.setVisibility(View.VISIBLE);
        }
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TalaPmbl.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void pilihgambar() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1){
                if (data!=null){
                    imageUriPmbl = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUriPmbl, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathPmbl = cursor.getString(columnIndex);
                    eFtoPmbl.setImageURI(imageUriPmbl);
                    cursor.close();
                    postPathPmbl = mediaPathPmbl;
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
            UpdateProfilPmbl();
            //PbSimpanData.setVisibility(View.GONE);
            //btnAddData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestPermissionResultPmbl(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 786 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            UpdateProfilPmbl();
        }
    }

}