package com.example.wuzhiming.myapplication.recyexpansion.suspension.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *          通过 list.get(i)来获取具体的section
 *          header+item即为展开的数据  position-1为每一个item
 *
 */
public class Section {
    private Header mHeader;
    private List<Item> mItemList;
    // 是否折叠
    private boolean isFold;

    private boolean isLoadBeforeError = false;
    private boolean isLoadAfterError = false;

    public boolean isLoadBeforeError() {
        return isLoadBeforeError;
    }

    public void setLoadBeforeError(boolean loadBeforeError) {
        isLoadBeforeError = loadBeforeError;
    }

    public boolean isLoadAfterError() {
        return isLoadAfterError;
    }

    public void setLoadAfterError(boolean loadAfterError) {
        isLoadAfterError = loadAfterError;
    }

    public Section(Header header, List<Item> itemList, boolean isFold) {
        mHeader = header;
        mItemList = itemList;
        this.isFold = isFold;
    }

    public int getChildAccount(){
        if (mItemList != null){
            return mItemList.size();
        }
        return 0;
    }

    public Header getHeader() {
        return mHeader;
    }

    public void setHeader(Header header) {
        mHeader = header;
    }

    public List<Item> getItemList() {
        return mItemList;
    }

    public void setItemList(List<Item> itemList) {
        mItemList = itemList;
    }

    public boolean isFold() {
        return isFold;
    }

    public void setFold(boolean fold) {
        isFold = fold;
    }


    public Section copy(){
        List<Item> mNewList = new ArrayList<>();
        for (Item item:mItemList){
            mNewList.add(item.copy());
        }
        Section section = new Section(mHeader.copy(),mNewList,isFold);
        section.setLoadAfterError(isLoadAfterError);
        section.setLoadBeforeError(isLoadBeforeError);
        return section;
    }

    public void cloneStatusTo(Section section){
        section.isFold = isFold;
        section.isLoadAfterError = isLoadAfterError;
        section.isLoadBeforeError = isLoadBeforeError;
    }

}
