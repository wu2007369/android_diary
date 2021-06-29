package com.example.wuzhiming.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.wuzhiming.myapplication.coordinatorlayout.HeaderAnimatorActivity;
import com.example.wuzhiming.myapplication.customCamera.Main11Activity;
import com.example.wuzhiming.myapplication.immerse.ImmerseActivity;
import com.example.wuzhiming.myapplication.intent.IntentActivity;
import com.example.wuzhiming.myapplication.itext.PdfAboutActivity;
import com.example.wuzhiming.myapplication.jetpacktest.JetPackTestActivity;
import com.example.wuzhiming.myapplication.period.PeriodActivity;
import com.example.wuzhiming.myapplication.recyexpansion.Main13Activity;
import com.example.wuzhiming.myapplication.service.ServiceUseActivity;
import com.example.wuzhiming.myapplication.shape.ShapeActivity;
import com.example.wuzhiming.myapplication.textexpansion.TextActivity;
import com.example.wuzhiming.myapplication.uieffect.UiEffectActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn);
        btn1.setOnClickListener(this);


        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(this);

        Button btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(this);

        findViewById(R.id.btn11).setOnClickListener(this);
        findViewById(R.id.btn12).setOnClickListener(this);
        findViewById(R.id.btn13).setOnClickListener(this);
        findViewById(R.id.btn14).setOnClickListener(this);
        findViewById(R.id.btn15).setOnClickListener(this);
        findViewById(R.id.btn16).setOnClickListener(this);
        findViewById(R.id.btn17).setOnClickListener(this);
        findViewById(R.id.btn18).setOnClickListener(this);
        findViewById(R.id.btn19).setOnClickListener(this);
        findViewById(R.id.btn20).setOnClickListener(this);
        findViewById(R.id.btn21).setOnClickListener(this);
        findViewById(R.id.btn22).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Intent intent = new Intent(this, PeriodActivity.class);
                startActivity(intent);
                break;
            case R.id.btn5:
                startActivity(new Intent(this, ImmerseActivity.class));
                break;

            case R.id.btn7:
                startActivity(new Intent(this,Main7Activity.class));
                break;
            case R.id.btn8:
                startActivity(new Intent(this, HeaderAnimatorActivity.class));
                break;
            case R.id.btn11:
                startActivity(new Intent(this, ShapeActivity.class));
                break;
            case R.id.btn12:
                startActivity(new Intent(this, Main11Activity.class));
                break;
            case R.id.btn13:
                startActivity(new Intent(this, Main13Activity.class));
                break;
            case R.id.btn14:
                startActivity(new Intent(this, IntentActivity.class));
                break;
            case R.id.btn15:
                startActivity(new Intent(this, TextActivity.class));
                break;
            case R.id.btn16:
                startActivity(new Intent(this, UiEffectActivity.class));
                break;
            case R.id.btn17:
                startActivity(new Intent(this, DialogTestActivity.class));
                break;
            case R.id.btn18:
                startActivity(new Intent(this, ServiceUseActivity.class));
                break;
            case R.id.btn19:
                startActivity(new Intent(this, JetPackTestActivity.class));
                break;
            case R.id.btn20:
                startActivity(new Intent(this, LoadingTestActivity.class));
                break;
            case R.id.btn21:
                startActivity(new Intent(this, WidegetAboutActivity.class));
                break;
            case R.id.btn22:
                if (requestPermission()){
//                    startActivity(new Intent(this, PdfAboutActivity2.class));
                    startActivity(new Intent(this, PdfAboutActivity.class));
                }
                break;
            default:
                break;
        }
    }


    public Boolean requestPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "申请权限", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,

                    Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return false;
        }else {
            return true;
        }
    }
}
