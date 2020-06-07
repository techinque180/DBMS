package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONObject;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DeclareList extends AppCompatActivity {
    private  String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_list);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");

        StrictMode.ThreadPolicy oldThreadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());

        StrictMode.VmPolicy oldVmPolicy = StrictMode.getVmPolicy();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectAll()
//                .penaltyLog()
//                .penaltyDialog()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
//                .penaltyLog()
//                .build());


        TableLayout tl_userList = (TableLayout)findViewById(R.id.tl_userList);
        tl_userList.setStretchAllColumns(true);
        TableLayout.LayoutParams row_layout = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams view_layout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        try{
            String result = DBConnect.executeQuery("SELECT * FROM declared");

            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonData = jsonArray.getJSONObject(i);
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(row_layout);
                tr.setGravity(Gravity.CENTER_HORIZONTAL);


                TextView user_room_no = new TextView(this);
                user_room_no.setText(jsonData.getString("room_no"));
                user_room_no.setLayoutParams(view_layout);

                TextView user_uti = new TextView(this);
                user_uti.setText(jsonData.getString("kind"));
                user_uti.setLayoutParams(view_layout);

                TextView user_level = new TextView(this);
                user_level.setText(jsonData.getString("damage_level"));
                user_level.setLayoutParams(view_layout);

                TextView user_why = new TextView(this);
                user_why.setText(jsonData.getString("reason"));
                user_why.setLayoutParams(view_layout);

                tr.addView(user_room_no);
                tr.addView(user_level);
                tr.addView(user_uti);
                tr.addView(user_why);
                tl_userList.addView(tr);
            }
        } catch (Exception e){
            Log.e("log_tag", e.toString());
        }
    }



}