package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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

    private String result;

    private String why;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);

        et_reason = (EditText)findViewById(R.id.et_reason) ;



        spinner_uti = (Spinner)findViewById(R.id.spinner_uti);

        final String[] lunch = {"KTV01", "KTV02", "健身房01", "游泳池01", "討論室01", "討論室02", "討論室03", "討論室04"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>( Declare.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner_uti.setAdapter(lunchList);
        utility = spinner_uti.getSelectedItem().toString();
        ///////輸入種類到資料庫////////



        spinner_damageLevel = (Spinner)findViewById(R.id.spinner_damageLevel);

        final Integer[] lunch1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> lunch1List = new ArrayAdapter<>( Declare.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch1);
        spinner_damageLevel.setAdapter(lunch1List);
        level = spinner_damageLevel.getSelectedItem().toString();
        ///////輸入人數到資料庫////////

        btn_declare_go = (Button)findViewById(R.id.btn_declare_go);
        btn_declare_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///////傳原因到設施使用狀況畫面/////////
                why = et_reason.getText().toString();
                ///////Login要改成登入後畫面///////
                Intent intent = new Intent(Declare.this, resident.class);
                //intent.putExtra("why", why);
                startActivity(intent);
            }
        });

        Toast.makeText(this, "已送出", Toast.LENGTH_LONG).show();

    }


}
