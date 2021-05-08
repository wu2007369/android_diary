package com.example.wuzhiming.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wuzhiming.myapplication.dialog.VerificationScanCodeOrderDialog;
import com.example.wuzhiming.myapplication.textexpansion.TextActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DialogTestActivity extends AppCompatActivity {

    private String TAG="DialogTestActivity";

    DateFormat format= DateFormat.getDateTimeInstance();
    Calendar calendar= Calendar.getInstance(Locale.CHINA);
    private VerificationScanCodeOrderDialog mDialog;

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            Log.e("------->", "点击了");
            String content = ((TextView) widget).getText().toString();
            Toast.makeText(DialogTestActivity.this,"点击了"+content,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
//            Toast.makeText(TextActivity.this,"点击了"+ds,Toast.LENGTH_SHORT).show();
//            ds.setColor(ds.linkColor);
//            ds.setUnderlineText(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test2);

        findViewById(R.id.btn1).setOnClickListener(v->tipDialog());
        findViewById(R.id.btn7).setOnClickListener(v->tipDialog2());
        findViewById(R.id.btn2).setOnClickListener(v->itemListDialog());
        findViewById(R.id.btn3).setOnClickListener(v->singleChoiceDialog());
        findViewById(R.id.btn4).setOnClickListener(v->multiChoiceDialog());
        findViewById(R.id.btn5).setOnClickListener(v->showDateDialog());
        findViewById(R.id.btn6).setOnClickListener(v->showTimeDialog());
        findViewById(R.id.btn8).setOnClickListener(v->showCustomeDialog());
        findViewById(R.id.btn9).setOnClickListener(v->showWindowParamsDialog());
        findViewById(R.id.btn10).setOnClickListener(v->showDialogContentClick());

    }

    private void showDialogContentClick() {
        final TextView message = new TextView(this);
        // i.e.: R.string.dialog_message =>
        // "Test this dialog following the link to dtmilano.blogspot.com"
        String a="1234567890abcdefghijklmn";
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(a);
        // 单独设置字体颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#3072F6"));

/*        spannableBuilder.setSpan(colorSpan, 14, a.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableBuilder.setSpan(colorSpan, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        spannableBuilder.setSpan(CharacterStyle.wrap(colorSpan), 14, a.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableBuilder.setSpan(CharacterStyle.wrap(colorSpan), 1, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableBuilder.setSpan(new TextClick(), 14, a.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableBuilder.setSpan(new TextClick(), 1, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        message.setHighlightColor(getResources().getColor(android.R.color.transparent));//不设置会有背景色
        message.setMovementMethod(LinkMovementMethod.getInstance());
        message.setText(spannableBuilder);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage(spannableBuilder);
//        builder.setView(message);
        builder.setCancelable(false);            //点击对话框以外的区域是否让对话框消失

        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogTestActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        dialog.show();                              //显示对话框
        TextView textView = dialog.findViewById(android.R.id.message);
        textView.setHighlightColor(getResources().getColor(android.R.color.transparent));//不设置会有背景色
        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        textView.setText(spannableBuilder);
    }

    private void showWindowParamsDialog() {
        AlertDialog mWindowDialog = tipDialog();
        //调整明暗度的代码一定要在show()之后执行
        WindowManager.LayoutParams lp = mWindowDialog.getWindow().getAttributes();
        //调整明暗度，float值，完全透明不变暗是0.0f，完全变暗不透明是1.0f
        lp.dimAmount=0.65f;
        //必须要设置回去
        mWindowDialog.getWindow().setAttributes(lp);
        //根据谷歌文档，给对应的Window添加FLAG_DIM_BEHIND标志位，dimAmount值才有效。
        mWindowDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void showCustomeDialog() {
        if (null == mDialog) {
            mDialog = new VerificationScanCodeOrderDialog(
                    this, VerificationScanCodeOrderDialog.DialogType.MARKET, "content");
        }
        mDialog.show();
    }

    private void showTimeDialog() {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( this,0,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.i(TAG,"hourOfDay：" + hourOfDay + "; minute:" + minute);
                        Log.i(TAG,"您选择了：" + hourOfDay + "时" + minute  + "分");
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();

    }

    private void showDateDialog() {
// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                Log.i(TAG,"year：" + year + "; monthOfYear" + monthOfYear + "; dayOfMonth" + dayOfMonth);
                Log.i(TAG,"您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
            }
        };
        new DatePickerDialog(this, 0, listener
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    /**
     * 提示对话框
     */
    public AlertDialog  tipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage("这是一个普通对话框，");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);            //点击对话框以外的区域是否让对话框消失

        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogTestActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogTestActivity.this, "你点击了取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        //设置中立按钮
        builder.setNeutralButton("保密", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogTestActivity.this, "你选择了中立", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事件
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.e(TAG, "对话框显示了");
            }
        });
        //对话框消失的监听事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e(TAG, "对话框消失了");
            }
        });
        dialog.show();                              //显示对话框


        //改变dialog按钮颜色
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#fd8d99"));

        return dialog;
    }

    public void tipDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage("这是一个普通对话框，");
        builder.setCancelable(false);            //点击对话框以外的区域是否让对话框消失

        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogTestActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        dialog.show();                              //显示对话框
    }

    /**
     * 列表对话框
     */
    private void itemListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);
        builder.setTitle("选择你喜欢的课程：");
        builder.setCancelable(true);
        final String[] lesson = new String[]{"语文", "数学", "英语", "化学", "生物", "物理", "体育"};
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setIcon(R.mipmap.ic_launcher_round)
                .setItems(lesson, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "你选择了" + lesson[which], Toast.LENGTH_SHORT).show();
                    }
                }).create();
        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();     //创建AlertDialog对象
        dialog.show();                              //显示对话框
    }

    /**
     * 单选对话框
     */
    public void singleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);

        builder.setTitle("你现在居住地是：");
        final String[] cities = {"北京", "上海", "广州", "深圳", "杭州", "天津", "成都"};

        final int[] chedkedItem = {2};
        builder.setSingleChoiceItems(cities, chedkedItem[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "你选择了" + cities[which], Toast.LENGTH_SHORT).show();
                chedkedItem[0] = which;
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();                           //显示对话框
    }

    /**
     * 复选对话框
     */
    public void multiChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);
        builder.setTitle("请选择你喜欢的颜色：");
        final String[] colors = {"红色", "橙色", "黄色", "绿色", "蓝色", "靛色", "紫色"};
        final List<String> myColors = new ArrayList<>();

        builder.setMultiChoiceItems(colors, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    myColors.add(colors[which]);
                } else {
                    myColors.remove(colors[which]);
                }
            }
        });

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = "";
                for (String color : myColors) {
                    result += color + "、";
                }
                Toast.makeText(getApplicationContext(), "你选择了: " + result, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myColors.clear();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        dialog.show();                               //显示对话框

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                Color.parseColor("#D0021B"));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                Color.parseColor("#3072F6"));
    }

}