package com.example.wuzhiming.myapplication.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wuzhiming.myapplication.R;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        findViewById(R.id.btn14).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn14:
                Intent intent2=new Intent();
                ComponentName cn = new ComponentName("com.jinying.mobile",  "com.jinying.mobile.wxapi.WXEntryActivity");
//                ComponentName cn = new ComponentName("net.sourceforge.simcpux",  "net.sourceforge.simcpux.wxapi.WXEntryActivity");
                intent2.setComponent(cn);
                startActivity(intent2);
                break;

            case R.id.btn1:
//                startActivity(new Intent(this,AliasActivity.class));
                startActivity(new Intent(this,JumpActivity.class));
                break;
            default:
                break;
        }
    }
}