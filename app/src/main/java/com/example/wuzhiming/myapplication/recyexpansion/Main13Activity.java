package com.example.wuzhiming.myapplication.recyexpansion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.wuzhiming.myapplication.Main3Activity;
import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.recyexpansion.diff.DiffActivity;
import com.example.wuzhiming.myapplication.recyexpansion.suspension.WXReadDemoActivity;

public class Main13Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);

        findViewById(R.id.btn4).setOnClickListener(v->{
            startActivity(new Intent(this, Main3Activity.class));

        });

        findViewById(R.id.btn_diff).setOnClickListener(v->{

            startActivity(new Intent(this, DiffActivity.class));
        });

        findViewById(R.id.btn_wxread).setOnClickListener(v->{
            startActivity(new Intent(this, WXReadDemoActivity.class));
        });

        findViewById(R.id.btn_item_clip).setOnClickListener(v->{
            startActivity(new Intent(this, RecyItemClipActivity.class));
        });

        findViewById(R.id.btn_item_decoration).setOnClickListener(v->{
            startActivity(new Intent(this, DecorationTestActivity.class));
        });


    }
}