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
    private  String result;
    private accountData[] accountDatas = new accountData[20];

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

        Thread thread = new Thread(mutiThread);
        thread.start();

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /////帳號密碼是否存在於資料庫

                int flag = 0;
                System.out.println(et_account.getText());
                for (int i = 0; i < accountDatas.length; i++) {
                    if(accountDatas[i] == null) {
                        break;
                    }
                    if(accountDatas[i].getAccount_id().equals(et_account.getText().toString())
                            && accountDatas[i].getPassword().equals(et_passwd.getText().toString())) {
                        //帳密存在資料庫
                        System.out.println("login");
                        /////登入成功跳到主畫面
                        if(spinner_identity.getSelectedItem().toString().equals("住戶")){

                            Intent intent = new Intent(MainActivity.this, resident.class);
                            startActivity(intent);
                        }else if(spinner_identity.getSelectedItem().toString().equals("管理員")){
                            Intent intent = new Intent(MainActivity.this, managerActivity.class);
                            startActivity(intent);
                        }
                        flag++;
                        break;
                    }
                }
                if(flag == 1) {
                    flag--;
                }else{
                    //帳密不存在資料庫
                    /////登入失敗停留在此畫面
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
                URL url = new URL("http://10.22.15.106/accountGetdata.php");

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
                    JSONObject jsonObject = array.getJSONObject(i);
                    accountDatas[i] = new accountData();
                    accountDatas[i].setAccount_id(jsonObject.getString("account_id"));
                    accountDatas[i].setBalance_money(jsonObject.getString("balance_money"));
                    accountDatas[i].setManager_id(jsonObject.getString("manager_id"));
                    accountDatas[i].setPassword(jsonObject.getString("password"));
                    System.out.println(accountDatas[i].getAccount_id() + " " + accountDatas[i].getPassword() + " "
                            + accountDatas[i].getManager_id() + " " + accountDatas[i].getBalance_money());
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
