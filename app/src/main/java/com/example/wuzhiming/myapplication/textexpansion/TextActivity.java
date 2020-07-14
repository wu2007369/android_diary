package com.example.wuzhiming.myapplication.textexpansion;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.example.wuzhiming.myapplication.R;

public class TextActivity extends AppCompatActivity {

    private TextView text;
    private TextView text2;
    private TextView text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        text=findViewById(R.id.html_text2);
        String value=getString(R.string.html_value_and_placeholder,"我要替换");
        text.setText(Html.fromHtml(value));

        text2=findViewById(R.id.html_text3);
        text2.setText(getText(R.string.html_value));

        String a="1234567890abcdefghijklmn";
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(a);
        // 单独设置字体颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#3072F6"));
        spannableBuilder.setSpan(colorSpan, 14, a.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 不设置点击不生效
        text3=findViewById(R.id.html_text4);
        text3.setMovementMethod(LinkMovementMethod.getInstance());
        text3.setText(spannableBuilder);
    }
}