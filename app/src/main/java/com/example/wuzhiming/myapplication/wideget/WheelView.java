//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.wuzhiming.myapplication.wideget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.example.wuzhiming.myapplication.interfa.OnWheelChangedListener;
import com.example.wuzhiming.myapplication.interfa.OnWheelScrollListener;
import com.example.wuzhiming.myapplication.interfa.WheelAdapter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WheelView extends View {
    private static final int SCROLLING_DURATION = 400;
    private static final int MIN_DELTA_FOR_SCROLLING = 1;
    private static final int VALUE_TEXT_COLOR = -12541697;
    private static final int ITEMS_TEXT_COLOR = -7237231;
    private static final int[] SHADOWS_COLORS = new int[]{-15658735, 11184810, 11184810};
    private static final int ADDITIONAL_ITEM_HEIGHT = 60;
    public int TEXT_SIZE;
    private final int ITEM_OFFSET;
    private static final int ADDITIONAL_ITEMS_SPACE = 12;
    private static final int LABEL_OFFSET = 22;
    private static final int PADDING = 0;
    private static final int DEF_VISIABLE_ITEMS = 3;
    private WheelAdapter adapter;
    private int currentItem;
    private int itemsWidth;
    private int labelWidth;
    private int visibleItems;
    private int itemHeight;
    private TextPaint itemsPaint;
    private TextPaint valuePaint;
    private int selectColor;
    private StaticLayout itemsLayout;
    private StaticLayout labelLayout;
    private StaticLayout valueLayout;
    private String label;
    private Drawable centerDrawableTop;
    private Drawable centerDrawableBottom;
    private GradientDrawable topShadow;
    private GradientDrawable bottomShadow;
    private boolean isScrollingPerformed;
    private int scrollingOffset;
    private GestureDetector gestureDetector;
    private Scroller scroller;
    private int lastScrollY;
    private boolean isCyclic;
    private List<OnWheelChangedListener> changingListeners;
    private List<OnWheelScrollListener> scrollingListeners;
    private SimpleOnGestureListener gestureListener;
    private final int MESSAGE_SCROLL;
    private final int MESSAGE_JUSTIFY;
    private Handler animationHandler;

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }

    public WheelAdapter getAdapter() {
        return this.adapter;
    }

    public void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        this.invalidateLayouts();
        this.invalidate();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.scroller.forceFinished(true);
        this.scroller = new Scroller(this.getContext(), interpolator);
    }

    public int getVisibleItems() {
        return this.visibleItems;
    }

    public void setVisibleItems(int count) {
        this.visibleItems = count;
        this.invalidate();
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String newLabel) {
        if (this.label == null || !this.label.equals(newLabel)) {
            this.label = newLabel;
            this.labelLayout = null;
            this.invalidate();
        }

    }

    public void addChangingListener(OnWheelChangedListener listener) {
        this.changingListeners.add(listener);
    }

    public void removeChangingListener(OnWheelChangedListener listener) {
        this.changingListeners.remove(listener);
    }

    public void addScrollingListener(OnWheelScrollListener listener) {
        this.scrollingListeners.add(listener);
    }

    public void removeScrollingListener(OnWheelScrollListener listener) {
        this.scrollingListeners.remove(listener);
    }

    public void clearListener() {
        this.changingListeners.clear();
        this.scrollingListeners.clear();
    }

    public int getCurrentItem() {
        return this.currentItem;
    }

    public void setCurrentItem(int index) {
        this.setCurrentItem(index, false);
    }

    public void setCurrentItem(int index, boolean animated) {
        if (this.adapter != null && this.adapter.getItemsCount() != 0) {
            if (index < 0 || index >= this.adapter.getItemsCount()) {
                if (!this.isCyclic) {
                    return;
                }

                while(index < 0) {
                    index += this.adapter.getItemsCount();
                }

                index %= this.adapter.getItemsCount();
            }

            if (index != this.currentItem) {
                if (animated) {
                    this.scroll(index - this.currentItem, 400);
                } else {
                    this.invalidateLayouts();
                    int old = this.currentItem;
                    this.currentItem = index;
                    this.notifyChangingListeners(old, this.currentItem);
                    this.invalidate();
                }
            }

        }
    }

    public boolean isCyclic() {
        return this.isCyclic;
    }

    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
        this.invalidate();
        this.invalidateLayouts();
    }

    public WheelView(Context context) {
        this(context, (AttributeSet)null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.ITEM_OFFSET = this.TEXT_SIZE / 3;
        this.adapter = null;
        this.currentItem = 0;
        this.itemsWidth = 0;
        this.labelWidth = 0;
        this.visibleItems = 3;
        this.itemHeight = 0;
        this.selectColor = -12541697;
        this.isCyclic = false;
        this.changingListeners = new LinkedList();
        this.scrollingListeners = new LinkedList();
        this.gestureListener = new SimpleOnGestureListener() {
            public boolean onDown(MotionEvent e) {
                if (WheelView.this.isScrollingPerformed) {
                    WheelView.this.scroller.forceFinished(true);
                    WheelView.this.clearMessages();
                    return true;
                } else {
                    return false;
                }
            }

            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                WheelView.this.startScrolling();
                WheelView.this.doScroll((int)(-distanceY));
                return true;
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                WheelView.this.lastScrollY = WheelView.this.currentItem * WheelView.this.getItemHeight() + WheelView.this.scrollingOffset;
                int maxY = WheelView.this.isCyclic ? 2147483647 : WheelView.this.adapter.getItemsCount() * WheelView.this.getItemHeight();
                int minY = WheelView.this.isCyclic ? -maxY : 0;
                WheelView.this.scroller.fling(0, WheelView.this.lastScrollY, 0, (int)(-velocityY) / 2, 0, 0, minY, maxY);
                WheelView.this.setNextMessage(0);
                return true;
            }
        };
        this.MESSAGE_SCROLL = 0;
        this.MESSAGE_JUSTIFY = 1;
        this.animationHandler = new Handler() {
            public void handleMessage(Message msg) {
                WheelView.this.scroller.computeScrollOffset();
                int currY = WheelView.this.scroller.getCurrY();
                int delta = WheelView.this.lastScrollY - currY;
                WheelView.this.lastScrollY = currY;
                if (delta != 0) {
                    WheelView.this.doScroll(delta);
                }

                if (Math.abs(currY - WheelView.this.scroller.getFinalY()) < 1) {
                    currY = WheelView.this.scroller.getFinalY();
                    WheelView.this.scroller.forceFinished(true);
                }

                if (!WheelView.this.scroller.isFinished()) {
                    WheelView.this.animationHandler.sendEmptyMessage(msg.what);
                } else if (msg.what == 0) {
                    WheelView.this.justify();
                } else {
                    WheelView.this.finishScrolling();
                }

            }
        };
        this.initData(context);
    }

    private void initData(Context context) {
        this.gestureDetector = new GestureDetector(context, this.gestureListener);
        this.gestureDetector.setIsLongpressEnabled(false);
        this.scroller = new Scroller(context);
    }

    protected void notifyChangingListeners(int oldValue, int newValue) {
        Iterator var3 = this.changingListeners.iterator();

        while(var3.hasNext()) {
            OnWheelChangedListener listener = (OnWheelChangedListener)var3.next();
            listener.onChanged(this, oldValue, newValue);
        }

    }

    protected void notifyScrollingListenersAboutStart() {
        Iterator var1 = this.scrollingListeners.iterator();

        while(var1.hasNext()) {
            OnWheelScrollListener listener = (OnWheelScrollListener)var1.next();
            listener.onScrollingStarted(this);
        }

    }

    protected void notifyScrollingListenersAboutEnd() {
        Iterator var1 = this.scrollingListeners.iterator();

        while(var1.hasNext()) {
            OnWheelScrollListener listener = (OnWheelScrollListener)var1.next();
            listener.onScrollingFinished(this);
        }

    }

    private void invalidateLayouts() {
        this.itemsLayout = null;
        this.valueLayout = null;
        this.scrollingOffset = 0;
    }

    private void initResourceIfNecessary() {
        if (this.itemsPaint == null) {
            this.itemsPaint = new TextPaint(1);
            this.itemsPaint.setTextSize((float)this.TEXT_SIZE);
        }

        if (this.valuePaint == null) {
            this.valuePaint = new TextPaint(5);
            this.valuePaint.setTextSize((float)this.TEXT_SIZE);
            this.valuePaint.setShadowLayer(0.1F, 0.0F, 0.1F, -4144960);
        }

        if (this.centerDrawableTop == null) {
        }

        if (this.centerDrawableBottom == null) {
        }

        if (this.topShadow == null) {
            this.topShadow = new GradientDrawable(Orientation.TOP_BOTTOM, SHADOWS_COLORS);
        }

        if (this.bottomShadow == null) {
            this.bottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP, SHADOWS_COLORS);
        }

    }

    private int getDesiredHeight(Layout layout) {
        if (layout == null) {
            return 0;
        } else {
            int desired = this.getItemHeight() * this.visibleItems - this.ITEM_OFFSET * 2 - 60;
            desired = Math.max(desired, this.getSuggestedMinimumHeight());
            return desired;
        }
    }

    private String getTextItem(int index) {
        if (this.adapter != null && this.adapter.getItemsCount() != 0) {
            int count = this.adapter.getItemsCount();
            if ((index < 0 || index >= count) && !this.isCyclic) {
                return null;
            } else {
                while(index < 0) {
                    index += count;
                }

                index %= count;
                return this.adapter.getItem(index);
            }
        } else {
            return null;
        }
    }

    private String buildText(boolean useCurrentValue) {
        StringBuilder itemsText = new StringBuilder();
        int addItems = this.visibleItems / 2 + 1;

        for(int i = this.currentItem - addItems; i <= this.currentItem + addItems; ++i) {
            if (useCurrentValue || i != this.currentItem) {
                String text = this.getTextItem(i);
                if (text != null) {
                    itemsText.append(text);
                }
            }

            if (i < this.currentItem + addItems) {
                itemsText.append("\n");
            }
        }

        return itemsText.toString();
    }

    private int getMaxTextLength() {
        WheelAdapter adapter = this.getAdapter();
        if (adapter == null) {
            return 0;
        } else {
            int adapterLength = adapter.getMaximumLength();
            if (adapterLength > 0) {
                return adapterLength;
            } else {
                String maxText = null;
                int addItems = this.visibleItems / 2;

                for(int i = Math.max(this.currentItem - addItems, 0); i < Math.min(this.currentItem + this.visibleItems, adapter.getItemsCount()); ++i) {
                    String text = adapter.getItem(i);
                    if (text != null && (maxText == null || maxText.length() < text.length())) {
                        maxText = text;
                    }
                }

                return maxText != null ? maxText.length() : 0;
            }
        }
    }

    private int getItemHeight() {
        if (this.itemHeight != 0) {
            return this.itemHeight;
        } else if (this.itemsLayout != null && this.itemsLayout.getLineCount() > 2) {
            this.itemHeight = this.itemsLayout.getLineTop(2) - this.itemsLayout.getLineTop(1);
            return this.itemHeight;
        } else {
            return this.getHeight() / this.visibleItems;
        }
    }

    private int calculateLayoutWidth(int widthSize, int mode) {
        this.initResourceIfNecessary();
        int maxLength = this.getMaxTextLength();
        if (maxLength > 0) {
            float textWidth = (float)Math.ceil((double)Layout.getDesiredWidth("0", this.itemsPaint));
            this.itemsWidth = (int)((float)maxLength * textWidth);
        } else {
            this.itemsWidth = 0;
        }

        this.itemsWidth += 12;
        this.labelWidth = 0;
        if (this.label != null && this.label.length() > 0) {
            this.labelWidth = (int)Math.ceil((double)Layout.getDesiredWidth(this.label, this.valuePaint));
        }

        boolean recalculate = false;
        int width;
        if (mode == 1073741824) {
            width = widthSize;
            recalculate = true;
        } else {
            width = this.itemsWidth + this.labelWidth + 0;
            if (this.labelWidth > 0) {
                width += 22;
            }

            width = Math.max(width, this.getSuggestedMinimumWidth());
            if (mode == -2147483648 && widthSize < width) {
                width = widthSize;
                recalculate = true;
            }
        }

        if (recalculate) {
            int pureWidth = width - 22 - 0;
            if (pureWidth <= 0) {
                this.itemsWidth = this.labelWidth = 0;
            }

            if (this.labelWidth > 0) {
                double newWidthItems = (double)this.itemsWidth * (double)pureWidth / (double)(this.itemsWidth + this.labelWidth);
                this.itemsWidth = (int)newWidthItems;
                this.labelWidth = pureWidth - this.itemsWidth;
            } else {
                this.itemsWidth = pureWidth + 22;
            }
        }

        if (this.itemsWidth > 0) {
            this.createLayouts(this.itemsWidth, this.labelWidth);
        }

        return width;
    }

    private void createLayouts(int widthItems, int widthLabel) {
        if (this.itemsLayout != null && this.itemsLayout.getWidth() <= widthItems) {
            this.itemsLayout.increaseWidthTo(widthItems);
        } else {
            this.itemsLayout = new StaticLayout(this.buildText(this.isScrollingPerformed), this.itemsPaint, widthItems, widthLabel > 0 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_CENTER, 1.0F, 60.0F, false);
        }

        if (!this.isScrollingPerformed && (this.valueLayout == null || this.valueLayout.getWidth() > widthItems)) {
            String text = this.getAdapter() != null ? this.getAdapter().getItem(this.currentItem) : null;
            this.valueLayout = new StaticLayout(text != null ? text : "", this.valuePaint, widthItems, widthLabel > 0 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_CENTER, 1.0F, 60.0F, false);
        } else if (this.isScrollingPerformed) {
            this.valueLayout = null;
        } else {
            this.valueLayout.increaseWidthTo(widthItems);
        }

        if (widthLabel > 0) {
            if (this.labelLayout != null && this.labelLayout.getWidth() <= widthLabel) {
                this.labelLayout.increaseWidthTo(widthLabel);
            } else {
                this.labelLayout = new StaticLayout(this.label, this.valuePaint, widthLabel, Alignment.ALIGN_NORMAL, 1.0F, 60.0F, false);
            }
        }

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = this.calculateLayoutWidth(widthSize, widthMode);
        int height;
        if (heightMode == 1073741824) {
            height = heightSize;
        } else {
            height = this.getDesiredHeight(this.itemsLayout);
            if (heightMode == -2147483648) {
                height = Math.min(height, heightSize);
            }
        }

        this.setMeasuredDimension(width, height);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.itemsLayout == null) {
            if (this.itemsWidth == 0) {
                this.calculateLayoutWidth(this.getWidth(), 1073741824);
            } else {
                this.createLayouts(this.itemsWidth, this.labelWidth);
            }
        }

        if (this.itemsWidth > 0) {
            canvas.save();
            canvas.translate(0.0F, (float)(-this.ITEM_OFFSET));
            this.drawItems(canvas);
            this.drawValue(canvas);
            canvas.restore();
        }

    }

    private void drawShadows(Canvas canvas) {
        this.topShadow.setBounds(0, 0, this.getWidth(), this.getHeight() / this.visibleItems);
        this.topShadow.draw(canvas);
        this.bottomShadow.setBounds(0, this.getHeight() - this.getHeight() / this.visibleItems, this.getWidth(), this.getHeight());
        this.bottomShadow.draw(canvas);
    }

    private void drawValue(Canvas canvas) {
        this.valuePaint.setColor(this.selectColor);
        this.valuePaint.drawableState = this.getDrawableState();
        Rect bounds = new Rect();
        this.itemsLayout.getLineBounds(this.visibleItems / 2, bounds);
        if (this.labelLayout != null) {
            canvas.save();
            canvas.translate((float)(this.itemsLayout.getWidth() + 22), (float)bounds.top);
            this.labelLayout.draw(canvas);
            canvas.restore();
        }

        if (this.valueLayout != null) {
            canvas.save();
            canvas.translate(0.0F, (float)(bounds.top + this.scrollingOffset));
            this.valueLayout.draw(canvas);
            canvas.restore();
        }

    }

    private void drawItems(Canvas canvas) {
        canvas.save();
        int top = this.itemsLayout.getLineTop(1);
        canvas.translate(0.0F, (float)(-top + this.scrollingOffset));
        this.itemsPaint.setColor(-7237231);
        this.itemsPaint.drawableState = this.getDrawableState();
        this.itemsLayout.draw(canvas);
        canvas.restore();
    }

    private void drawCenterRect(Canvas canvas) {
        int center = this.getHeight() / 2;
        int offset = this.getItemHeight() / 2;
        this.centerDrawableTop.setBounds(0, center - offset, this.getWidth(), center + offset);
        this.centerDrawableTop.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        WheelAdapter adapter = this.getAdapter();
        if (adapter == null) {
            return true;
        } else {
            if (!this.gestureDetector.onTouchEvent(event) && event.getAction() == 1) {
                this.justify();
            }

            return true;
        }
    }

    private void doScroll(int delta) {
        this.scrollingOffset += delta;
        int count = this.scrollingOffset / this.getItemHeight();
        int pos = this.currentItem - count;
        if (this.isCyclic && this.adapter.getItemsCount() > 0) {
            while(true) {
                if (pos >= 0) {
                    pos %= this.adapter.getItemsCount();
                    break;
                }

                pos += this.adapter.getItemsCount();
            }
        } else if (this.isScrollingPerformed) {
            if (pos < 0) {
                count = this.currentItem;
                pos = 0;
            } else if (pos >= this.adapter.getItemsCount()) {
                count = this.currentItem - this.adapter.getItemsCount() + 1;
                pos = this.adapter.getItemsCount() - 1;
            }
        } else {
            pos = Math.max(pos, 0);
            pos = Math.min(pos, this.adapter.getItemsCount() - 1);
        }

        int offset = this.scrollingOffset;
        if (pos != this.currentItem) {
            this.setCurrentItem(pos, false);
        } else {
            this.invalidate();
        }

        this.scrollingOffset = offset - count * this.getItemHeight();
        if (this.scrollingOffset > this.getHeight()) {
            this.scrollingOffset = this.scrollingOffset % this.getHeight() + this.getHeight();
        }

    }

    private void setNextMessage(int message) {
        this.clearMessages();
        this.animationHandler.sendEmptyMessage(message);
    }

    private void clearMessages() {
        this.animationHandler.removeMessages(0);
        this.animationHandler.removeMessages(1);
    }

    private void justify() {
        if (this.adapter != null) {
            this.lastScrollY = 0;
            int offset = this.scrollingOffset;
            int itemHeight = this.getItemHeight();
            boolean needToIncrease = offset > 0 ? this.currentItem < this.adapter.getItemsCount() : this.currentItem > 0;
            if ((this.isCyclic || needToIncrease) && Math.abs((float)offset) > (float)itemHeight / 2.0F) {
                if (offset < 0) {
                    offset += itemHeight + 1;
                } else {
                    offset -= itemHeight + 1;
                }
            }

            if (Math.abs(offset) > 1) {
                this.scroller.startScroll(0, 0, 0, offset, 400);
                this.setNextMessage(1);
            } else {
                this.finishScrolling();
            }

        }
    }

    private void startScrolling() {
        if (!this.isScrollingPerformed) {
            this.isScrollingPerformed = true;
            this.notifyScrollingListenersAboutStart();
        }

    }

    void finishScrolling() {
        if (this.isScrollingPerformed) {
            this.notifyScrollingListenersAboutEnd();
            this.isScrollingPerformed = false;
        }

        this.invalidateLayouts();
        this.invalidate();
    }

    public void scroll(int itemsToScroll, int time) {
        this.scroller.forceFinished(true);
        this.lastScrollY = this.scrollingOffset;
        int offset = itemsToScroll * this.getItemHeight();
        this.scroller.startScroll(0, this.lastScrollY, 0, offset - this.lastScrollY, time);
        this.setNextMessage(0);
        this.startScrolling();
    }
}
