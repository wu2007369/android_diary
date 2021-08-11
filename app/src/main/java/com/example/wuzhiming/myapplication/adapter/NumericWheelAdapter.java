package com.example.wuzhiming.myapplication.adapter;

import com.example.wuzhiming.myapplication.interfa.WheelAdapter;

/**
 * @Author: wuzm
 * @CreateDate: 2021/8/6 3:22 下午
 * @Description: 类作用描述
 */
public class NumericWheelAdapter implements WheelAdapter {
    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE = 0;
    private int minValue;
    private int maxValue;
    private String format;

    public NumericWheelAdapter() {
        this(0, 9);
    }

    public NumericWheelAdapter(int minValue, int maxValue) {
        this(minValue, maxValue, (String)null);
    }

    public NumericWheelAdapter(int minValue, int maxValue, String format) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.format = format;
    }

    public String getItem(int index) {
        if (index >= 0 && index < this.getItemsCount()) {
            int value = this.minValue + index;
            return this.format != null ? String.format(this.format, value) : Integer.toString(value);
        } else {
            return null;
        }
    }

    public int getItemsCount() {
        return this.maxValue - this.minValue + 1;
    }

    public int getMaximumLength() {
        int max = Math.max(Math.abs(this.maxValue), Math.abs(this.minValue));
        int maxLen = this.format != null ? Integer.toString(max).length() + this.format.length() : Integer.toString(max).length();
        if (this.minValue < 0) {
            ++maxLen;
        }

        return maxLen;
    }

    public int getmaxValue() {
        return this.maxValue;
    }

    public int getMinValue() {
        return this.minValue;
    }
}
