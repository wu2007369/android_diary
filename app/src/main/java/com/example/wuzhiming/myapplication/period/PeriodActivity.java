package com.example.wuzhiming.myapplication.period;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wuzhiming.myapplication.base.BaseActivity;
import com.example.wuzhiming.myapplication.Main2Activity;
import com.example.wuzhiming.myapplication.Main5Activity;
import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.TranslucentActivity;

public class PeriodActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);

        Button btn1 = findViewById(R.id.btn);
        btn1.setOnClickListener(this);

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        findViewById(R.id.btn4)
        .setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Intent intent = new Intent(this, TranslucentActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                Intent intent2 = new Intent(this, Main2Activity.class);
                startActivity(intent2);
                break;

            case R.id.btn3:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("csdcdscs");
                builder.show();
                break;
            case R.id.btn4:
                startActivity(new Intent(this, Main5Activity.class));
                overridePendingTransition(R.anim.pop_bottom_in_normal, R.anim.pop_bottom_out_normal);
                break;
            case R.id.btn5:
                break;
        }
    }
}