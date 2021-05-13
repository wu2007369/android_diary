package com.example.wuzhiming.myapplication.textexpansion;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.utils.DpPxExchange;
import com.example.wuzhiming.myapplication.utils.PhoneUtils;
import com.example.wuzhiming.myapplication.utils.ScreenUtils;

public class TextActivity extends AppCompatActivity {

    private TextView text;
    private TextView text2;
    private TextView text3;
    private TextView imageText1;
    private TextView imageText2;
    private String TAG="TextActivity";
    private RadioGroup rg;

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            Log.e("------->", "点击了");
            String content = ((TextView) widget).getText().toString();
            Toast.makeText(TextActivity.this,"点击了"+content,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
//            Toast.makeText(TextActivity.this,"点击了"+ds,Toast.LENGTH_SHORT).show();
//            ds.setColor(ds.linkColor);
//            ds.setUnderlineText(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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

        spannableBuilder.setSpan(new TextClick(), 14, a.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableBuilder.setSpan(new TextClick(), 1, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        text3=findViewById(R.id.html_text4);

        text3.setMovementMethod(LinkMovementMethod.getInstance());// 不设置点击不生效
        text3.setHighlightColor(getResources().getColor(android.R.color.transparent));//不设置会有背景色
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


        rg=(RadioGroup)findViewById(R.id.radio_group); //定义radioGroup控件
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {//表示RadioGroup中的radioButton状态切换时触发的监听
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId); //选中radioButton的对象资源id获取radioButton对象
                String str = rb.getText().toString();//获取选中radioButton的文本
                Toast.makeText(TextActivity.this,"Hello"+str,Toast.LENGTH_SHORT).show();
            }
        });
/*        RadioButton radio1 = findViewById(R.id.radio1);
        Drawable drawable = radio1.getButtonDrawable();
        drawable.setBounds(0, 0, ScreenUtils.dp2px(this, 10), ScreenUtils.dp2px(this, 10));
        radio1.setButtonDrawable(drawable);*/
/*        Drawable[] drawables = radio1.getCompoundDrawables();
        Rect r = new Rect(0, 0, ScreenUtils.dp2px(this, 10), ScreenUtils.dp2px(this, 10));
        drawables[0].setBounds(r);
        radio1.setCompoundDrawables(drawables[0], null, null, null);*/


        TextView ttfTest = findViewById(R.id.ttfTest);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_light);
        ttfTest.setTypeface(typeface);

        TextView ttfTest2 = findViewById(R.id.ttfTest2);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.roboto_thin);
        ttfTest2.setTypeface(typeface2);

        TextView ttfTest3 = findViewById(R.id.ttfTest3);
        Typeface typeface3 = ResourcesCompat.getFont(this, R.font.roboto_medium);
        ttfTest3.setTypeface(typeface3);

        TextView ttfTest4 = findViewById(R.id.ttfTest4);
        Typeface typeface4 = ResourcesCompat.getFont(this, R.font.roboto_regular);
        ttfTest4.setTypeface(typeface4);
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

    /**
     * 完成view的绘制自身
     * @param view
     * @param width
     * @param height
     */
    private void layoutView(View view, int width, int height) {
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(DpPxExchange.Dp2Px(this, width), View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(DpPxExchange.Dp2Px(this, height), View.MeasureSpec.EXACTLY);
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}