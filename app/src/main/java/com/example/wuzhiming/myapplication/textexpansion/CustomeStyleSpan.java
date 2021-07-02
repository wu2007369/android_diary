package com.example.wuzhiming.myapplication.textexpansion;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.StyleSpan;

import androidx.annotation.NonNull;

public class CustomeStyleSpan extends StyleSpan {
    private final int mStyle2;

    public CustomeStyleSpan(int style) {
        super(style);
        mStyle2=style;
    }

    public CustomeStyleSpan(@NonNull  Parcel src) {
        super(src);
        mStyle2=src.readInt();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        apply2(ds, mStyle2);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        apply2(paint, mStyle2);
    }

    private static void apply2(Paint paint, int style) {
        Typeface old = paint.getTypeface();

        Typeface tf;
        if (old == null) {
            tf = Typeface.defaultFromStyle(style);
        } else {
            tf = Typeface.create(old, style);
        }

/*        if ((style & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }else {
            paint.setFakeBoldText(false);
        }*/

        if ((style & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }else {
            paint.setTextSkewX(0f);
        }

        paint.setTypeface(tf);
    }
}
