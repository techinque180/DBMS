package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class Usecondition extends AppCompatActivity {

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    private Button btn_usedate ;
    private int useYear, useMonth, useDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usecondition);

        btn_usedate = (Button) findViewById(R.id.btn_usedate);
        btn_usedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                useYear = c.get(Calendar.YEAR);
                useMonth = c.get(Calendar.MONTH);
                useDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(Usecondition.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String useformat = setDateFormat(year, month, day);
                        btn_usedate.setText(useformat);

                        //////輸入日期資訊到資料庫//////
                    }

                }, useYear, useMonth, useDay).show();
            }

        });
    }
}