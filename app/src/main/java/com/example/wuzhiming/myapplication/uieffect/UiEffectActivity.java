package com.example.wuzhiming.myapplication.uieffect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.wuzhiming.myapplication.R;

public class UiEffectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_effect);

        findViewById(R.id.btn1).setOnClickListener(v->{
            startActivity(new Intent(this,ShadowActivity.class));
        });

    }
}