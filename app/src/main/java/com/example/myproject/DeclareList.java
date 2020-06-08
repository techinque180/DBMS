package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
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
    private int flag_usecondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_list);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        flag_usecondition = intent.getIntExtra("flag_usecondition",flag_usecondition);
        setTitle("申報狀況");

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeclareList.this, managerActivity.class);
        intent.putExtra("flag_usecondition", flag_usecondition);
        startActivity(intent);

    }
    private Runnable mutiThread = new Runnable() {
        @Override
        public void run() {
            try {
//                10.22.15.106
                URL url = new URL("http://10.22.23.6/utities_connect/declareGetData.php");

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
                TableLayout user_list = (TableLayout)findViewById(R.id.tl_userList);
                user_list.setStretchAllColumns(true);
                TableLayout.LayoutParams row_layout = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                TableRow.LayoutParams view_layout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
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

//                    room = (TextView) findViewById(R.id.room);
//                    level = (TextView) findViewById(R.id.level);
//                    uti = (TextView) findViewById(R.id.uti);
//                    reason = (TextView) findViewById(R.id.reason);
                    TableRow tr = new TableRow(DeclareList.this);
                    tr.setLayoutParams(row_layout);
                    tr.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView user_room = new TextView(DeclareList.this);
                    user_room.setText(room_no);
                    user_room.setLayoutParams(view_layout);

                    TextView user_level = new TextView(DeclareList.this);
                    user_level.setText(damage_level);
                    user_level.setLayoutParams(view_layout);

                    TextView user_kind= new TextView(DeclareList.this);
                    user_kind.setText(kind);
                    user_kind.setLayoutParams(view_layout);

                    TextView user_reason = new TextView(DeclareList.this);
                    user_reason.setText(reason);
                    user_reason.setLayoutParams(view_layout);

                    tr.addView(user_room);
                    tr.addView(user_level);
                    tr.addView(user_kind);
                    tr.addView(user_reason);
                    user_list.addView(tr);
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