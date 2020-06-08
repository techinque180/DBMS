package com.example.myproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class resident extends AppCompatActivity {

    private Button bnt_record;
    private Button bnt_rent;
    private Button bnt_ressearchbalance;
    private Button bnt_resusecondition;
    private Button bnt_resdamagelevel;
    private String account;
    private String result;

    private int flag_usecondition;
    private BalanceData balanceData = new BalanceData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        flag_usecondition = intent.getIntExtra("flag_usecondition",flag_usecondition);
        setTitle("住戶房號：" + account);
        bnt_record = (Button) findViewById(R.id.bnt_record);
        bnt_rent = (Button) findViewById(R.id.bnt_rent);
        bnt_ressearchbalance = (Button) findViewById(R.id.bnt_ressearchbalance);
        bnt_resusecondition = (Button) findViewById(R.id.bnt_resusecondition);
        bnt_resdamagelevel = (Button) findViewById(R.id.bnt_resdamagelevel);

        Thread thread = new Thread(mutiThread);
        thread.start();

        bnt_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resident.this, rentcontentActivity.class);
                intent.putExtra("account", account);
                intent.putExtra("flag_usecondition", flag_usecondition);
                startActivity(intent);//跳到租借紀錄畫面

            }
        });

        bnt_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resident.this, Rental.class);
                intent.putExtra("account", account);
                intent.putExtra("flag_usecondition", flag_usecondition);
                startActivity(intent);//跳到租借畫面
            }
        });

        bnt_ressearchbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(resident.this)
                        .setTitle("餘額：" + balanceData.getBalance_money())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            };
        });

        bnt_resusecondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resident.this, Usecondition.class);
                intent.putExtra("account", account);
                intent.putExtra("flag_usecondition", flag_usecondition);
                startActivity(intent);//跳到使用狀況畫面
            }
        });

        bnt_resdamagelevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resident.this, Declare.class);
                intent.putExtra("account", account );
                intent.putExtra("flag_usecondition", flag_usecondition);
                startActivity(intent);//跳到申報畫面
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(resident.this, MainActivity.class);
        startActivity(intent);
    }

    private Runnable mutiThread = new Runnable() {
        @Override
        public void run() {
            try {
//                10.22.15.106
                URL url = new URL("http://10.22.23.6/account_connect/get_all_account.php");

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
                    String room_no = jsonObject.getString("room_no");
                    if(account.equals(room_no)) {
                        balanceData.setBalance_money(jsonObject.getString("balance_money"));
                        System.out.println("money is" + balanceData.getBalance_money());
                    }
//                    System.out.println(accountDatas[i].getAccount_id() + " " + accountDatas[i].getPassword() + " "
//                            + accountDatas[i].getManager_id() + " " + accountDatas[i].getBalance_money());
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
