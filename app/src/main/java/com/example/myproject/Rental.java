package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Rental extends AppCompatActivity {

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    private Spinner spinner_type;
    private Spinner spinner_nPeople;
    private int mYear, mMonth, mDay;

    private Button btn_date ;
    private Button btn_timeStart;
    private Button btn_timeEnd;
    private Button btn_rental_go;


    private int declare_flag = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);


        spinner_type = (Spinner)findViewById(R.id.spinner_type);

        final String[] lunch = {"KTV01", "KTV02", "健身房01", "游泳池01", "討論室01", "討論室02", "討論室03", "討論室04"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>( Rental.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner_type.setAdapter(lunchList);
        ///////輸入種類到資料庫////////


        spinner_nPeople = (Spinner)findViewById(R.id.spinner_nPeople);

        final Integer[] lunch1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> lunch1List = new ArrayAdapter<>( Rental.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch1);
        spinner_nPeople.setAdapter(lunch1List);
        ///////輸入人數到資料庫////////



        btn_date = (Button)findViewById(R.id.btn_date);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(Rental.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year, month, day);
                        btn_date.setText(format);

                        //////輸入日期資訊到資料庫//////
                    }

                }, mYear, mMonth, mDay).show();
            }

        });


        btn_timeStart = (Button)findViewById(R.id.btn_timeStart);

        btn_timeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(Rental.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        btn_timeStart.setText(hourOfDay + ":" + minute);


                        ///////輸入時間資訊到資料庫//////
                    }
                }, hour, minute, true).show();
            }

        });


        btn_timeEnd = (Button)findViewById(R.id.btn_timeEnd);

        btn_timeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(Rental.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        btn_timeEnd.setText(hourOfDay + ":" + minute);


                        ///////輸入時間資訊到資料庫//////
                    }
                }, hour, minute, true).show();
            }

        });

        btn_rental_go = (Button)findViewById(R.id.btn_rental_go);
        btn_rental_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////判斷租借時間是否已經存在於資料庫///////if(不存在){}
                //////將Login畫面改成登入後的畫面//////

                Intent intent = new Intent(Rental.this, resident.class);
                startActivity(intent);
            }
        });

        if(declare_flag == 0){
            Toast.makeText(this, "此時段已借出", Toast.LENGTH_LONG).show();
        }
        else if(declare_flag == 2){
            Toast.makeText(this, "租借成功", Toast.LENGTH_LONG).show();
        }

    }
}
