package com.example.wuzhiming.myapplication.textexpansion;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.utils.DpPxExchange;
import com.example.wuzhiming.myapplication.utils.PhoneUtils;

public class TextActivity extends AppCompatActivity {

    private TextView text;
    private TextView text2;
    private TextView text3;
    private TextView imageText1;
    private TextView imageText2;
    private String TAG="TextActivity";

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

/*        spannableBuilder.setSpan(colorSpan, 14, a.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableBuilder.setSpan(colorSpan, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        spannableBuilder.setSpan(CharacterStyle.wrap(colorSpan), 14, a.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableBuilder.setSpan(CharacterStyle.wrap(colorSpan), 1, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        text3=findViewById(R.id.html_text4);
        // 不设置点击不生效
//        text3.setMovementMethod(LinkMovementMethod.getInstance());
        text3.setText(spannableBuilder);


        Switch switchBtn = (Switch) findViewById(R.id.switch1);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG,"isChecked="+isChecked);
            }
        });




        imageText1=findViewById(R.id.text_with_image1);
        addImageToText();

        imageText2=findViewById(R.id.text_with_image2);
        addCustomeViewToText();
    }

    private void addCustomeViewToText() {

        //手动画一个标签view，合入到textView里
        TextView tag = new TextView(this);
        tag.setText("自定义标签内容");
        tag.setTextSize(10);
        tag.setTextColor(this.getResources().getColor(R.color.white));
        tag.setGravity(Gravity.CENTER);
        tag.setBackgroundResource(R.drawable.bg_corner4dp_ff6633_solid);
        tag.buildDrawingCache();
        layoutView(tag, 80, 15);
        Bitmap bitmap = Bitmap.createBitmap(tag.getWidth(), tag.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        tag.draw(canvas);
        Drawable drawable = new BitmapDrawable(bitmap);
        drawable.setBounds(0, 0, DpPxExchange.Dp2Px(this, 40), DpPxExchange.Dp2Px(this, 15));
        ImageSpan TagSpan = new ImageSpan(drawable);
        SpannableString goodsNameSpanStr = new SpannableString("  " + "这里是自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字，自定义view+文字");
        goodsNameSpanStr.setSpan(TagSpan, 0, 1, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        imageText2.setText(goodsNameSpanStr);
    }

    private void addImageToText(){
        Drawable mDrable = getResources().getDrawable(R.drawable.bg);
        mDrable.setBounds(0, 0, DpPxExchange.Dp2Px(this,64),DpPxExchange.Dp2Px(this,96));
        ImageSpan mImageSpan = new ImageSpan(mDrable);
        SpannableString SpanStr = new SpannableString(" 这里是图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字");
        SpanStr.setSpan(mImageSpan, 0, 1, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        imageText1.setText(SpanStr);
    }

    private void layoutView(View view, int width, int height) {
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(DpPxExchange.Dp2Px(this, width), View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(DpPxExchange.Dp2Px(this, height), View.MeasureSpec.EXACTLY);
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}