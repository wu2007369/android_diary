package com.example.wuzhiming.myapplication.wideget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.utils.DpPxExchange;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import static androidx.appcompat.widget.LinearLayoutCompat.SHOW_DIVIDER_MIDDLE;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;

/**
 * <pre>
 *      Project:    PalmGoldenEagle
 *      Github :    https://github.com/MingYueChunQiu
 *      Author:     XiYuJie
 *      Email:      xiyujie@jinying.com
 *      Time:       2020/2/28 13:32
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class ImageTabLayout extends HorizontalScrollView {

    private static final int MSG_UPDATE_INDICATOR = 1;//更新指示器

    private int textGravity;//textView位置
    private int mTextColorDef;//文本默认颜色
    private int mTextColorSelect;//文本选中颜色
    private int mTextBgDefResId;//默认背景
    private int mTextBgSelectResId;
    private boolean textBold;//标记文字是否加粗
    private int mIndicatorHeight, mIndicatorWidth;//指示器宽高
    private Drawable mIndicator;// 指示器图片（或颜色）资源

    private int mScrollViewWidth = 0, mScrollViewMiddle = 0, selectedTabPosition = -1, tabCount;
    private int mViewHeight, mViewWidth, mInnerLeftMargin, mInnerRightMargin;// item高度，item宽度，item距左，item距右
    private int mTabTopPadding, mTabBottomPadding, mTabLeftPadding, mTabRightPadding;
    private int mTabIndicatorPadding;
    private float mTextSize = 18;//默认字体大小(px)。
    private boolean averageTab; // 是否屏幕等分
    private int mWidth;//总宽度
    private float mTextSizeSelected;//选中后字体大小(px)。

    private Handler mHandler = null;
    private ViewPager mViewPager;
    private ViewPager2 mViewPager2;
    private List<TabItem> mTabList;
    private LinearLayoutCompat llContent;
    private ImageView ivIndicator;
    private TabLayoutOnPageChangeListener mPageChangeListener;
    private OnTabItemSelectListener onTabItemSelectListener;
    private List<TextView> mViewsList;
    private ValueAnimator mClipAnimator;
    private boolean smoothScroll;//标记是否平滑滚动

    public ImageTabLayout(@NonNull Context context) {
        this(context, null);
    }

    public ImageTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTabLayout);
        mTextColorDef = a.getColor(R.styleable.ImageTabLayout_itl_text_color, Color.BLACK);
        mTextColorSelect = a.getColor(R.styleable.ImageTabLayout_itl_text_color_selected, Color.BLUE);
        mTextBgDefResId = a.getResourceId(R.styleable.ImageTabLayout_itl_text_background, 0);
        mTextBgSelectResId = a.getResourceId(R.styleable.ImageTabLayout_itl_text_background_selected, 0);
        textBold = a.getBoolean(R.styleable.ImageTabLayout_itl_text_bold, false);

        mIndicator = a.getDrawable(R.styleable.ImageTabLayout_itl_indicator);
        mIndicatorWidth = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_indicator_width, 40);
        mIndicatorHeight = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_indicator_height, 5);
        mViewWidth = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_view_width, 0);
        mViewHeight = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_view_height, 0);
        mInnerLeftMargin = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_inner_margin_left, 0);
        mInnerRightMargin = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_inner_margin_right, 0);
        mTabTopPadding = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_tab_padding_top, 0);
        mTabBottomPadding = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_tab_padding_bottom, 0);
        mTabLeftPadding = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_tab_padding_left, 0);
        mTabRightPadding = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_tab_padding_right, 0);
        mTabIndicatorPadding = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_tab_indicator_padding, 0);

        mTextSize = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_text_size, 18);
        mTextSizeSelected = a.getDimensionPixelSize(R.styleable.ImageTabLayout_itl_text_size_selected, 0);
        smoothScroll = a.getBoolean(R.styleable.ImageTabLayout_itl_smooth_scroll, true);
        a.recycle();

        textGravity = Gravity.CENTER_HORIZONTAL;//默认水平居中

        mHandler = new StaticHandler(context, this);
        mViewsList = new ArrayList<>();

        setHorizontalScrollBarEnabled(false);
        setHorizontalFadingEdgeEnabled(false);

        FrameLayout layParent = new FrameLayout(context);
        addView(layParent);
        llContent = new LinearLayoutCompat(context);
        layParent.addView(llContent);
        ivIndicator = new AppCompatImageView(context);
        layParent.addView(ivIndicator);
    }

    public int getViewWidth() {
        return mViewWidth;
    }

    public int getViewHeight() {
        return mViewHeight;
    }

    public void setTextGravity(int textGravity) {
        this.textGravity = textGravity;
    }

    public void setViewWidth(int viewWidth) {
        this.mViewWidth = viewWidth;
    }

    public void setInnerLeftMargin(int innerLeftMargin) {
        this.mInnerLeftMargin = innerLeftMargin;
    }

    public void setInnerRightMargin(int innerRightMargin) {
        this.mInnerRightMargin = innerRightMargin;
    }

    public void setTextBgDefResId(int textBgDefResId) {
        mTextBgDefResId = textBgDefResId;
    }

    public void setTextBgSelectResId(int textBgSelectResId) {
        mTextBgSelectResId = textBgSelectResId;
    }

    public void setTextColorSelect(int textColorSelect) {
        mTextColorSelect = textColorSelect;
    }

    public void setTextColorSelectId(int colorId) {
        this.mTextColorSelect = getResources().getColor(colorId);
    }

    public void setTextColorDef(int textColorDef) {
        mTextColorDef = textColorDef;
    }

    public void setTextColorUnSelectId(int colorId) {
        mTextColorDef = getResources().getColor(colorId);
    }

    public void setViewHeight(int viewHeightPx) {
        this.mViewHeight = viewHeightPx;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public void setTextSizeSel(float textSizeSel) {
        this.mTextSizeSelected = textSizeSel;
    }

    public void setAverageTab(boolean averageTab, int mWidth) {
        this.averageTab = averageTab;
        this.mWidth = mWidth;
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.mIndicatorHeight = indicatorHeight;
    }

    public void setIndicatorWidth(int indicatorWidth) {
        this.mIndicatorWidth = indicatorWidth;
    }

    public void setIndicator(Drawable indicator) {
        mIndicator = indicator;
    }

    public View getIndicatorView() {
        return ivIndicator;
    }

    public boolean isSmoothScroll() {
        return smoothScroll;
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.smoothScroll = smoothScroll;
    }

    //只是用标题, 不使用ViewPager
    public void setTabData(@Nullable List<TabItem> tabList, int defaultPos) {
        setTabData(tabList, null, defaultPos);
    }

    public void setTabData(@Nullable List<TabItem> tabList, @Nullable ViewPager viewPager, int defaultPos) {
        if (tabList == null || tabList.size() == 0) return;
        mTabList = tabList;
        if (defaultPos >= 0 && defaultPos < tabList.size()) {
            selectedTabPosition = defaultPos;
        } else {
            selectedTabPosition = 0;
        }
        initView();
        clickTabSelItem(selectedTabPosition);
        if (viewPager != null) {
            mViewPager = viewPager;
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    clickTabSelItem(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    public void setTabData(@Nullable ViewPager2 viewPager, @Nullable List<TabItem> tabList, int defaultPos) {
        if (tabList == null || tabList.size() == 0) return;
        mTabList = tabList;
        if (defaultPos >= 0 && defaultPos < tabList.size()) {
            selectedTabPosition = defaultPos;
        } else {
            selectedTabPosition = 0;
        }
        initView();
        clickTabSelItem(selectedTabPosition);
        if (viewPager != null) {
            mViewPager2 = viewPager;
            mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    clickTabSelItem(position);
                }
            });
        }
    }

    /**
     * 设置Tab之间分割线
     *
     * @param divider 分割线图片
     */
    public void setTabDivider(@Nullable Drawable divider, int dividerPadding) {
        if (divider == null || llContent == null) {
            return;
        }
        llContent.setShowDividers(SHOW_DIVIDER_MIDDLE);
        llContent.setDividerPadding((int) DpPxExchange.Dp2Px(getContext(), dividerPadding));
        llContent.setDividerDrawable(divider);
    }

    /**
     * 设置指示器距离上部文字边距
     *
     * @param topMargin 上部边距
     */
    public void setIndicatorPadding(int topMargin) {
        if (llContent == null || ivIndicator == null) {
            return;
        }
        View view = llContent.getChildAt(0);
        LayoutParams params = (LayoutParams) ivIndicator.getLayoutParams();
        params.bottomMargin = view.getPaddingBottom() - topMargin;
    }

    /**
     * 标记Tab控件是否已经初始化过
     *
     * @return 返回true表示已经初始化过，否则返回false
     */
    public boolean isInitialized() {
        return mTabList != null && mTabList.size() > 0;
    }

    @NonNull
    public List<TabItem> getTabData() {
        return mTabList == null ? new ArrayList<>() : mTabList;
    }


    private void setData(List<TabItem> tabList) {
        if (tabList == null || tabList.size() == 0) return;
        mTabList = tabList;
        initView();
    }

    private void initView() {
        if (mTabList == null || mTabList.size() == 0) return;
        mViewsList = new ArrayList<>();
        llContent.removeAllViews();

        for (int i = 0; i < mTabList.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.getPaint().setFakeBoldText(textBold);
            textView.setPadding(mTabLeftPadding, mTabTopPadding, mTabRightPadding, mTabBottomPadding);
            if (averageTab) {
                LinearLayoutCompat linearLayout = new LinearLayoutCompat(getContext());
                linearLayout.setGravity(Gravity.CENTER);
                LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                if (mWidth > 0)
                    layoutParams.width = mWidth / mTabList.size();
                linearLayout.addView(textView);
                llContent.addView(linearLayout, layoutParams);
            } else {
                llContent.addView(textView);
            }

            textView.setGravity(textGravity);
            if (i == selectedTabPosition) {
                textView.setBackgroundResource(mTextBgSelectResId);
                textView.setTextColor(mTextColorSelect);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeSelected <= 0 ? mTextSize : mTextSizeSelected);//如果选中字体未设置，选用普通字体
            } else {
                textView.setBackgroundResource(mTextBgDefResId);
                textView.setTextColor(mTextColorDef);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);//数据类型px
            }
            textView.setTag(i);
            initTabViewWithItem(textView, mTabList.get(i));
            textView.setOnClickListener(view -> {
                int position = Integer.parseInt(view.getTag().toString());
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(position, smoothScroll);
                } else if (mViewPager2 != null) {
                    mViewPager2.setCurrentItem(position, smoothScroll);
                } else {
                    clickTabSelItem(position);
                }
            });

            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(
                    mViewWidth > 0 ? mViewWidth : LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (!averageTab) {
                layoutParams.rightMargin = mInnerRightMargin;
                layoutParams.leftMargin = mInnerLeftMargin;
            }
            layoutParams.height = mViewHeight <= 0 ? LayoutParams.WRAP_CONTENT : mViewHeight;
            textView.setLayoutParams(layoutParams);
            mViewsList.add(textView);
        }
        initIndicatorView();
        mHandler.sendEmptyMessageDelayed(0, 200);
    }

    /**
     * 根据Item数据初始化Tab控件
     *
     * @param textView Tab控件
     * @param tabItem  Tab数据对象
     */
    private void initTabViewWithItem(@NonNull TextView textView, @Nullable TabItem tabItem) {
        if (tabItem == null) {
            return;
        }
        if (TextUtils.isEmpty(tabItem.getContent())) {
            textView.setText(tabItem.getName());
        } else {
            textView.setText(tabItem.getContent().toString());
        }
        if (tabItem.getIconResId() == 0) {
            return;
        }
        Context context = getContext();
        if (context == null) {
            return;
        }
        Drawable drawable = ContextCompat.getDrawable(context, tabItem.getIconResId());
        if (drawable != null) {
            int width = Math.max(tabItem.getIconWidth(), 0);
            int height = Math.max(tabItem.getIconHeight(), 0);
            drawable.setBounds(0, 0, width, height);
            textView.setCompoundDrawables(null, null, drawable, null);
        }
    }

    private void initIndicatorView() {
        ivIndicator.setImageDrawable(mIndicator);
        LayoutParams fl = (LayoutParams) ivIndicator.getLayoutParams();
        fl.width = mIndicatorWidth;
        fl.height = mIndicatorHeight;
        fl.gravity = Gravity.BOTTOM;
        fl.setMargins(fl.leftMargin, mTabIndicatorPadding, 0, 0);
        ivIndicator.setLayoutParams(fl);
    }

    public void setIndicatorLeftMargin(int leftMargin) {
        ivIndicator.setImageDrawable(mIndicator);
        LayoutParams fl = (LayoutParams) ivIndicator.getLayoutParams();
        fl.setMargins(leftMargin, fl.topMargin, fl.rightMargin, fl.bottomMargin);
        ivIndicator.setLayoutParams(fl);
    }

    private void clickTabSelItem(int position) {
        if (mViewsList == null) return;
        selectedTabPosition = position;
        if (null != onTabItemSelectListener) {
            onTabItemSelectListener.onSelected(this, position);
        }

        for (int i = 0; i < mViewsList.size(); i++) {
            TextView textView = mViewsList.get(i);
            TabItem item = mTabList.get(i);
            if (Integer.parseInt(mViewsList.get(i).getTag().toString()) == position) {
                textView.setBackgroundResource(mTextBgSelectResId);
                if (TextUtils.isEmpty(item.getContent())) {
                    textView.setTextColor(mTextColorSelect);
                } else {
                    textView.setText(item.getContent());
                }
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeSelected <= 0 ? mTextSize : mTextSizeSelected);//如果选中字体未设置，选用普通字体
                changeItemLocation(i);
            } else {
                if (TextUtils.isEmpty(item.getContent())) {
                    textView.setTextColor(mTextColorSelect);
                } else {
                    textView.setText(item.getContent().toString());
                }
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
                mViewsList.get(i).setBackgroundResource(mTextBgDefResId);
                mViewsList.get(i).setTextColor(mTextColorDef);
            }
        }

    }

    public void setCurrentItem(int position) {
        if (selectedTabPosition == position) {
            if (onTabItemSelectListener != null) {
                onTabItemSelectListener.onReSelected(this, position);
            }
            return;
        }
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position);
        } else {
            clickTabSelItem(position);
        }
    }

    public TextView getTextView(int position) {
        if (mViewsList == null || position >= mViewsList.size())
            throw new RuntimeException("mViewsList == null || position >= mViewsList.size()");
        return mViewsList.get(position);
    }

    public LinearLayoutCompat getLayContent() {
        return llContent;
    }

    /**
     * 改变tabItem滚动位置
     *
     * @param position 改变的Item位置
     */
    public void changeItemLocation(int position) {
        if (position >= 0 && position < mViewsList.size()) {
            mHandler.removeMessages(MSG_UPDATE_INDICATOR);
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_INDICATOR, 300);
//            changeIndicatorLocation();
            int x = (mViewsList.get(position).getLeft() - getScrollViewMiddle() +
                    (getViewHeight(mViewsList.get(position)) / 2));
            smoothScrollTo(x, 0);
        }
    }

    /**
     * 改变底部指示器位置
     */
    private void changeIndicatorLocation() {
        if (selectedTabPosition >= 0 && selectedTabPosition < mViewsList.size()) {
            TextView textView = getTextView(selectedTabPosition);
            textView.post(() -> {
                //防止在RecyclerView嵌套情況下，控件从屏幕进入，TextView还没来得及绘制，导致TextView的四个角获取为0,
                //使用post方式，确保TextView已经绘制
                int x;
                if (averageTab) {
                    int[] position = new int[2];
                    textView.getLocationOnScreen(position);
                    int l1 = position[0];
                    if (l1 == 0) {
                        int sWidth = mWidth / mViewsList.size();//tab的宽度
                        int bWidth = sWidth / 2;//tab/2
                        textView.measure(0, 0);
                        l1 = sWidth * selectedTabPosition + bWidth - textView.getMeasuredWidth() / 2;
                    }
                    x = l1 + (textView.getRight() - textView.getLeft() - mIndicatorWidth) / 2;
                } else {
                    x = textView.getLeft() + (textView.getRight() - textView.getLeft() - mIndicatorWidth) / 2;
                }

                LayoutParams fl = (LayoutParams) ivIndicator.getLayoutParams();
                fl.leftMargin = x;
                ivIndicator.setLayoutParams(fl);
                if (mClipAnimator == null) {
                    mClipAnimator = ValueAnimator.ofInt(0, 10000).setDuration(400);
                    mClipAnimator.addUpdateListener(animation -> {
                        if (ivIndicator == null) {
                            return;
                        }
                        Drawable drawable = ivIndicator.getDrawable();
                        if (drawable instanceof ClipDrawable) {
                            //裁剪范围是从0到10000
                            drawable.setLevel((Integer) animation.getAnimatedValue());
                        }
                    });
                } else {
                    mClipAnimator.cancel();
                }
                mClipAnimator.start();
            });
        }
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return 返回scrollview的中间位置
     */
    private int getScrollViewMiddle() {
        if (mScrollViewMiddle == 0)
            mScrollViewMiddle = getScrollViewWidth() / 2;
        return mScrollViewMiddle;
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return 返回ScrollView的宽度
     */
    private int getScrollViewWidth() {
        if (mScrollViewWidth == 0)
            mScrollViewWidth = getRight() - getLeft();
        return mScrollViewWidth;
    }

    private int getViewHeight(View view) {
        return view.getBottom() - view.getTop();
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private final WeakReference<ImageTabLayout> mTabLayoutRef;
        private int mPreviousScrollState;
        private int mScrollState;

        public TabLayoutOnPageChangeListener(ImageTabLayout tabLayout) {
            mTabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }

        @Override
        public void onPageScrolled(final int position, final float positionOffset,
                                   final int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(final int position) {
            final ImageTabLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != position
                    && position < tabLayout.getTabCount()) {
                tabLayout.clickTabSelItem(position);
            }
        }

        void reset() {
            mPreviousScrollState = mScrollState = SCROLL_STATE_IDLE;
        }
    }

    public int getSelectedTabPosition() {
        return selectedTabPosition;
    }

    public int getTabCount() {
        return tabCount;
    }

    public void setOnTabItemSelectListener(OnTabItemSelectListener listener) {
        onTabItemSelectListener = listener;
    }

    /* 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private static class StaticHandler extends Handler {
        private final WeakReference<Context> mWeakContext;
        private final WeakReference<ImageTabLayout> mParent;

        StaticHandler(Context context, ImageTabLayout view) {
            mWeakContext = new WeakReference<>(context);
            mParent = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Context context = mWeakContext.get();
            ImageTabLayout parent = mParent.get();
            if (null != context && null != parent) {
                switch (msg.what) {
                    case 0:
                        parent.changeItemLocation(parent.selectedTabPosition);
                        break;
                    case MSG_UPDATE_INDICATOR:
                        parent.changeIndicatorLocation();
                    default:
                        break;
                }

                super.handleMessage(msg);
            }
        }
    }

    /**
     * Tab对象
     */
    public static class TabItem {

        private Builder mBuilder;

        public TabItem() {
            this(new Builder());
        }

        public TabItem(@NonNull Builder builder) {
            mBuilder = builder;
        }

        public String getName() {
            return mBuilder.name;
        }

        public void setName(String name) {
            mBuilder.name = name;
        }

        public CharSequence getContent() {
            return mBuilder.content;
        }

        public void setContent(CharSequence content) {
            mBuilder.content = content;
        }

        public int getIconResId() {
            return mBuilder.iconResId;
        }

        public void setIconResId(@DrawableRes int iconResId) {
            mBuilder.iconResId = iconResId;
        }

        public int getIconWidth() {
            return mBuilder.iconWidth;
        }

        public void setIconWidth(int iconWidth) {
            mBuilder.iconWidth = iconWidth;
        }

        public int getIconHeight() {
            return mBuilder.iconHeight;
        }

        public void setIconHeight(int iconHeight) {
            mBuilder.iconHeight = iconHeight;
        }

        public Object getExtra() {
            return mBuilder.extra;
        }

        public void setExtra(Object extra) {
            mBuilder.extra = extra;
        }

        public static class Builder {

            private String name;//名称
            private CharSequence content;//内容
            private @DrawableRes
            int iconResId;//图标资源ID
            private int iconWidth;//图标宽度
            private int iconHeight;//图标高度
            private Object extra;//额外数据

            public TabItem build() {
                return new TabItem(this);
            }

            public String getName() {
                return name;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public CharSequence getContent() {
                return content;
            }

            public Builder setContent(CharSequence content) {
                this.content = content;
                return this;
            }

            public int getIconResId() {
                return iconResId;
            }

            public Builder setIconResId(@DrawableRes int iconResId) {
                this.iconResId = iconResId;
                return this;
            }

            public int getIconWidth() {
                return iconWidth;
            }

            public Builder setIconWidth(int iconWidth) {
                this.iconWidth = iconWidth;
                return this;
            }

            public int getIconHeight() {
                return iconHeight;
            }

            public Builder setIconHeight(int iconHeight) {
                this.iconHeight = iconHeight;
                return this;
            }

            public Object getExtra() {
                return extra;
            }

            public Builder setExtra(Object extra) {
                this.extra = extra;
                return this;
            }
        }
    }

    /**
     * Tab选择监听器
     */
    public interface OnTabItemSelectListener {

        void onSelected(@NonNull ImageTabLayout tabLayout, int position);

        void onReSelected(@NonNull ImageTabLayout tabLayout, int position);
    }
}
