package com.example.myproject;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeclareList extends AppCompatActivity {
    private  String account;
    private String result;

    private String room_no;
    private TextView room;
    private String kind;
    private TextView uti;
    private String reason;
    private String damage_level;
    private TextView level;
    private LinearLayout container;
    private TextView tv11;
    private TextView tv12;
    private TextView tv13;
    private TextView tv14;
    private TextView tv21;
    private TextView tv22;
    private TextView tv23;
    private TextView tv24;
    private TextView tv31;
    private TextView tv32;
    private TextView tv33;
    private TextView tv34;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_list);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        LinearLayout mContainer = findViewById(R.id.container);
        tv11 = (TextView)findViewById(R.id.tv11);
        tv12 = (TextView)findViewById(R.id.tv12);
        tv13 = (TextView)findViewById(R.id.tv13);
        tv14 = (TextView)findViewById(R.id.tv14);
        tv21 = (TextView)findViewById(R.id.tv21);
        tv22 = (TextView)findViewById(R.id.tv22);
        tv23 = (TextView)findViewById(R.id.tv23);
        tv24 = (TextView)findViewById(R.id.tv24);
        tv31 = (TextView)findViewById(R.id.tv31);
        tv32 = (TextView)findViewById(R.id.tv32);
        tv33 = (TextView)findViewById(R.id.tv33);
        tv34 = (TextView)findViewById(R.id.tv34);

        Thread thread = new Thread(mutiThread);
        thread.start();

    }

    private Runnable mutiThread = new Runnable() {
        @Override
        public void run() {
            try {
//                10.22.15.106
                URL url = new URL("http://10.22.3.26/declared_connect/declaredGetData.php");

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
//                TableLayout user_list = (TableLayout)findViewById(R.id.tl_userList);
//                //LinearLayout ll = (LinearLayout)findViewById(R.id.tv_declareList);
//                user_list.setStretchAllColumns(true);
//                TableLayout.LayoutParams row_layout = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
//                TableRow.LayoutParams view_layout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    room_no = jsonObject.getString("room_no");
                    //room.setText(room_no);
                    kind = jsonObject.getString("kind");
                    //uti.setText(kind); //從資料庫拿取設施名字
                    damage_level = jsonObject.getString("damage_level");
                    //level.setText(damage_level); //從資料庫拿取設施名字
                    reason = jsonObject.getString("reason");
                    //reason.setText(jsonObject.getString("reason")); //從資料庫拿取租的時
                    System.out.println("i:"+i);
                    System.out.println(room_no+ kind+damage_level+reason);

                    if(i == 0){
                        tv11.setText(room_no);
                        tv12.setText(kind);
                        tv13.setText(damage_level);
                        tv14.setText(reason);
                    }else if(i == 1){
                        tv21.setText(room_no);
                        tv22.setText(kind);
                        tv23.setText(damage_level);
                        tv24.setText(reason);
                    }else if(i == 2){
                        tv31.setText(room_no);
                        tv32.setText(kind);
                        tv33.setText(damage_level);
                        tv34.setText(reason);
                    }
//                    TableRow tr = new TableRow(DeclareList.this);
//                    tr.setLayoutParams(row_layout);
//                    tr.setGravity(Gravity.CENTER_HORIZONTAL);
//
//                    //TextView child = new TextView(DeclareList.this);
//
//                    TextView user_room = new TextView(DeclareList.this);
//                    user_room.setText(room_no);
//                    user_room.setTextSize(20);
//                    user_room.setLayoutParams(view_layout);
//
//                    TextView user_level = new TextView(DeclareList.this);
//                    user_level.setText(damage_level);
//                    user_level.setTextSize(20);
//                    user_level.setLayoutParams(view_layout);
//
//                    TextView user_kind= new TextView(DeclareList.this);
//                    user_kind.setText(kind);
//                    user_kind.setTextSize(20);
//                    user_kind.setLayoutParams(view_layout);
//
//                    TextView user_reason = new TextView(DeclareList.this);
//                    user_reason.setText(reason);
//                    user_reason.setTextSize(20);
//                    user_reason.setLayoutParams(view_layout);
//
//                    tr.addView(user_room);
//                    tr.addView(user_level);
//                    tr.addView(user_kind);
//                    tr.addView(user_reason);
//                    user_list.addView(tr);
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