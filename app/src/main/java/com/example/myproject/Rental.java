package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Rental extends AppCompatActivity {
    public static final String TAG="Rental";
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
    private String nPeople;
    private String type;
    private String response;
    private int hour_start;
    private int minute_start;
    private int hour_end;
    private int minute_end;
    private int flag_usecondition;
    private String account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        flag_usecondition = intent.getIntExtra("flag_usecondition",flag_usecondition);
        System.out.println("room:" + account);
        setTitle("租借公共設施");
        spinner_type = (Spinner)findViewById(R.id.spinner_type);

        final String[] lunch = {"KTV01", "KTV02", "健身房01", "游泳池01", "討論室01", "討論室02", "討論室03", "討論室04"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>( Rental.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner_type.setAdapter(lunchList);
        type = spinner_type.getSelectedItem().toString();
        ///////輸入種類到資料庫////////


        spinner_nPeople = (Spinner)findViewById(R.id.spinner_nPeople);

        final Integer[] lunch1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> lunch1List = new ArrayAdapter<>( Rental.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch1);
        spinner_nPeople.setAdapter(lunch1List);
        nPeople = spinner_nPeople.getSelectedItem().toString();
        ///////輸入人數到資料庫////////



        btn_date = (Button)findViewById(R.id.btn_date);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                System.out.println(mYear + "/" + mMonth + "/" + mDay);
                new DatePickerDialog(Rental.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year, month, day);
                        btn_date.setText(format);
                        mYear = year;
                        mMonth = month + 1;
                        mDay = day;
                        System.out.println(format);
                    }
                }, mYear, mMonth, mDay).show();
            }

        });


        btn_timeStart = (Button)findViewById(R.id.btn_timeStart);

        btn_timeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                hour_start = c.get(Calendar.HOUR_OF_DAY);
                minute_start = c.get(Calendar.MINUTE);
                new TimePickerDialog(Rental.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        btn_timeStart.setText(hourOfDay + ":" + minute);
                        hour_start = hourOfDay;
                        minute_start = minute;
                    }
                }, hour_start, minute_start, true).show();
            }

        });


        btn_timeEnd = (Button)findViewById(R.id.btn_timeEnd);

        btn_timeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                hour_end = c.get(Calendar.HOUR_OF_DAY);
                minute_end = c.get(Calendar.MINUTE);
                new TimePickerDialog(Rental.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        btn_timeEnd.setText(hourOfDay + ":" + minute);
                        hour_end = hourOfDay;
                        minute_end = minute;
                    }
                }, hour_end, minute_end, true).show();
            }

        });

        btn_rental_go = (Button)findViewById(R.id.btn_rental_go);
        btn_rental_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////判斷租借時間是否已經存在於資料庫///////if(不存在){}
                //////將Login畫面改成登入後的畫面//////
                showDialog();
                showDialogMoney();
                System.out.println("rental_go");
                Intent intent = new Intent(Rental.this, resident.class);
                intent.putExtra("account", account);
                intent.putExtra("flag_usecondition", flag_usecondition);
                Toast.makeText(Rental.this, "租借成功", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Rental.this, resident.class);
        intent.putExtra("flag_usecondition", flag_usecondition);
        intent.putExtra("account", account);
        startActivity(intent);
    }


    /*傳送資料給MySQL資料庫*/

    private void showDialog() {
        String nPeople = spinner_nPeople.getSelectedItem().toString();
        String kind = spinner_type.getSelectedItem().toString();
        String year = String.valueOf(mYear);
        String month = String.valueOf(mMonth);
        String day = String.valueOf(mDay);
        String hour_s = String.valueOf(hour_start);
        String minute_s = String.valueOf(minute_start);
        String hour_e = String.valueOf(hour_end);
        String minute_e = String.valueOf(minute_end);

        try {
            jsonObject.put("room_no",account);
            jsonObject.put("npeople",nPeople);
            jsonObject.put("kind",kind);
            jsonObject.put("year",year);
            jsonObject.put("month",month);
            jsonObject.put("day",day);
            jsonObject.put("hour_start",hour_s);
            jsonObject.put("minute_start",minute_s);
            jsonObject.put("hour_end",hour_e);
            jsonObject.put("minute_end",minute_e);

        } catch (JSONException e) {
            e.printStackTrace();
        };
        send();

    }

    private void send() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeHttpPost();
            }
        }).start();

    }

    JSONObject jsonObject=new JSONObject();
    private void executeHttpPost() {
//        10.22.15.106
        String path="http://10.22.3.26/utities_connect/create_utilities.php";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setConnectTimeout(3000);     //設定連線超時時間
            conn.setDoOutput(true);  //開啟輸出流，以便向伺服器提交資料
            conn.setDoInput(true);  //開啟輸入流，以便從伺服器獲取資料
            conn.setUseCaches(false);//使用Post方式不能使用快取
            conn.setRequestMethod("POST");  //設定以Post方式提交資料
            //conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 設定檔案型別:
            //conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            // 設定接收型別否則返回415錯誤
            //conn.setRequestProperty("accept","*/*")此處為暴力方法設定接受所有型別，以此來防範返回415;
            conn.setRequestProperty("accept","application/json");

            // 往伺服器裡面傳送資料
            String Json=jsonObject.toString();

            System.out.println("-----------"+Json);

            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 設定檔案長度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("upload: ", "doJsonPost: "+conn.getResponseCode());//如輸出200，則對了
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //傳送
    private void showDialogMoney() {
        try {
            jsonObjectMoney.putOpt("room_no", account);
        } catch (JSONException e) {
            e.printStackTrace();
        };
        sendMoney();
    }

    private void sendMoney() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeHttpPostMoney();
            }
        }).start();

    }
    JSONObject jsonObjectMoney=new JSONObject();
    private void executeHttpPostMoney() {
//        10.22.23.6
        String path="http://10.22.3.26/account_connect/account_update_rental.php";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setConnectTimeout(3000);     //設定連線超時時間
            conn.setDoOutput(true);  //開啟輸出流，以便向伺服器提交資料
            conn.setDoInput(true);  //開啟輸入流，以便從伺服器獲取資料
            conn.setUseCaches(false);//使用Post方式不能使用快取
            conn.setRequestMethod("POST");  //設定以Post方式提交資料
            //conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 設定檔案型別:
            //conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            // 設定接收型別否則返回415錯誤
            //conn.setRequestProperty("accept","*/*")此處為暴力方法設定接受所有型別，以此來防範返回415;
            conn.setRequestProperty("accept","application/json");

            // 往伺服器裡面傳送資料
            String Json=jsonObjectMoney.toString();

            System.out.println("-----------    "+Json);

            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 設定檔案長度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("upload: ", "doJsonPost: "+conn.getResponseCode());//如輸出200，則對了
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
