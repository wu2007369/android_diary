package com.example.wuzhiming.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuzhiming.myapplication.databinding.ActivityWidegetAboutBinding;
import com.example.wuzhiming.myapplication.interfa.OnWheelChangedListener;
import com.example.wuzhiming.myapplication.interfa.WheelAdapter;
import com.example.wuzhiming.myapplication.wideget.WheelView;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class WidegetAboutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String TAG = "WidegetAboutActivity";
    private ActivityWidegetAboutBinding binding;

    private WheelAdapter adapter = new WheelAdapter() {
        @Override
        public int getItemsCount() {
            return 24;
        }

        @Override
        public String getItem(int var1) {
            return var1 + "";
        }

        @Override
        public int getMaximumLength() {
            return 2;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWidegetAboutBinding.inflate(getLayoutInflater());
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

        binding.datePickBtn.setOnClickListener(v -> {
            //获取日历的一个实例，里面包含了当前的时分秒
            Calendar calendar = Calendar.getInstance();
            //构建一个日期对话框，该对话框已经集成了日期选择器
            //DatePickerDialog的第二个构造参数指定了日期监听器
            DatePickerDialog dialog = new DatePickerDialog(this, this
                    , calendar.get(Calendar.YEAR)//年份
                    , calendar.get(Calendar.MONTH)//月份
                    , calendar.get(Calendar.DAY_OF_MONTH))//日子
                    ;
//            dialog.getDatePicker().
            hideDaySpiner(dialog.getDatePicker());
            //把日期对话框显示在界面上
            dialog.show();

        });


        shwoWheelView();

        hideDaySpiner(binding.datePicker);


        showTabLayout();
        showTabLayout2();//填充满与滚动的区别
        showTabLayout3();//是否填充满 的indicator长短区别
        showTabLayout4();//设置tabIndicator 为直接的drawble，其余无区别，但是颜色被着色，高度有变化
        showTabLayout5();//设置fullwidth为false；写死drawable长度，则indicator长短可控,距上的距离也可控。左右边距也同理,可设置gravity。但是颜色被着色
        showTabLayout6();//在5的基础上，设置fullwidth为true。居然会着色取消
        showTabLayout7();//6的基础上，改为滚动
        showTabLayout8();//2的基础上，改为滚动
        showTabLayout9();//3的基础上，改为滚动
        //如果要和viewpager fragment 联动，还可以使用官方支持的TabLayoutMediator

        showTabLayout10();//直接全部使用customeView，可以实现完全的自定义效果，唯一缺点是没有indicatior的滑动效果

    }

    private void showTabLayout() {
        String mTitles[] = {
                "上海", "头条推荐", "生活", "娱乐八卦", "体育",
                "段子", "美食", "电影", "科技", "搞笑",
                "社会", "财经", "时尚", "汽车", "军事",
                "小说", "育儿", "职场", "萌宠", "游戏",
                "健康", "动漫", "互联网"};

        //TabLayout的基本使用
//        binding.tablayout.noTint=true;


        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = new TextView(WidegetAboutActivity.this);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                textView.setTextColor(getResources().getColor(R.color.color_FF6633));
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                textView.setText(tab.getText());
                tab.setCustomView(textView);
/*
  无效
  text.setTextColor(ContextCompat.getColor(WidegetAboutActivity.this, R.color.color_FF6633));
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
/*
                无效
                text.setTextColor(ContextCompat.getColor(WidegetAboutActivity.this, R.color.black));
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                text.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));*/
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < 8; i++) {
            TabLayout.Tab tab = binding.tablayout.newTab();
            tab.setTag(i);
            tab.setText(mTitles[i]);
            binding.tablayout.addTab(tab);
        }

/*        try {
            Field field1 = binding.tablayout.getClass().getDeclaredField("slidingTabIndicator");
            field1.setAccessible(true);
            field1.get(binding.tablayout);

            Field field2 =Class.forName("com.google.android.material.tabs.TabLayout$SlidingTabIndicator").getDeclaredField("selectedIndicatorPaint");
            field2.setAccessible(true);
            field2.set(field1, null);
        }catch (Exception e){
            Log.e("test",e.getMessage());
        }*/
//        binding.tablayout.setSelectedTabIndicator(getDrawable(R.drawable.layer_tab_indicator));
    }


    private void showTabLayout2() {
        setTabData(binding.tablayout2);
    }

    private void showTabLayout3() {
        setTabData(binding.tablayout3);
    }

    private void showTabLayout4() {
        setTabData(binding.tablayout4);
    }

    private void showTabLayout5() {
        setTabData(binding.tablayout5);
    }

    private void showTabLayout6() {
        setTabData(binding.tablayout6);
    }

    private void showTabLayout7() {
        setTabData(binding.tablayout7);
    }

    private void showTabLayout8() {
        setTabData(binding.tablayout8);
    }

    private void showTabLayout9() {
        setTabData(binding.tablayout9);
    }

    private void showTabLayout10() {
        setTabData(binding.tablayout10);
        TabLayout.Tab tab1 = binding.tablayout10.getTabAt(0).setCustomView(R.layout.base_view_custom_tab_item);
        TabLayout.Tab tab2=binding.tablayout10.getTabAt(1).setCustomView(R.layout.base_view_custom_tab_item);
        AppCompatTextView   text = tab1.getCustomView().findViewById(R.id.tv_text);
                text.setText("casdcascsacas");
        AppCompatTextView   text2 = tab2.getCustomView().findViewById(R.id.tv_text);
        text2.setText("casdcascsacaxasxasxasxs");
        binding.tablayout10.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabTextState(tab.getCustomView().findViewById(R.id.tv_text),
                        tab.getCustomView().findViewById((R.id.iv_indicator)),
                        Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabTextState(tab.getCustomView().findViewById(R.id.tv_text),
                        tab.getCustomView().findViewById((R.id.iv_indicator)),
                        Typeface.NORMAL);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.tablayout10.selectTab(tab2);
    }
    private void setTabTextState(TextView textInner,
                                 ImageView indicatorInner,
                                 Integer tf) {
        textInner.setTypeface(Typeface.defaultFromStyle(tf));
        if (tf == Typeface.NORMAL) {
            textInner.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            indicatorInner.setVisibility(View.INVISIBLE);
        } else {
            textInner.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            indicatorInner.setVisibility(View.VISIBLE);
        }
    }

    private void setTabData(TabLayout tablayout) {
        String mTitles[] = {
                "上海", "头条推荐"};
        //TabLayout的基本使用
//        tablayout.noTint=true;
        for (int i = 0; i < 2; i++) {
            TabLayout.Tab tab = tablayout.newTab();
            tab.setTag(i);
            tab.setText(mTitles[i]);
            tablayout.addTab(tab);
        }
    }

    private void hideDaySpiner(DatePicker datePicker) {
        //        binding.datePicker.findViewById(com.android.internal.R.id.day).setVisibility(View.GONE);
        View ll = datePicker.getChildAt(0);
        if (ll instanceof LinearLayout) {
            View spiners = ((LinearLayout) ll).getChildAt(0);
            if (spiners instanceof LinearLayout) {
                if (((LinearLayout) spiners).getChildCount() == 3) {
                    ((LinearLayout) spiners).getChildAt(2).setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String desc = String.format("您选择的日期是：%s年%s月%s日", year, month + 1, dayOfMonth);
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();

    }

    private void shwoWheelView() {
        binding.wheelView.setAdapter(adapter);
//        binding.wheelView.setAdapter(new NumericWheelAdapter(2000,2100));
        binding.wheelView.setCyclic(true);
        binding.wheelView.setLabel("");
        binding.wheelView.setCurrentItem(0);
        binding.wheelView.TEXT_SIZE = 60;
        binding.wheelView.setSelectColor(ContextCompat.getColor(this, R.color.color_FF6633));
        binding.wheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView var1, int var2, int var3) {
                Log.e("onChanged", "old=" + var2 + ";new=" + var3 + ";currentItem=" + var1.getCurrentItem());
            }
        });
    }

}