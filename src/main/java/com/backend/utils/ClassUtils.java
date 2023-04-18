package com.backend.utils;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author FPH
 * @since 2023年4月18日16:55:08
 * 反射操作工具包
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {

    /**
     * 获取此类所有的属性中的值
     * @return
     * @throws IllegalAccessException
     */
    public static List<String> getAllFieldValue(Class<?> object) throws IllegalAccessException {
        List<String> resultList = Lists.newArrayList();
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {
            //设置当前属性为可访问
            field.setAccessible(Boolean.TRUE);
            resultList.add(String.valueOf(field.get(object)));
        }
        return resultList;
    }

    /**
     * 获取此类所有的属性名
     * @return
     * @throws IllegalAccessException
     */
    public static List<String> getAllFieldKey(Class<?> object) throws IllegalAccessException {
        List<String> resultList = Lists.newArrayList();
        Field[] fields = object.getDeclaredFields();
        for (Field field : fields) {
            //设置当前属性为可访问
            field.setAccessible(Boolean.TRUE);
            resultList.add(field.getName());
        }
        return resultList;
    }
}
