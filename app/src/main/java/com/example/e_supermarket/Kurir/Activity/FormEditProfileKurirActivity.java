package com.example.e_supermarket.Kurir.Activity;

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
import com.example.e_supermarket.Kurir.Interface.ApiRequestDataKurir;
import com.example.e_supermarket.Kurir.ResponseModel.ResponseDataKurir;
import com.example.e_supermarket.Pembeli.Activity.FormEditProfilePembeliActivity;
import com.example.e_supermarket.Pembeli.Activity.HalamanUtamaPembeliActivity;
import com.example.e_supermarket.Pembeli.Interface.ApiRequestPembeli;
import com.example.e_supermarket.Pembeli.ResponseModelPembeli.ResponseDataPembeli;
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

public class FormEditProfileKurirActivity extends AppCompatActivity implements onRequestPermissionResultKurir {
    EditText NikKurir;
    EditText NamaKurir;
    Spinner JkKurir;
    EditText NoPonsKurir;
    EditText TeLaKurir;
    EditText TalaKurir;
    EditText AlamatKurir;
    CircleImageView eFtoKurir;
    Button BtnUpdateProfileKurir;
    ProgressBar PbUpdteProfilKurir;

    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

    int index_jk;
    private String jk[] = {"Pria", "Wanita"};
    private int uIdKurir;
    private long uNikKurir;
    private String uNamaKurir;
    private String uJkKurir;
    private String uNoponsKurir;
    private String uTelaKurir;
    private String uTalaKurir;
    private String uAlamatKurir;
    private String uFtoKurir;

    private String mediaPathKurir;
    private String postPathKurir;
    public Uri imageUriKurir;


