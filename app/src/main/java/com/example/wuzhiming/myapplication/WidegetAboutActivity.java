package com.example.wuzhiming.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.wuzhiming.myapplication.databinding.ActivityWidegetAboutBinding;

import java.util.Calendar;

public class WidegetAboutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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

        binding.datePickBtn.setOnClickListener(v->{
            //获取日历的一个实例，里面包含了当前的时分秒
            Calendar calendar = Calendar.getInstance();
            //构建一个日期对话框，该对话框已经集成了日期选择器
            //DatePickerDialog的第二个构造参数指定了日期监听器
            DatePickerDialog dialog = new DatePickerDialog(this,this
                    ,calendar.get(Calendar.YEAR)//年份
                    ,calendar.get(Calendar.MONTH)//月份
                    ,calendar.get(Calendar.DAY_OF_MONTH))//日子
                    ;

/*            try {
                java.lang.reflect.Field[] datePickerDialogFields = dialog.getClass().getDeclaredFields();
                for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                    if (datePickerDialogField.getName().equals("mDatePicker")) {
                        datePickerDialogField.setAccessible(true);
                        DatePicker datePicker = (DatePicker) datePickerDialogField.get(dialog);
                        java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                        for (java.lang.reflect.Field datePickerField : datePickerFields) {
                            Log.i("test", datePickerField.getName());
                            if ("mDaySpinner".equals(datePickerField.getName())) {
                                datePickerField.setAccessible(true);
                                Object dayPicker = datePickerField.get(datePicker);
                                ((View) dayPicker).setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {
            }*/

            //把日期对话框显示在界面上
            dialog.show();

        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String desc = String.format("您选择的日期是：%s年%s月%s日",year,month+1,dayOfMonth);
        Toast.makeText(this,desc, Toast.LENGTH_SHORT).show();

    }
}