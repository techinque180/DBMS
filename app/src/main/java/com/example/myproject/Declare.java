package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Declare extends AppCompatActivity {

    private Spinner spinner_uti;
    private Spinner spinner_damageLevel;

    private Button btn_declare_go;
    private EditText et_reason;
    private String utility;
    private String level;

//    private String why;

    private  String account;
    private int flag_usecondition;


    public static final String TAG="Declare";
    private String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");//new
        flag_usecondition = intent.getIntExtra("flag_usecondition",flag_usecondition);
        et_reason = (EditText)findViewById(R.id.et_reason) ;
        setTitle("申報設施");


        spinner_uti = (Spinner)findViewById(R.id.spinner_uti);

        final String[] lunch = {"KTV01", "KTV02", "健身房01", "游泳池01", "討論室01", "討論室02", "討論室03", "討論室04"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>( Declare.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner_uti.setAdapter(lunchList);

        ///////輸入種類到資料庫////////



        spinner_damageLevel = (Spinner)findViewById(R.id.spinner_damageLevel);

        final Integer[] lunch1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> lunch1List = new ArrayAdapter<>( Declare.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch1);
        spinner_damageLevel.setAdapter(lunch1List);

        ///////輸入到資料庫////////

        btn_declare_go = (Button)findViewById(R.id.btn_declare_go);
        btn_declare_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                ///////儲存原因(why)到資料庫/////////

                ///////Login要改成登入後畫面///////
                Intent intent = new Intent(Declare.this, resident.class);
                Toast.makeText(Declare.this, "已送出", Toast.LENGTH_LONG).show();
                intent.putExtra("account", account);
                intent.putExtra("flag_usecondition", flag_usecondition);
                startActivity(intent);

            }
        });





    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Declare.this, resident.class);
        intent.putExtra("flag_usecondition", flag_usecondition);
        intent.putExtra("account", account);
        startActivity(intent);

    }
    JSONObject jsonObject=new JSONObject();


    //傳送
    private void showDialog() {
        String why = et_reason.getText().toString();//new
        //String reason=et_reason.getText().toString();
        level = spinner_damageLevel.getSelectedItem().toString();
        utility = spinner_uti.getSelectedItem().toString();
        System.out.println("Ya" + spinner_damageLevel.getSelectedItem().toString());
        System.out.println("account ="+account);
        try {

            System.out.println("here");
            //String reason=et_reason.getText().toString();
            jsonObject.put("room_no",account);
         //   jsonObject.put("uti_no","99");
           // jsonObject.put("reason", reason);
            jsonObject.put("kind",utility);
            jsonObject.put("reason", why);
            jsonObject.put("damage_level",level);


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

    private void executeHttpPost() {

        String path="http://10.22.3.26/declared_connect/create_declared.php";
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
