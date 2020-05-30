package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class rentcontentActivity extends AppCompatActivity {

    private TextView tv_rentpeople;
    private TextView tv_rentuti;
    private TextView tv_rentyear;
    private TextView rentmonth;
    private TextView tv_rentday;
    private TextView tv_renthr;
    private TextView tv_rentmin;
    private TextView returnhr;
    private TextView returnmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentcontent);

        tv_rentpeople = (TextView) findViewById(R.id.tv_rentpeople);
        tv_rentuti = (TextView) findViewById(R.id.tv_rentuti);
        tv_rentyear = (TextView) findViewById(R.id.tv_rentyear);
        rentmonth = (TextView) findViewById(R.id.rentmonth);
        tv_rentday = (TextView) findViewById(R.id.tv_rentday);
        tv_renthr = (TextView) findViewById(R.id.tv_renthr);
        tv_rentmin = (TextView) findViewById(R.id.tv_rentmin);
        returnhr = (TextView) findViewById(R.id.returnhr);
        returnmin = (TextView) findViewById(R.id.returnmin);

        tv_rentpeople.setText(""); //從資料庫拿取人數
        tv_rentuti.setText(""); //從資料庫拿取設施名字
        tv_rentyear.setText(""); //從資料庫拿取租的年份
        rentmonth.setText(""); //從資料庫拿取租的月分
        tv_rentday.setText(""); //從資料庫拿取租的日期
        tv_renthr.setText(""); //從資料庫拿取租的時
        tv_rentmin.setText(""); //從資料庫拿取租得分
        returnhr.setText(""); //從資料庫拿取歸還的時
        returnmin.setText(""); //從資料庫拿取歸還得分

    }
}