    long nik_Kurir;
    String nik_KurirS;
    int lenNik_Kurir;
    String nama_Kurir;
    String jk_Kurir;
    String nopons_Kurir;
    String tela_Kurir;
    String tala_Kurir;
    String alamat_Kurir;
    MultipartBody.Part foto_Kurir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit_profile_kurir);

        NikKurir = findViewById(R.id.EdtNikKurir);
        NamaKurir = findViewById(R.id.EdtNamaKurir);
        JkKurir = findViewById(R.id.EdtSpJkKurir);
        NoPonsKurir = findViewById(R.id.EdtNopKurir);
        TeLaKurir = findViewById(R.id.EdtTeLaKurir);
        TalaKurir = findViewById(R.id.EdtTalaKurir);
        AlamatKurir = findViewById(R.id.EdtAlamatKurir);
        eFtoKurir = findViewById(R.id.EdtImgProfileKurir);
        eFtoKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }
        });

        TalaKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        BtnUpdateProfileKurir = findViewById(R.id.btnSimpanEditDataKurir);
        BtnUpdateProfileKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfilKurir();
            }
        });

        PbUpdteProfilKurir = findViewById(R.id.progressProfileKurir);

        Bundle bundle = getIntent().getExtras();
        uIdKurir = bundle.getInt("id");
        uNikKurir = bundle.getLong("nik");
        uNamaKurir = bundle.getString("nama_penjual");
        uJkKurir = bundle.getString("jenis_kelamin");
        uNoponsKurir = bundle.getString("no_ponsel");
        uTelaKurir = bundle.getString("tempat_lahir");
        uTalaKurir = bundle.getString("tanggal_lahir");
        uAlamatKurir = bundle.getString("alamat");
        uFtoKurir = bundle.getString("gambar");

        NikKurir.setText(""+uNikKurir);
        NamaKurir.setText(uNamaKurir);
        if (uJkKurir.equals(jk[0])) index_jk = 0;
        else if (uJkKurir.equals(jk[1])) index_jk = 1;
        JkKurir.setSelection(index_jk);
        NoPonsKurir.setText(uNoponsKurir);
        TeLaKurir.setText(uTelaKurir);
        TalaKurir.setText(uTalaKurir);
        AlamatKurir.setText(uAlamatKurir);
        Glide.with(eFtoKurir.getContext())
                .load(uFtoKurir).into(eFtoKurir);
    }

    private void UpdateProfilKurir() {
        PbUpdteProfilKurir.setVisibility(View.VISIBLE);
        BtnUpdateProfileKurir.setVisibility(View.INVISIBLE);
        try {
            nik_Kurir = Long.parseLong(NikKurir.getText().toString().trim());
            nama_Kurir = NamaKurir.getText().toString().trim();
            jk_Kurir = JkKurir.getSelectedItem().toString().trim();
            nopons_Kurir = NoPonsKurir.getText().toString().trim();
            tela_Kurir = TeLaKurir.getText().toString().trim();
            tala_Kurir = TalaKurir.getText().toString().trim();
            alamat_Kurir = AlamatKurir.getText().toString().trim();
            if (mediaPathKurir != null){
                File imgFile = new File(mediaPathKurir);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
                foto_Kurir = MultipartBody.Part.createFormData("gambar", imgFile.getName(), reqBody);
            }else {
                foto_Kurir = null;
            }

            nik_KurirS = NikKurir.getText().toString().trim();
            lenNik_Kurir = nik_KurirS.length();

            if (nik_Kurir <= 0){
                NikKurir.setError("NIK TIDAK BOLEH KOSONG");
                PbUpdteProfilKurir.setVisibility(View.GONE);
                BtnUpdateProfileKurir.setVisibility(View.VISIBLE);
            } else if (lenNik_Kurir < 16) {
                NikKurir.setError("JUMLAH NIK TIDAK SESUAI");
                PbUpdteProfilKurir.setVisibility(View.GONE);
                BtnUpdateProfileKurir.setVisibility(View.VISIBLE);
            } else if (nama_Kurir.equals("")) {
                NamaKurir.setError("NAMA TIDAK BOLEH KOSONG");
                PbUpdteProfilKurir.setVisibility(View.GONE);
                BtnUpdateProfileKurir.setVisibility(View.VISIBLE);
            } else if (nopons_Kurir.equals("")) {
                NoPonsKurir.setError("NOMOR PONSEL TIDAK BOLEH KOSONG");
                PbUpdteProfilKurir.setVisibility(View.GONE);
                BtnUpdateProfileKurir.setVisibility(View.VISIBLE);
            } else if (tela_Kurir.equals("")) {
                TeLaKurir.setError("TEMPAT LAHIR TIDAK BOLEH KOSONG");
                PbUpdteProfilKurir.setVisibility(View.GONE);
                BtnUpdateProfileKurir.setVisibility(View.VISIBLE);
            }else if (alamat_Kurir.equals("")){
                AlamatKurir.setError("ALAMAT TIDAK BOLEH KOSONG ");
                PbUpdteProfilKurir.setVisibility(View.GONE);
                BtnUpdateProfileKurir.setVisibility(View.VISIBLE);
            }else {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    int id = uIdKurir;
                    ApiRequestDataKurir requestDataProfilKurir = RetroServer.konekRetrofit().create(ApiRequestDataKurir.class);
                    Call<ResponseDataKurir> UpdateProfileKurir = requestDataProfilKurir.UpdateProfileKurir(
                            id,
                            "PUT",
                            nik_Kurir,
                            RequestBody.create(MediaType.parse("text/plain"), nama_Kurir),
                            RequestBody.create(MediaType.parse("text/plain"), jk_Kurir),
                            RequestBody.create(MediaType.parse("text/plain"), alamat_Kurir),
                            RequestBody.create(MediaType.parse("text/plain"), tela_Kurir),
                            RequestBody.create(MediaType.parse("text/plain"), tala_Kurir),
                            RequestBody.create(MediaType.parse("text/plain"), nopons_Kurir),
                            foto_Kurir

                    );
                    UpdateProfileKurir.enqueue(new Callback<ResponseDataKurir>() {
                        @Override
                        public void onResponse(Call<ResponseDataKurir> call, Response<ResponseDataKurir> response) {
                            if( response.isSuccessful()) {
                                int kode = response.body().getKode();
                                String pesan = response.body().getPesan();
                                if (kode == 200){
                                    startActivity(new Intent(FormEditProfileKurirActivity.this, HalamanUtamaKurirActivity.class));
                                    Toast.makeText(FormEditProfileKurirActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();

                                }

                            }else {
                                Toast.makeText(FormEditProfileKurirActivity.this, "Data Gagal Tersimpan "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                            PbUpdteProfilKurir.setVisibility(View.GONE);
                            BtnUpdateProfileKurir.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onFailure(Call<ResponseDataKurir> call, Throwable t) {
                            Toast.makeText(FormEditProfileKurirActivity.this, "Gagal Menghubungi Server "+t.getMessage() , Toast.LENGTH_SHORT).show();
                            PbUpdteProfilKurir.setVisibility(View.GONE);
                            BtnUpdateProfileKurir.setVisibility(View.VISIBLE);
                            //startActivity(new Intent(AddDataActivityPenjual.this, HalamanUtamaPenjualActivity.class));
                        }

                    });
                }
            }
        }catch (NumberFormatException exception) {
            Toast.makeText(this, "Data Kurir Harus Terisi Semua !", Toast.LENGTH_SHORT).show();
            PbUpdteProfilKurir.setVisibility(View.GONE);
            BtnUpdateProfileKurir.setVisibility(View.VISIBLE);
        }
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TalaKurir.setText(dateFormat.format(newDate.getTime()));
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
                    imageUriKurir = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUriKurir, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathKurir = cursor.getString(columnIndex);
                    eFtoKurir.setImageURI(imageUriKurir);
                    cursor.close();
                    postPathKurir = mediaPathKurir;
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
            UpdateProfilKurir();
            //PbSimpanData.setVisibility(View.GONE);
            //btnAddData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestPermissionResultKurir(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 786 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            UpdateProfilKurir();
        }
    }
}