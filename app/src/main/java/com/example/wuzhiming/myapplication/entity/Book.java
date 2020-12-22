package com.example.wuzhiming.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:      明桑
 * Date:        2020/12/22
 * Describe:    添加描述
 */
public class Book implements Parcelable {
    private String name;

    public Book(String name) {
        this.name = name;
    }

    public Book() {
    }

    protected Book(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public void readFromParcel(Parcel in){
        this.name=in.readString();
    }

    @Override
    public String toString() {
        return "book name：" + name;
    }
}
