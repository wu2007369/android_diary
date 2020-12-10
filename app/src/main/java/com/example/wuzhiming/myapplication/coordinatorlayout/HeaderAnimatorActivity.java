package com.example.wuzhiming.myapplication.coordinatorlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wuzhiming.myapplication.R;

public class HeaderAnimatorActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_animator);

        Button btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(this);

        Button btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(this);

        findViewById(R.id.btn10).setOnClickListener(this);
        findViewById(R.id.btn11).setOnClickListener(this);
        findViewById(R.id.btn12).setOnClickListener(this);
        findViewById(R.id.btn13).setOnClickListener(this);
        findViewById(R.id.btn14).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn9:
                startActivity(new Intent(this, Main6Activity.class));
                break;
            case R.id.btn10:
                startActivity(new Intent(this, Main9Activity.class));
                break;
            case R.id.btn8:
                Intent intent = new Intent(this, Main8Activity.class);
                intent.putExtra(Main8Activity.LAYOUT_TYPE,1);
                startActivity(intent);
                break;
            case R.id.btn11:
                Intent intent2 = new Intent(this, Main8Activity.class);
                intent2.putExtra(Main8Activity.LAYOUT_TYPE,2);
                startActivity(intent2);
                break;
            case R.id.btn12:
                Intent intent3 = new Intent(this, Main8Activity.class);
                intent3.putExtra(Main8Activity.LAYOUT_TYPE,3);
                startActivity(intent3);
                break;
            case R.id.btn13:
                Intent intent4 = new Intent(this, Main8Activity.class);
                intent4.putExtra(Main8Activity.LAYOUT_TYPE,4);
                startActivity(intent4);
                break;
            case R.id.btn14:
                Intent intent5 = new Intent(this, Main8Activity.class);
                intent5.putExtra(Main8Activity.LAYOUT_TYPE,5);
                startActivity(intent5);
                break;
            case R.id.btn1:
                startActivity(new Intent(this, TitleGradientAnimateActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, PushNestedScrollUseActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, RecyDistanceListenerActivity.class));
                break;
        }
    }
}