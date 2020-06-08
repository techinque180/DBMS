package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class managerActivity extends AppCompatActivity {

    private Button bnt_mansearchdamage;
    private Button bnt_manusecondition;
    private Button bnt_manstore;
    private String account;
    private int flag_usecondition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        setTitle("管理員");
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        flag_usecondition = intent.getIntExtra("flag_usecondition",flag_usecondition);

        bnt_mansearchdamage = (Button) findViewById(R.id.bnt_mansearchdamage);
        bnt_manusecondition = (Button) findViewById(R.id.bnt_manusecondition);
        bnt_manstore = (Button) findViewById(R.id.bnt_manstore);
        flag_usecondition = intent.getIntExtra("flag_usecondition",flag_usecondition);

        bnt_mansearchdamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳到申報紀錄畫面
                Intent intent = new Intent(managerActivity.this, DeclareList.class);
                intent.putExtra("flag_usecondition", flag_usecondition);
                startActivity(intent);//跳到使用狀況畫面
            }
        });

        bnt_manusecondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(managerActivity.this, Usecondition.class);
                intent.putExtra("flag_usecondition", flag_usecondition);
                startActivity(intent);//跳到使用狀況畫面
            }
        });

        bnt_manstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(managerActivity.this, Store.class);
                intent.putExtra("flag_usecondition", flag_usecondition);
                startActivity(intent);//跳到儲值畫面
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(managerActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
