package com.example.wuzhiming.myapplication.immerse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wuzhiming.myapplication.R;

public class ImmerseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immerse);

        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        findViewById(R.id.btn1).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn5:
                startActivity(new Intent(this, Main4Activity.class));
                break;

            case R.id.btn1:
                startActivity(new Intent(this, ImmerseWebviewActivity.class));
                break;
        }
    }
}