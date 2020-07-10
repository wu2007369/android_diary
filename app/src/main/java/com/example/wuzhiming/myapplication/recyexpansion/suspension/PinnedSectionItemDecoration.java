package com.example.wuzhiming.myapplication.recyexpansion.suspension;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PinnedSectionItemDecoration<VH extends RecyclerView.ViewHolder>  extends RecyclerView.ItemDecoration{
    private static final String TAG="ItemDecoration";
    //外部activity传入的 recycler顶部的framlayour浮层
    private ViewGroup sectionContainer;
    private Callback<VH> mCallback;

    private VH mFixedHeaderViewHolder  = null;
    private int mFixedHeaderViewPosition = RecyclerView.NO_POSITION;
    private WeakReference<ViewGroup> mWeakSectionContainer;


    /**
     * 构造方法
     * @param sectionContainer
     * @param callback
     */
    public PinnedSectionItemDecoration(ViewGroup sectionContainer, Callback<VH> callback) {
        this.sectionContainer = sectionContainer;
        mCallback = callback;
        mWeakSectionContainer =  new WeakReference<>(sectionContainer);

        //通过回调，将匿名内部类的观察者，注册到外部adapter
        mCallback.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                //每次有数据源变动，则刷新顶部当前数据
                if (mFixedHeaderViewPosition >= positionStart && mFixedHeaderViewPosition < positionStart + itemCount) {
                    if (mFixedHeaderViewHolder != null) {
                        if (mWeakSectionContainer.get() != null) {
                                bindFixedViewHolder(mWeakSectionContainer.get(), mFixedHeaderViewHolder, mFixedHeaderViewPosition);
                        }
                    }
                }
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                if (mFixedHeaderViewPosition >= positionStart && mFixedHeaderViewPosition < positionStart + itemCount) {
                    mFixedHeaderViewPosition = RecyclerView.NO_POSITION;
                }
            }
        });
    }

    @Override
    public void onDrawOver( Canvas c,  RecyclerView parent,  RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        ViewGroup  sectionContainer = mWeakSectionContainer.get();
        if (null == sectionContainer){
            return;
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null || ! (layoutManager instanceof LinearLayoutManager)) {
            setHeaderVisibility(false);
            return;
        }

        // 获得第一个可见的item的下标...
        int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

        if (firstVisibleItemPosition == RecyclerView.NO_POSITION) {
            setHeaderVisibility(false);
            return;
        }


        //将当前adapterPosition对应到所属的 头部Header索引
        int headerPos = mCallback.getRelativeFixedItemPosition(firstVisibleItemPosition);

        if (headerPos == RecyclerView.NO_POSITION) {
            setHeaderVisibility(false);
            return;
        }
        if (mFixedHeaderViewHolder == null || mFixedHeaderViewHolder.getItemViewType() != mCallback.getItemViewType(headerPos)) {
            mFixedHeaderViewHolder = createFixedViewHolder(parent, headerPos);
        }

        if (mFixedHeaderViewPosition != headerPos) {
            mFixedHeaderViewPosition = headerPos;
            bindFixedViewHolder(sectionContainer, mFixedHeaderViewHolder, headerPos);
        }

        setHeaderVisibility(true);

        int contactPoint = sectionContainer.getHeight();
        View childInContact = parent.findChildViewUnder((parent.getWidth() / 2), contactPoint);
        if (childInContact == null) {
            Log.i(TAG,"in childInContact == null:parent.getTop()="+parent.getTop());
            Log.i(TAG,"in childInContact == null:sectionContainer.getTop()="+sectionContainer.getTop());
            sectionContainer.offsetTopAndBottom(parent.getTop() - sectionContainer.getTop());
            return;
        }

        if (mCallback.isHeader(parent.getChildAdapterPosition(childInContact))) {
            Log.i(TAG,"in mCallback.isHeader:c***************************");
            Log.i(TAG,"in mCallback.isHeader:childInContact.getTop()="+childInContact.getTop());
            Log.i(TAG,"in mCallback.isHeader:parent.getTop()="+parent.getTop());
            Log.i(TAG,"in mCallback.isHeader:sectionContainer.getHeight()="+sectionContainer.getHeight());
            Log.i(TAG,"in mCallback.isHeader:sectionContainer.getTop()="+sectionContainer.getTop());
            sectionContainer.offsetTopAndBottom(childInContact.getTop() + parent.getTop() - sectionContainer.getHeight() - sectionContainer.getTop());
            return;
        }
        Log.i(TAG,"in onDrawOver:parent.getTop()="+parent.getTop());
        Log.i(TAG,"in onDrawOver:sectionContainer.getTop()="+sectionContainer.getTop());
        sectionContainer.offsetTopAndBottom(parent.getTop() - sectionContainer.getTop());
    }

    private void setHeaderVisibility(boolean visibility) {
        ViewGroup sectionContainer = mWeakSectionContainer.get();
        if (null == sectionContainer){
            return;
        }
        sectionContainer.setVisibility(visibility ? View.VISIBLE:View.GONE);
        mCallback.onHeaderVisibilityChanged(visibility);
    }

    /**
     * 通过recyclerview和position，调用Adapter.createViewHolder(parent, viewType)
     * @param recyclerView
     * @param position
     * @return
     */
    private VH createFixedViewHolder(RecyclerView recyclerView, int position){
        int viewType = mCallback.getItemViewType(position);
        VH vh = mCallback.createViewHolder(recyclerView, viewType);
        mCallback.afterCreateFixedViewHolder(vh, viewType);
        return vh;
    }

    private void bindFixedViewHolder(ViewGroup sectionContainer, VH viewHolder, int position) {
        mCallback.bindViewHolder(viewHolder, position);
        mCallback.afterBindFixedViewHolder(viewHolder, position);
        sectionContainer.removeAllViews();
        sectionContainer.addView(viewHolder.itemView);
    }



    public interface Callback<ViewHolder> {
        /**
         * @param position 任意 pos
         * @return 获取 pos 对应的 sectionHeader 的 pos
         */
        int getRelativeFixedItemPosition(int position);


        boolean isHeader(int position);

        /**
         * 在 RecyclerView.Adapter#onCreateViewHolder 之后的方法
         */
        void afterCreateFixedViewHolder(ViewHolder viewHolder, int viewType);

        /**
         * 在 RecyclerView.Adapter#bindViewHolder 之后的渲染方法
         */
        void afterBindFixedViewHolder(ViewHolder viewHolder, int pos);

        ViewHolder createViewHolder(ViewGroup parent, int viewType);

        void bindViewHolder(ViewHolder holder, int position);

        int getItemViewType(int position);

        void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer);

        void onHeaderVisibilityChanged(boolean visible);
    }

}
