package com.example.wuzhiming.myapplication.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.wuzhiming.myapplication.R;

public class ServiceUseActivity extends AppCompatActivity {

    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("ServiceUseActivity","onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("ServiceUseActivity","onServiceDisconnected");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_use);

        findViewById(R.id.btn1).setOnClickListener(v->{
            Intent intent=new Intent(this,AIDLService.class);
            bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
        });

        findViewById(R.id.btn2).setOnClickListener(v->{
            unbindService(mServiceConnection);
        });

        findViewById(R.id.btn3).setOnClickListener(v->{
            Intent intent=new Intent();
            intent.setPackage("com.example.wuzhiming.myapplication");//传的是app包名，而不是service类所在的路径包名
            intent.setAction("com.example.wuzhiming.myapplication.action");
            bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
        });
    }
}