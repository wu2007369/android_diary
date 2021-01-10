package com.example.wuzhiming.myapplication.jetpacktest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

// 管理 UI Data
public class MainViewModel extends /*ViewModel*/AndroidViewModel {


    // UI  Data
    // 传统方式的 数据
    // private String phoneInfo = "";

    // LiveData   感应改变数据
    private MutableLiveData<String> phoneInfo;

    private Context context;


    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    public MutableLiveData<String> getPhoneInfo() {
        if (phoneInfo == null) {
            phoneInfo = new MutableLiveData<>();

            phoneInfo.setValue(""); // 默认值
        }
        return phoneInfo;
    }

    /**
     * 输入
     * @param number
     */
    public void appendNumber(String number) {
        phoneInfo.setValue(phoneInfo.getValue() + number);
    }

    /**
     * 删除
     */
    public void backspaceNumber() {
        int length = phoneInfo.getValue().length();
        if (length > 0) {
            phoneInfo.setValue(phoneInfo.getValue().substring(0, phoneInfo.getValue().length() - 1));
        }
    }

    /**
     * 清空
     */
    public void clear() {
        phoneInfo.setValue("");
    }

    public void callPhone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneInfo.getValue()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);      /// 加标记   非Activity启动，必须
        context.startActivity(intent);
    }

    // 10000行代码
    // ...
}
