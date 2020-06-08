package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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
    private String account;
    private Button btn_usedate ;
    private int useYear, useMonth, useDay;
    private String result;
    private int flag_usecondition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usecondition);


        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        flag_usecondition = intent.getIntExtra("flag_usecondition",flag_usecondition);
        setTitle("設施使用狀況");
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
                        useYear = year;
                        useMonth = month + 1;
                        useDay = day;

                        //////輸入日期資訊到資料庫//////
                    }

                }, useYear, useMonth, useDay).show();
            }

        });
    }

    @Override
    public void onBackPressed() {
        if(flag_usecondition == 1){
            Intent intent = new Intent(Usecondition.this, resident.class);
            intent.putExtra("flag_usecondition", flag_usecondition);
            startActivity(intent);
        }
        else if(flag_usecondition == 2){
            Intent intent = new Intent(Usecondition.this, managerActivity.class);
            intent.putExtra("flag_usecondition", flag_usecondition);
            startActivity(intent);
        }


    }
}