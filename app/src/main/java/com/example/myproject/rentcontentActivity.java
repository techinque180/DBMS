package com.example.myproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    private Button bnt_return;
    private String account;
    private String result;
    private String room_no;
    private String npeople;
    private String kind;
    private String year;
    private String month;
    private String day;
    private int returnflag = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentcontent);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        System.out.println("room_no is " + account);

        Thread thread = new Thread(mutiThread);
        thread.start();

        tv_rentpeople = (TextView) findViewById(R.id.tv_rentpeople);
        tv_rentuti = (TextView) findViewById(R.id.tv_rentuti);
        tv_rentyear = (TextView) findViewById(R.id.tv_rentyear);
        rentmonth = (TextView) findViewById(R.id.rentmonth);
        tv_rentday = (TextView) findViewById(R.id.tv_rentday);
        tv_renthr = (TextView) findViewById(R.id.tv_renthr);
        tv_rentmin = (TextView) findViewById(R.id.tv_rentmin);
        returnhr = (TextView) findViewById(R.id.returnhr);
        returnmin = (TextView) findViewById(R.id.returnmin);
        bnt_return = (Button) findViewById(R.id.bnt_return);

        bnt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rentcontentActivity.this, "已歸還", Toast.LENGTH_LONG).show();
                showDialog();
                Intent intent = new Intent(rentcontentActivity.this, resident.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });
    }


    private void showDialog() {
        try {
            jsonObject.put("room_no",account);

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
        String path="http://192.168.1.101/utities_connect/utilities_delete.php";
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



    private Runnable mutiThread = new Runnable() {
        @Override
        public void run() {
            try {
//                10.22.15.106
                URL url = new URL("http://192.168.1.101/utities_connect/utilitiesGetData.php");

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
                int flag = 0;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    if(jsonObject.getString("room_no").equals(account)) {
                        npeople = jsonObject.getString("npeople");
                        tv_rentpeople.setText(npeople);
                        kind = jsonObject.getString("kind");
                        tv_rentuti.setText(kind); //從資料庫拿取設施名字
                        year = jsonObject.getString("year");
                        tv_rentyear.setText(year); //從資料庫拿取租的年份
                        month = jsonObject.getString("month");
                        rentmonth.setText(month); //從資料庫拿取租的月分
                        day = jsonObject.getString("day");
                        tv_rentday.setText(day); //從資料庫拿取租的日期
                        tv_renthr.setText(jsonObject.getString("hour_start")); //從資料庫拿取租的時
                        tv_rentmin.setText(jsonObject.getString("minute_start")); //從資料庫拿取租得分
                        returnhr.setText(jsonObject.getString("hour_end")); //從資料庫拿取歸還的時
                        returnmin.setText(jsonObject.getString("minute_end")); //從資料庫拿取歸還得分
                        flag++;
                    }
                }
                if(flag == 1) {
                    flag--;
                }else{
                    tv_rentpeople.setText(" ");
                    tv_rentday.setText(" ");
                    tv_renthr.setText(" ");
                    tv_rentmin.setText(" ");
                    tv_rentuti.setText(" ");
                    tv_rentyear.setText(" ");
                    returnhr.setText(" ");
                    returnmin.setText(" ");
                    rentmonth.setText(" ");
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
