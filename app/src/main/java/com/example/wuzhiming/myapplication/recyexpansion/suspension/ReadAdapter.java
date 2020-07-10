package com.example.wuzhiming.myapplication.recyexpansion.suspension;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.recyexpansion.suspension.bean.Section;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ReadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 多布局
    public static final int ITEM_TYPE_SECTION_HEADER = 0;
    public static final int ITEM_TYPE_SECTION_ITEM = 1;


    //diff工具变换之前，上一次adapter数据（变换完之后要更新到最新状态）
    private List<Section> mLastData = new ArrayList<>();
    //adapter初始数据
    private List<Section> mData = new ArrayList<>();

    //映射着当前adapterPosition对应的真实section索引
    private SparseArray<Integer> mSectionIndex = new SparseArray<>();
    //存储每一个adapterIndex对应的item
    private SparseArray<Integer> mItemIndex = new SparseArray<>();


    private Context mContext;

    public void setData(List<Section> mSectionList) {
        mData.clear();
        mData.addAll(mSectionList);
        diff(true);
    }

    /**
     * @param retValue 是否需要重新创建
     */
    private void diff(boolean retValue) {
        MydiffCallback mydiffCallback = new MydiffCallback(mLastData, mData);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mydiffCallback, false);
        // 是有必要的
        mydiffCallback.generateIndex(mData, mSectionIndex, mItemIndex);
        diffResult.dispatchUpdatesTo(this);

        // 每次diff工具，变换完数据，要将mLadtData更新到最新
        if (retValue) {
            //初始化逻辑，深拷贝
            mLastData.clear();
            for (Section section : mData) {
                mLastData.add(section.copy());
            }
        } else {
            //刷新数据到mLastData中，与mData保持一致
            for (int i = 0; i < mData.size(); i++) {
                Section section = mData.get(i);
                section.cloneStatusTo(mLastData.get(i));
            }
        }
    }

    /**
     * 构造方法 --
     *
     * @param context
     */
    public ReadAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        if (i == ITEM_TYPE_SECTION_HEADER) {
            return new SectionHeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.item_section_header, viewGroup, false));
        } else {
            return new SectionItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_section_item, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder viewHolder, int i) {
        int sectionIndex = mSectionIndex.get(i);
        Section section = mData.get(sectionIndex);

        int itemIndex = mItemIndex.get(i);

        if (viewHolder instanceof SectionHeaderHolder) {
            SectionHeaderHolder headerHolder = (SectionHeaderHolder) viewHolder;
            // 设置了状态了...
            headerHolder.mIvArrow.setRotation(section.isFold() ? 90f : 270f);
            headerHolder.mTvContent.setText(section.getHeader().getTitle());
            headerHolder.itemView.setOnClickListener(new HeadClickListener(headerHolder, i));
        } else {
            final SectionItemHolder itemHolder = (SectionItemHolder) viewHolder;
            itemHolder.mTvContent.setText(section.getItemList().get(itemIndex).getContent());
        }


    }




    /**
     * @param pos 将item的adapter索引，定位关联到对应头布局的adapter索引
     * @return
     */
    public int getRelativeFixedItemPosition(int pos) {
        int position = pos;
        while (getItemViewType(position) != ReadAdapter.ITEM_TYPE_SECTION_HEADER) {
            position--;
            if (position < 0) {
                return RecyclerView.NO_POSITION;
            }
        }
        return position;
    }

    public void afterBindFixedViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener(new HeadClickListener(viewHolder, position));
    }


    private void onItemClick(int adapterPosition) {
        if (mItemIndex == null) {
            return;
        }
        if (getItemViewType(adapterPosition) == ITEM_TYPE_SECTION_HEADER) {
            toggleFold(adapterPosition);
        }
    }

    private void toggleFold(int adapterPosition) {
        Section section = mData.get(mSectionIndex.get(adapterPosition));
        section.setFold(!section.isFold());
        diff(false);
    }

    @Override
    public int getItemCount() {
        return null == mItemIndex ? 0 : mItemIndex.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("item", mItemIndex.size() + "  详细情况 = " + mItemIndex.toString());
        int layout = mItemIndex.get(position);
        int itemType;
        switch (layout) {
            case MydiffCallback.ITEM_INDEX_SECTION_HEADER:
                //单独的 头title
                itemType = ITEM_TYPE_SECTION_HEADER;
                break;
            default:
                //itemSize
                itemType = ITEM_TYPE_SECTION_ITEM;
                break;
        }
        return itemType;
    }

    private class HeadClickListener implements View.OnClickListener {
        private RecyclerView.ViewHolder mSectionHeaderHolder;
        private int position;

        public HeadClickListener(RecyclerView.ViewHolder sectionHeaderHolder, int position) {
            mSectionHeaderHolder = sectionHeaderHolder;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(position);
            }
        }

    }


    class SectionHeaderHolder extends RecyclerView.ViewHolder{
        public TextView mTvContent;
        public ImageView mIvArrow;

        public SectionHeaderHolder( View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tvContent);
            mIvArrow = itemView.findViewById(R.id.ivArrow);
        }
    }
    class SectionItemHolder extends RecyclerView.ViewHolder {
        public TextView mTvContent;
        public SectionItemHolder( View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tvContent);
        }
    }

}
