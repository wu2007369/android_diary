package com.example.wuzhiming.myapplication.itext;

public interface ItextCallBack {
    /**
     * @param index 当前切割分拆的页面索引
     * @param length 大pdf文件的总页数长度
     */
    void onProgress(int index,int length);
}
