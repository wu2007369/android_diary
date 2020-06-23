package com.example.wuzhiming.myapplication.recyexpansion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.diff.DiffActivity;

public class Main13Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);

        findViewById(R.id.btn_diff).setOnClickListener(v->{

            startActivity(new Intent(this, DiffActivity.class));
        });

    }
}