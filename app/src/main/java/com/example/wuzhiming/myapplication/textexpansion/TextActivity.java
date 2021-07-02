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
import android.os.Parcel;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    private String TAG = "TextActivity";
    private RadioGroup rg;


    BulletSpan bulletSpan, bulletSpan2, bulletSpan3;

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            Log.e("------->", "点击了");
            String content = ((TextView) widget).getText().toString();
            Toast.makeText(TextActivity.this, "点击了" + content, Toast.LENGTH_SHORT).show();
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

        setHeader();
        text = findViewById(R.id.html_text2);
        String value = getString(R.string.html_value_and_placeholder, "我要替换");
        text.setText(Html.fromHtml(value));

        text2 = findViewById(R.id.html_text3);
        text2.setText(getText(R.string.html_value));

        String a = "1234567890abcdefghijklmn";
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(a);
        // 单独设置字体颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#3072F6"));

/*        spannableBuilder.setSpan(colorSpan, 14, a.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableBuilder.setSpan(colorSpan, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        spannableBuilder.setSpan(CharacterStyle.wrap(colorSpan), 14, a.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableBuilder.setSpan(CharacterStyle.wrap(colorSpan), 1, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        spannableBuilder.setSpan(new TextClick(), 14, a.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableBuilder.setSpan(new TextClick(), 1, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        text3 = findViewById(R.id.html_text4);

        text3.setMovementMethod(LinkMovementMethod.getInstance());// 不设置点击不生效
        text3.setHighlightColor(getResources().getColor(android.R.color.transparent));//不设置会有背景色
        text3.setText(spannableBuilder);


        Switch switchBtn = (Switch) findViewById(R.id.switch1);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "isChecked=" + isChecked);
            }
        });


        imageText1 = findViewById(R.id.text_with_image1);
        addImageToText();

        imageText2 = findViewById(R.id.text_with_image2);
        addCustomeViewToText();


        rg = (RadioGroup) findViewById(R.id.radio_group); //定义radioGroup控件
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {//表示RadioGroup中的radioButton状态切换时触发的监听
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId); //选中radioButton的对象资源id获取radioButton对象
                String str = rb.getText().toString();//获取选中radioButton的文本
                Toast.makeText(TextActivity.this, "Hello" + str, Toast.LENGTH_SHORT).show();
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


        findViewById(R.id.btnEditAddBullet).setOnClickListener(v -> editAddBullet2(findViewById(R.id.edit2)));
        findViewById(R.id.btnRemoveEditAddBullet).setOnClickListener(v -> removeAddBullet2(findViewById(R.id.edit2)));

        EditText typefacetest=findViewById(R.id.typefaceTest);
        Editable edit = typefacetest.getText();

        findViewById(R.id.typefaceSpan1).setOnClickListener(v -> {
            edit.setSpan(new StyleSpan(Typeface.NORMAL),0,edit.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        });
        findViewById(R.id.typefaceSpan2).setOnClickListener(v -> {
            edit.setSpan(new StyleSpan(Typeface.BOLD),0,edit.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        });
        findViewById(R.id.typefaceSpan3).setOnClickListener(v -> {
            edit.setSpan(new StyleSpan(Typeface.ITALIC),0,edit.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        });
        findViewById(R.id.typefaceSpan4).setOnClickListener(v -> {
            edit.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),0,edit.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        });

        findViewById(R.id.customeSpan1).setOnClickListener(v->{
            edit.setSpan(new CustomeStyleSpan(Typeface.NORMAL),0,edit.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        });
        findViewById(R.id.customeSpan2).setOnClickListener(v->{
            edit.setSpan(new CustomeStyleSpan(Typeface.BOLD),0,edit.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        });
        findViewById(R.id.customeSpan3).setOnClickListener(v->{
            edit.setSpan(new CustomeStyleSpan(Typeface.ITALIC),0,edit.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        });
        findViewById(R.id.customeSpan4).setOnClickListener(v->{
            edit.setSpan(new CustomeStyleSpan(Typeface.BOLD_ITALIC),0,edit.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        });
    }

    private void setHeader() {
        Window window = this.getWindow();
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色(任意颜色)
        window.setStatusBarColor(getColor(R.color.white));
        //设置系统状态栏处于可见状态
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//状态栏白字
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//状态栏 黑字
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

    private void addImageToText() {
        Drawable mDrable = getResources().getDrawable(R.drawable.bg);
        mDrable.setBounds(0, 0, DpPxExchange.Dp2Px(this, 64), DpPxExchange.Dp2Px(this, 96));
        ImageSpan mImageSpan = new ImageSpan(mDrable);
        SpannableString SpanStr = new SpannableString(" 这里是图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字图片加文字");
        SpanStr.setSpan(mImageSpan, 0, 1, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        imageText1.setText(SpanStr);
    }

    /**
     * 完成view的绘制自身
     *
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

    //有两个缺：会便宜内容、api要求28以上
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void editAddBullet(View v) {
        if (v instanceof EditText) {
            Editable text = ((EditText) v).getText();
            bulletSpan = new BulletSpan(24, Color.BLACK, 10);
            bulletSpan2 = new BulletSpan(24, Color.BLACK, 10);
            bulletSpan3 = new BulletSpan(24, Color.BLACK, 10);
            text.setSpan(bulletSpan, 0, 0, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            text.setSpan(bulletSpan, 4, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            text.setSpan(bulletSpan, 8, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
    }

    private void removeAddBullet(View v) {
        if (v instanceof EditText) {
            Editable text = ((EditText) v).getText();
            text.removeSpan(bulletSpan);
//            text.removeSpan(bulletSpan2);
//            text.removeSpan(bulletSpan3);

        }
    }

    private void removeAddBullet2(EditText viewById) {
        int length = viewById.getText().length();
        if (length==0){
            return;
        }
        int[] bounds = getBoundInSelection(viewById);
//        if (bounds[0] == 0 && bounds[1] == length) {
//            removeBullet2All(viewById);
//        } else {
            removeBullet2Choosen(viewById, bounds[0], bounds[1]);
//        }

    }

    private void removeBullet2Choosen(EditText viewById, int bound, int bound1) {
        Editable editText = viewById.getText();
        char[] chars = editText.toString().toCharArray();
        for (int i=bound1-1;i>=bound;i--){
            if (chars[i]=='●'){
                editText.delete(i,i+1);
            }
        }
/*        Editable editText = viewById.getText();
        String content = editText.toString();
        String originalContent = content.substring(bound,bound1);
        String newContent = originalContent.replaceAll("●", "");
        content=content.replace(originalContent,newContent);
        viewById.setText(content);*/
    }

    private void removeBullet2All(EditText viewById) {
        Editable editText = viewById.getText();
        char[] chars = editText.toString().toCharArray();
        for (int i=chars.length-1;i>=0;i--){
            if (chars[i]=='●'){
                editText.delete(i,i+1);
            }
        }
        /*        String content = editText.toString();
        content = content.replaceAll("●", "");
        viewById.setText(content);*/
    }

    private void editAddBullet2(EditText viewById) {
        int length = viewById.getText().length();
        if (length==0){
            return;
        }
        int[] bounds = getBoundInSelection(viewById);
//        if (bounds[0] == 0 && bounds[1] == length) {
//            addBullet2All(viewById);
//        } else {
            addBullet2Choosen(viewById, bounds[0], bounds[1]);
//        }
    }

    /**
     * 判断光标选中部分的 所在内容段的头尾索引，以\n作为边界标准
     * @param viewById
     * @return
     */
    private int[] getBoundInSelection(EditText viewById) {
        int start = viewById.getSelectionStart();
        int end = viewById.getSelectionEnd();
        String content = viewById.getText().toString();
        char[] chars = content.toCharArray();
        if (start == content.length() || chars[start]=='\n') {
            //如果start指针处于句尾或者 已经指在了 \n上
            start--;
        }
        //start指针向前开始寻找\n
        while (start > 0) {
            if (chars[start] == '\n') {
                //如果找到，则向后回退到\n之后一格
                start++;
                break;
            }
            start--;
        }
        //end指针向后开始寻找\n
        while (end < content.length()) {
            if (chars[end] == '\n') {
                //如果找到，则停止寻找
                break;
            }
            end++;
        }
        return new int[]{start, end};
    }

    /**
     * 给editText选中部分的行，添加 点
     *
     * @param viewById
     * @param start
     * @param end
     */
    private void addBullet2Choosen(EditText viewById, int start, int end) {
        if (start>=end){
            return;
        }
        Editable editText = viewById.getText();
        if (editText.toString().substring(start, end).contains("●")){
            //给这个按钮双用性，既可增加点，又能删除点
            removeBullet2Choosen(viewById,start,end);
            return;
        }
        char[] chars = editText.toString().substring(start, end).toCharArray();

        int i = 0, offset = 0;
        for (; i < chars.length; i++) {
            if (chars[i] == '\n' && i != (chars.length - 1) && chars[i+1]!='\n') {
                //避免在结尾添加点，避免在连续的\n符号间添加点，做到只在文字中间的单个\n符号 之后添加点
                offset++;
                editText.insert(i + offset, "●");
            }
        }
        editText.insert(start, "●");//粗
    }

    /**
     * 给全局edittext添加 点
     *
     * @param viewById
     */
    private void addBullet2All(EditText viewById) {
        addBullet2Choosen(viewById, 0, viewById.getText().length() - 1);
    }
}