package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class Store extends AppCompatActivity {
    public static final String TAG="Store";

    private EditText et_storeroom;
    private EditText et_store_money;
    private EditText et_store_manager;
    private Button bnt_storesend;
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");

        et_storeroom = (EditText) findViewById(R.id.et_storeroom);
        et_store_money = (EditText) findViewById(R.id.et_storemoney);
        bnt_storesend = (Button) findViewById(R.id.bnt_storesend);
        et_store_manager = (EditText) findViewById(R.id.et_storemanager);

        bnt_storesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                Intent intent = new Intent(Store.this, managerActivity.class);
                startActivity(intent);
            }
        });
    }

    private String executeHttpGet() {

        HttpURLConnection con=null;
        InputStream in=null;
        String      path="http://10.22.15.106/transaction_connect/get_all_transaction.php";
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
        Log.i(TAG, "parseInfo: sb:"+sb.toString());
        return sb.toString();
    }

    //傳送
    private void showDialog() {
        String room_no=et_storeroom.getText().toString();
        String stored_money= et_store_money.getText().toString();
        String manager_id= et_store_manager.getText().toString();

        try {
            jsonObject.putOpt("room_no", room_no);
            jsonObject.putOpt("stored_money",stored_money);
            jsonObject.putOpt("manager_id",manager_id);

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

        String path="http://10.22.15.106/transaction_connect/create_transaction.php";
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
