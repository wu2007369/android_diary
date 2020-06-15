package com.example.wuzhiming.myapplication.utils;

import android.text.TextUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommonUtils {
    /**
     *判断一个实体类对象实例,存在一个变量是否为空
     *@param obj 校验的类对象实例
     *@return List
     *@throws Exception
     */

    public static boolean isObjectHasFieldEmpty(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();  //得到类对象
        Field[] fs = clazz.getDeclaredFields(); //得到属性集合
        List<String> list = new ArrayList<String>();
        for (Field field : fs) {            //遍历属性
            field.setAccessible(true); //设置属性是可以访问的（私有的也可以）
            if (field.get(obj) == null || TextUtils.equals((String)field.get(obj),"null")) {
                return true;
            }
        }
        return false;
    }


    /**
     * 实现list的深拷贝
     * @param tClass
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> deepCopy(Class<T> tClass,List<T> list){
        //list深度拷贝
        List<T> newList = new ArrayList<>();
        T[] array = (T[]) Array.newInstance(tClass,list.size());
        newList.addAll(Arrays.asList(array));
        Collections.copy(newList, list);
        return newList;
    }
}
