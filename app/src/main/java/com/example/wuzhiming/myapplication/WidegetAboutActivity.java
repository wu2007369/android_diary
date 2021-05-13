package com.example.wuzhiming.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.wuzhiming.myapplication.databinding.ActivityWidegetAboutBinding;

public class WidegetAboutActivity extends AppCompatActivity {

    private String TAG = "WidegetAboutActivity";
    private ActivityWidegetAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityWidegetAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        millisInFuture: 你要倒计时的总时间, 单位ms.
//                countDownInterval: 你要倒计时的间隔时间, 单位ms.
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.showCount.setText("seconds remaining: " + millisUntilFinished / 1000);

            }

            public void onFinish() {
                binding.showCount.setText("done!");
            }
        }.start();
    }
}