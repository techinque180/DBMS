package com.example.myproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class resident extends AppCompatActivity {

    private Button bnt_record;
    private Button bnt_rent;
    private Button bnt_ressearchbalance;
    private Button bnt_resusecondition;
    private Button bnt_resdamagelevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident);

        bnt_record = (Button) findViewById(R.id.bnt_record);
        bnt_rent = (Button) findViewById(R.id.bnt_rent);
        bnt_ressearchbalance = (Button) findViewById(R.id.bnt_ressearchbalance);
        bnt_resusecondition = (Button) findViewById(R.id.bnt_resusecondition);
        bnt_resdamagelevel = (Button) findViewById(R.id.bnt_resdamagelevel);

        bnt_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resident.this, rentcontentActivity.class);
                startActivity(intent);//跳到租借紀錄畫面
            }
        });

        bnt_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resident.this, Rental.class);
                startActivity(intent);//跳到租借畫面
            }
        });

        bnt_ressearchbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(resident.this)
                        .setTitle("餘額：")
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
                //跳到使用狀況畫面
            }
        });

        bnt_resdamagelevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resident.this, Declare.class);
                startActivity(intent);//跳到申報畫面
            }
        });
    }
}
