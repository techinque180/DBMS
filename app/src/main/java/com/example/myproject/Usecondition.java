package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private TextView kind1;
    private  TextView time1;
    private TextView kind2;
    private  TextView time2;
    private String kind;
    private String month;
    private String day;
    private String hourstart;
    private String minstart;
    private String hourend;
    private String minend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usecondition);
        kind1 = (TextView)findViewById(R.id.kind1);
        time1 = (TextView)findViewById(R.id.time1);
        kind2 = (TextView)findViewById(R.id.kind2);
        time2 = (TextView)findViewById(R.id.time2);

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
                        System.out.println(useDay+":"+useMonth);
                        Thread thread = new Thread(mutiThread);
                        thread.start();
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
            intent.putExtra("account", account);
            startActivity(intent);
        }
        else if(flag_usecondition == 2){
            Intent intent = new Intent(Usecondition.this, managerActivity.class);
            intent.putExtra("flag_usecondition", flag_usecondition);
            startActivity(intent);
        }


    }
    private Runnable mutiThread = new Runnable() {
        @Override
        public void run() {
            try {
//                10.22.15.106
                URL url = new URL("http://10.22.23.6/utities_connect/utilitiesGetData.php");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();


                int responseCode = connection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
                    String box = "";
                    String line = null;
                    while ((line = bufReader.readLine()) != null ) {
                        box += line + '\n';
                    }
                    inputStream.close();
                    result = box;
                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    int flag = 0;
                    JSONObject jsonObject = array.getJSONObject(i);


                    kind = jsonObject.getString("kind");
                    month = jsonObject.getString("month");
                    day = jsonObject.getString("day");
                    hourstart = jsonObject.getString("hour_start");
                    minstart = jsonObject.getString("minute_start");
                    hourend = jsonObject.getString("hour_end");
                    minend = jsonObject.getString("minute_end");
                    System.out.println(day+"/"+month);
                    if(String.valueOf(useDay).equals(day)  && String.valueOf(useMonth).equals(month)){
                        if(flag == 0){
                            System.out.println(useDay+useMonth);
                            kind1.setText(kind);
                            time1.setText(hourstart+":"+minstart+"~"+hourend+":"+minend);
                            flag++;
                        }
                        else if(flag == 1){
                            kind2.setText(kind);
                            time2.setText(hourstart+":"+minstart+"~"+hourend+":"+minend);
                        }
                    }else{
                        kind1.setText(" ");
                        time1.setText(" ");
                        kind2.setText(" ");
                        time2.setText(" ");
                    }
                    flag = 0;
                }
            } catch (Exception e) {
                result = e.toString();
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    //textView.setText(result);
                    System.out.println(result); // 更改顯示文字
                }
            });
        }
    };
}
