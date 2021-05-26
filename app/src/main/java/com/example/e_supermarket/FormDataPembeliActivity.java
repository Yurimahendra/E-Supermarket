package com.example.e_supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormDataPembeliActivity extends AppCompatActivity {

    EditText EdtTalaB;
    DatePickerDialog datePickerDialogB;
    SimpleDateFormat dateFormatB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_pembeli);

        EditText editTextB = findViewById(R.id.EdtNopB);
        editTextB.setText(String.format(
                "%s", getIntent().getStringExtra("mobile")
        ));

        EdtTalaB = (EditText)findViewById(R.id.EdtTalaB);

        dateFormatB = new SimpleDateFormat("dd-MM-yyyy");

        EdtTalaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialogB = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                EdtTalaB.setText(dateFormatB.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialogB.show();
    }
}