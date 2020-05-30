package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner_identity;
    private EditText et_account;
    private EditText et_passwd;
    private Button btn_login;
    private Button btn_reg;

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


}
