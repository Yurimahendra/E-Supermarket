package com.example.e_supermarket.Penjual.Activity;

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

public class FormEditProfilePenjualActivity extends AppCompatActivity implements onRequestPermissionResultPnjl {
    EditText NikPnjl;
    EditText NamaPnjl;
    Spinner JkPnjl;
    EditText NoPonsPnjl;
    EditText TeLaPnjl;
    EditText TalaPnjl;
    EditText AlamatPnjl;
    EditText NatoPnjl;
    CircleImageView eFtoPnjl;
    Button BtnUpdateProfilePenjual;
    ProgressBar PbUpdteProfilPnjl;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

    int index_jk;
    private String jk[] = {"Pria", "Wanita"};
    private int uIdPnjl;
    private long uNikPnjl;
    private String uNamaPnjl;
    private String uJkPnjl;
    private String uNoponsPnjl;
    private String uTelaPnjl;
    private String uTalaPnjl;
    private String uAlamatPnjl;
    private String uNatoPnjl;
    private String uFtoPnjl;

    private String mediaPathPnjl;
    private String postPathPnjl;
    public Uri imageUriPnjl;


    long nik_pnjl;
    String nik_PnjlS;
    int lenNik_Pnjl;
    String nama_pnjl;
    String jk_pnjl;
    String nopons_pnjl;
    String tela_pnjl;
    String tala_pnjl;
    String alamat_pnjl;
    String nato_pnjl;
    MultipartBody.Part foto_pnjl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit_profile_penjual);

        NikPnjl = findViewById(R.id.EdtNikPnjl);
        NamaPnjl = findViewById(R.id.EdtNamaPnjl);
        JkPnjl = findViewById(R.id.EdtSpJkPnjl);
        NoPonsPnjl = findViewById(R.id.EdtNopPnjl);
        TeLaPnjl = findViewById(R.id.EdtTeLaPnjl);
        TalaPnjl = findViewById(R.id.EdtTalaPnjl);
        AlamatPnjl = findViewById(R.id.EdtAlamatPnjl);
        NatoPnjl = findViewById(R.id.EdtNatoPnjl);
        eFtoPnjl = findViewById(R.id.EdtImgProfilePnjl);
        eFtoPnjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }
        });

        TalaPnjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        BtnUpdateProfilePenjual = findViewById(R.id.btnSimpanEdtDataPnjl);
        BtnUpdateProfilePenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfilPnjl();
            }
        });

        PbUpdteProfilPnjl = findViewById(R.id.progressProfilePnjl);

        Bundle bundle = getIntent().getExtras();
        uIdPnjl = bundle.getInt("id");
        uNikPnjl = bundle.getLong("nik");
        uNamaPnjl = bundle.getString("nama_penjual");
        uJkPnjl = bundle.getString("jenis_kelamin");
        uNoponsPnjl = bundle.getString("no_ponsel");
        uTelaPnjl = bundle.getString("tempat_lahir");
        uTalaPnjl = bundle.getString("tanggal_lahir");
        uAlamatPnjl = bundle.getString("alamat");
        uNatoPnjl = bundle.getString("nama_toko");
        uFtoPnjl = bundle.getString("gambar");

        NikPnjl.setText(""+uNikPnjl);
        NamaPnjl.setText(uNamaPnjl);
        if (uJkPnjl.equals(jk[0])) index_jk = 0;
        else if (uJkPnjl.equals(jk[1])) index_jk = 1;
        JkPnjl.setSelection(index_jk);
        NoPonsPnjl.setText(uNoponsPnjl);
        TeLaPnjl.setText(uTelaPnjl);
        TalaPnjl.setText(uTalaPnjl);
        AlamatPnjl.setText(uAlamatPnjl);
        NatoPnjl.setText(uNatoPnjl);
        /*if (uFtoPnjl == null){
            eFtoPnjl.setImageURI(null);
        }else {
            Glide.with(eFtoPnjl.getContext())
                    .load(uFtoPnjl).into(eFtoPnjl);
        }*/

    }

    private void pilihgambar(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1){
                if (data!=null){
                    imageUriPnjl = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUriPnjl, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathPnjl = cursor.getString(columnIndex);
                    eFtoPnjl.setImageURI(imageUriPnjl);
                    cursor.close();
                    postPathPnjl = mediaPathPnjl;
                }
            }
        }
    }

    private void UpdateProfilPnjl(){
        PbUpdteProfilPnjl.setVisibility(View.VISIBLE);
        BtnUpdateProfilePenjual.setVisibility(View.INVISIBLE);
        try {
            nik_pnjl = Long.parseLong(NikPnjl.getText().toString().trim());
            nama_pnjl = NamaPnjl.getText().toString().trim();
            jk_pnjl = JkPnjl.getSelectedItem().toString().trim();
            nopons_pnjl = NoPonsPnjl.getText().toString().trim();
            tela_pnjl = TeLaPnjl.getText().toString().trim();
            tala_pnjl = TalaPnjl.getText().toString().trim();
            alamat_pnjl = AlamatPnjl.getText().toString().trim();
            nato_pnjl = NatoPnjl.getText().toString().trim();
            if (mediaPathPnjl != null){
                File imgFile = new File(mediaPathPnjl);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
                foto_pnjl = MultipartBody.Part.createFormData("gambar", imgFile.getName(), reqBody);
            }else {
                foto_pnjl = null;
            }

            nik_PnjlS = NikPnjl.getText().toString().trim();
            lenNik_Pnjl = nik_PnjlS.length();

            if (nik_pnjl <= 0){
                NikPnjl.setError("NIK TIDAK BOLEH KOSONG");
                PbUpdteProfilPnjl.setVisibility(View.GONE);
                BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
            } else if (lenNik_Pnjl < 16) {
                NikPnjl.setError("JUMLAH NIK TIDAK SESUAI");
                PbUpdteProfilPnjl.setVisibility(View.GONE);
                BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
            } else if (nama_pnjl.equals("")) {
                NamaPnjl.setError("NAMA TIDAK BOLEH KOSONG");
                PbUpdteProfilPnjl.setVisibility(View.GONE);
                BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
            } else if (nopons_pnjl.equals("")) {
                NoPonsPnjl.setError("NOMOR PONSEL TIDAK BOLEH KOSONG");
                PbUpdteProfilPnjl.setVisibility(View.GONE);
                BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
            } else if (tela_pnjl.equals("")) {
                TeLaPnjl.setError("TEMPAT LAHIR TIDAK BOLEH KOSONG");
                PbUpdteProfilPnjl.setVisibility(View.GONE);
                BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
            }else if (alamat_pnjl.equals("")){
                AlamatPnjl.setError("ALAMAT TIDAK BOLEH KOSONG ");
                PbUpdteProfilPnjl.setVisibility(View.GONE);
                BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
            }else if (nato_pnjl.equals("")){
                NatoPnjl.setError("NAMA TOKO TIDAK BOLEH KOSONG");
                PbUpdteProfilPnjl.setVisibility(View.GONE);
                BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
            }else {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    int id = uIdPnjl;
                    ApiRequestDataProduk requestDataProfilPnjl = RetroServer.konekRetrofit().create(ApiRequestDataProduk.class);
                    Call<ResponseDataPenjual> UpdateProfilePnjl = requestDataProfilPnjl.UpdateProfilePenjual(
                            id,
                            "PUT",
                            nik_pnjl,
                            RequestBody.create(MediaType.parse("text/plain"), nama_pnjl),
                            RequestBody.create(MediaType.parse("text/plain"), jk_pnjl),
                            RequestBody.create(MediaType.parse("text/plain"), alamat_pnjl),
                            RequestBody.create(MediaType.parse("text/plain"), tela_pnjl),
                            RequestBody.create(MediaType.parse("text/plain"), tala_pnjl),
                            RequestBody.create(MediaType.parse("text/plain"), nopons_pnjl),
                            RequestBody.create(MediaType.parse("text/plain"), nato_pnjl),
                            foto_pnjl

                    );
                    UpdateProfilePnjl.enqueue(new Callback<ResponseDataPenjual>() {
                        @Override
                        public void onResponse(Call<ResponseDataPenjual> call, Response<ResponseDataPenjual> response) {
                            if( response.isSuccessful()) {
                                //int kode = response.body().getKode();
                                //String pesan = response.body().getPesan();
                                startActivity(new Intent(FormEditProfilePenjualActivity.this, HalamanUtamaPenjualActivity.class));
                                Toast.makeText(FormEditProfilePenjualActivity.this, "berhasil update", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(FormEditProfilePenjualActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                            PbUpdteProfilPnjl.setVisibility(View.GONE);
                            BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onFailure(Call<ResponseDataPenjual> call, Throwable t) {
                            Toast.makeText(FormEditProfilePenjualActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                            PbUpdteProfilPnjl.setVisibility(View.GONE);
                            BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
                            //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                        }

                    });
                }
            }
        }catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Produk Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbUpdteProfilPnjl.setVisibility(View.GONE);
            BtnUpdateProfilePenjual.setVisibility(View.VISIBLE);
        }
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TalaPnjl.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void requestPermission(){
        //PbSimpanData.setVisibility(View.VISIBLE);
        //btnAddData.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 786);
        }else {
            UpdateProfilPnjl();
            //PbSimpanData.setVisibility(View.GONE);
            //btnAddData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestPermissionResultPnjl(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 786 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            UpdateProfilPnjl();
        }
    }


}