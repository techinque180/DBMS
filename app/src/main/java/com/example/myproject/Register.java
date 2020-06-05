package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    public static final String TAG="Register";

    private EditText et_name;
    private EditText et_floor;
    private EditText et_roomNum;
    private  EditText et_phoneNum;
    private  EditText et_reg_passwd;
    private  EditText et_reg_passwd_check;
    private Button btn_go;


    private String name ;
    private String floor;
    private String roomNum ;
    private String phoneNum ;

    private int passwd_flag;

    private  int count = 0; //帳號id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = (EditText)findViewById(R.id.et_name);
        et_floor = (EditText)findViewById(R.id.et_floor);
        et_roomNum = (EditText)findViewById(R.id.et_roomNum);
        et_phoneNum = (EditText)findViewById(R.id.et_phoneNum);
        et_reg_passwd = (EditText)findViewById(R.id.et_reg_passwd);
        et_reg_passwd_check = (EditText)findViewById(R.id.et_reg_passwd_check);
        btn_go = (Button) findViewById(R.id.btn_go);

        name = et_name.getText().toString();
        floor = et_floor.getText().toString();
        roomNum = et_roomNum.getText().toString();
        phoneNum = et_phoneNum.getText().toString();


        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Thread thread = new Thread(mutiThread);
//                thread.start();
                showDialog();
                if((et_reg_passwd.getText().toString()).equals(et_reg_passwd_check.getText().toString())){
                    Toast.makeText(Register.this, "註冊成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                } ////////else if(如果房號不存在於資料庫)
                else {
                    Toast.makeText(Register.this, "重複輸入密碼錯誤", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    private String executeHttpGet() {

        HttpURLConnection con=null;
        InputStream in=null;
        String      path="http://192.168.1.101/resident_connect/get_all_resident.php";
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
        BufferedReader  br=new BufferedReader(new InputStreamReader(in));
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
        count++;
        String room_no=et_roomNum.getText().toString();
        String floor=et_floor.getText().toString();
        String phone=et_phoneNum.getText().toString();
        String account_id = String.valueOf(count);
        String name = et_name.getText().toString();
        try {
            jsonObject.put("room_no", room_no);
            jsonObject.put("floor",floor);
            jsonObject.put("phone",phone);
            jsonObject.put("account_id",account_id);
            jsonObject.put("name",name);
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

        String path="http://192.168.1.101/resident_connect/create_resident.php";
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
