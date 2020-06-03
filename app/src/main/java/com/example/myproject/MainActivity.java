package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner_identity;
    private EditText et_account;
    private EditText et_passwd;
    private Button btn_login;
    private Button btn_reg;
    String result;
    private residentData[] residentDatas = new residentData[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner_identity = (Spinner)findViewById(R.id.spinner_identity);

        final String[] lunch = {"住戶", "管理員"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>( MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner_identity.setAdapter(lunchList);

        et_account = (EditText)findViewById(R.id.et_account);
        et_passwd = (EditText)findViewById(R.id.et_passwd);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_reg = (Button)findViewById(R.id.btn_reg);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////帳號密碼是否存在於資料庫
                if(spinner_identity.getSelectedItem().toString().equals("住戶")){
                    Thread thread = new Thread(mutiThread);
                    thread.start();
                    Intent intent = new Intent(MainActivity.this, resident.class);
                    startActivity(intent);
                }else if(spinner_identity.getSelectedItem().toString().equals("管理員")){
                    Intent intent = new Intent(MainActivity.this, managerActivity.class);
                    startActivity(intent);
                }
                /////登入成功跳到主畫面(顯示登入成功)
                /////登入失敗停留在此畫面(顯示登入失敗)
            }
        });



        /*跳出顯示框(Toast)
        Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
        */


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                //////跳到註冊畫面
            }
        });
    }

    private Runnable mutiThread = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("http:/10.22.15.106/registerGetdata.php");

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
                System.out.println(result);
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    residentDatas[i] = new residentData();
                    String account_id = jsonObject.getString("account_id");
                    residentDatas[i].setAccount_id(account_id);
                    String room_no = jsonObject.getString("room_no");
                    residentDatas[i].setRoom_no(room_no);
                    System.out.println(residentDatas[i].getAccount_id() + residentDatas[i].getRoom_no());
                    //Log.d("TAG", "title:" + title + ", tag:" + tag + ", info:" + info);
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
