package com.example.wuzhiming.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wuzhiming.myapplication.coordinatorlayout.Main6Activity;
import com.example.wuzhiming.myapplication.coordinatorlayout.Main8Activity;
import com.example.wuzhiming.myapplication.coordinatorlayout.Main9Activity;
import com.example.wuzhiming.myapplication.customCamera.Main11Activity;
import com.example.wuzhiming.myapplication.recyexpansion.Main13Activity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn);
        btn1.setOnClickListener(this);

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);

        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        Button btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(this);

        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(this);

        Button btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(this);

        Button btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(this);

        findViewById(R.id.btn10).setOnClickListener(this);
        findViewById(R.id.btn11).setOnClickListener(this);
        findViewById(R.id.btn12).setOnClickListener(this);
        findViewById(R.id.btn13).setOnClickListener(this);
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
                startActivity(new Intent(this,Main3Activity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(this,Main4Activity.class));
                break;

            case R.id.btn6:
                startActivity(new Intent(this,Main5Activity.class));
                overridePendingTransition(R.anim.pop_bottom_in_normal, R.anim.agile_fix_stand);

                break;
            case R.id.btn7:
                startActivity(new Intent(this,Main7Activity.class));
                break;
            case R.id.btn8:
                startActivity(new Intent(this, Main8Activity.class));
                break;
            case R.id.btn9:
                startActivity(new Intent(this, Main6Activity.class));
                break;
            case R.id.btn10:
                startActivity(new Intent(this, Main9Activity.class));
                break;
            case R.id.btn11:
                startActivity(new Intent(this, MainActivity2.class));
                break;
            case R.id.btn12:
                startActivity(new Intent(this, Main11Activity.class));
                break;
            case R.id.btn13:
                startActivity(new Intent(this, Main13Activity.class));
                break;
            default:
                break;
        }
    }
}
