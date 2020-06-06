package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

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
    private String account;

    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentcontent);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");

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

        private String executeHttpGet() {

            HttpURLConnection con=null;
            InputStream in=null;
            String      path="http://10.22.15.106/utities_connect/get_all_utilities.php";
            try {
                con= (HttpURLConnection) new URL(path).openConnection();
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setDoInput(true);
                con.setRequestMethod("GET");
                if(con.getResponseCode()==200){

                    in=con.getInputStream();
                    return parseInfo(in);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private String parseInfo(InputStream in) throws IOException {
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            StringBuilder sb=new StringBuilder();
            String line=null;
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
//            Log.i(TAG, "parseInfo: sb:"+sb.toString());
            return sb.toString();
        }
//
//        //傳送
//        private void showDialog() {
//            String room_no=et_roomNum.getText().toString();
//            String floor=et_floor.getText().toString();
//            String phone=et_phoneNum.getText().toString();
//            String name = et_name.getText().toString();
//            String password = et_reg_passwd.getText().toString();
//            try {
//                jsonObject.putOpt("room_no", room_no);
//                jsonObject.putOpt("floor",floor);
//                jsonObject.putOpt("phone",phone);
//                jsonObject.putOpt("name",name);
//                jsonObject.putOpt("password", password);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            };
//            send();
//        }
//
//        private void send() {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    executeHttpPost();
//                }
//            }).start();
//
//        }
//        JSONObject jsonObject=new JSONObject();
//        private void executeHttpPost() {
//
//            String path="http://10.22.15.106/utities_connect/create_utilities.php";
//            try {
//                URL url = new URL(path);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                //conn.setConnectTimeout(3000);     //設定連線超時時間
//                conn.setDoOutput(true);  //開啟輸出流，以便向伺服器提交資料
//                conn.setDoInput(true);  //開啟輸入流，以便從伺服器獲取資料
//                conn.setUseCaches(false);//使用Post方式不能使用快取
//                conn.setRequestMethod("POST");  //設定以Post方式提交資料
//                //conn.setRequestProperty("Connection", "Keep-Alive");
//                conn.setRequestProperty("Charset", "UTF-8");
//                // 設定檔案型別:
//                //conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
//                // 設定接收型別否則返回415錯誤
//                //conn.setRequestProperty("accept","*/*")此處為暴力方法設定接受所有型別，以此來防範返回415;
//                conn.setRequestProperty("accept","application/json");
//
//                // 往伺服器裡面傳送資料
//                String Json=jsonObject.toString();
//
//                System.out.println("-----------    "+Json);
//
//                if (Json != null && !TextUtils.isEmpty(Json)) {
//                    byte[] writebytes = Json.getBytes();
//                    // 設定檔案長度
//                    conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
//                    OutputStream outwritestream = conn.getOutputStream();
//                    outwritestream.write(Json.getBytes());
//                    outwritestream.flush();
//                    outwritestream.close();
//                    Log.d("upload: ", "doJsonPost: "+conn.getResponseCode());//如輸出200，則對了
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

}
