package com.example.wuzhiming.myapplication.recyexpansion.suspension;

import android.util.SparseArray;

import com.example.wuzhiming.myapplication.recyexpansion.suspension.bean.Section;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class MydiffCallback extends DiffUtil.Callback {
    /**
     * 头布局 类型
     */
    public static final int ITEM_INDEX_SECTION_HEADER = -1;

    private SparseArray<Integer> mOldSectionIndex = new SparseArray<>();
    private SparseArray<Integer> mOldItemIndex = new SparseArray<>();

    private SparseArray<Integer> mNewSectionIndex = new SparseArray<>();
    private SparseArray<Integer> mNewItemIndex = new SparseArray<>();

    private List<Section> mOldList;
    private List<Section> mNewList;

    /**
     * 构造函数传入老的以及新的list
     * @param oldList
     * @param newList
     */
    public MydiffCallback(List<Section> oldList, List<Section> newList) {
        mOldList = oldList;
        mNewList = newList;
        generateIndex(oldList,mOldSectionIndex,mOldItemIndex);
        generateIndex(newList,mNewSectionIndex,mNewItemIndex);
    }


    /**
     * 获得老数据的长度
     * @return
     */
    @Override
    public int getOldListSize() {
        return mOldSectionIndex.size();
    }

    /**
     * 获得新数据的长度
     * @return
     */
    @Override
    public int getNewListSize() {
        return mNewSectionIndex.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        int oldSectionIndex = mOldSectionIndex.get(i);
        int oldItemIndex = mOldItemIndex.get(i);
        Section section = mOldList.get(oldSectionIndex);

        int newSectionIndex = mNewSectionIndex.get(i1);
        int newItemIndex = mNewItemIndex.get(i1);
        Section newSection = mNewList.get(newSectionIndex);
        if (!section.getHeader().equals(newSection.getHeader())){
            //变化前后  当前索引对应的头布局文字不相等
            return false;
        }
        if (oldItemIndex < 0 && oldItemIndex == newItemIndex) {
            //变化前后 当前索引对应的都是头布局
            return true;
        }

        if (oldItemIndex < 0 || newItemIndex < 0) {
            //变化前后 当前索引对应的item类型，是非头布局（可能是上下loading等待扩充类型）
            return false;
        }

        //比较的是 变化前后 当前索引对应的 item内容比较
        return section.getItemList().get(oldItemIndex).equals(newSection.getItemList().get(newItemIndex));
    }


    @Override
    public boolean areContentsTheSame(int i, int i1) {

        int oldSectionIndex = mOldSectionIndex.get(i);
        int oldItemIndex = mOldItemIndex.get(i);
        Section section = mOldList.get(oldSectionIndex);

        int newSectionIndex = mNewSectionIndex.get(i1);
        Section newSection = mNewList.get(newSectionIndex);

        if (oldItemIndex == ITEM_INDEX_SECTION_HEADER){
            //比较 头布局中 展开状态
            return section.isFold() == newSection.isFold();
        }

        return true;
    }


    /**
     * 将元数据bean list，生成与adapter有对应规则的sectionIndex、itemIndex
     *
     * @param list    元数据bean list
     * @param sectionIndex  键值对为（递增数，section的索引）
     * @param itemIndex    键值对为（递增数，header类型————为-1||item类型————为item在当前mItemList中的索引）

     * 递增数为： list<Section>.size() + list中展开的itemlist.size()
     * 递增数对应的是adapterPosition
     */
    public void generateIndex(List<Section> list,SparseArray<Integer> sectionIndex,SparseArray<Integer> itemIndex){
        // 填充list前先进行清除
        sectionIndex.clear();
        itemIndex.clear();


        for (int j=0,i =0;j<list.size();j++){
            // 获取某一个section Bean
            Section section = list.get(j);
            // 如果该item未被锁定
                sectionIndex.append(i,j);
                // 首先添加头
                itemIndex.append(i,ITEM_INDEX_SECTION_HEADER);
                i++;
                // 如果未被折叠而且含有item的话
                if (!section.isFold() && section.getChildAccount() > 0){
                    // 循环添加普通的item即可
                    for (int k=0;k<section.getChildAccount();k++) {
                        sectionIndex.append(i, j);
                        itemIndex.append(i, k);
                        i++;
                    }

                }

        }

    }
}
