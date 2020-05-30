package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Store extends AppCompatActivity {

    private EditText et_storeroom;
    private EditText et_storemoney;
    private Button bnt_storesend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        et_storeroom = (EditText) findViewById(R.id.et_storeroom);
        et_storemoney = (EditText) findViewById(R.id.et_storemoney);
        bnt_storesend = (Button) findViewById(R.id.bnt_storesend);

        bnt_storesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Store.this, managerActivity.class);
                startActivity(intent);
            }
        });
    }

}
