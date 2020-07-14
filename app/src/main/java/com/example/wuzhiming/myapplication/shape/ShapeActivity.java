package com.example.wuzhiming.myapplication.shape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wuzhiming.myapplication.R;

public class ShapeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);

        findViewById(R.id.btn11).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn11:
                startActivity(new Intent(this, DottedLineActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, BottomLineActivity.class));
                break;
        }
    }
}