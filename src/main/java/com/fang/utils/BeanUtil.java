package com.fang.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author FPH
 * @since 2022年6月2日15:15:33
 * BeanUtils重写
 */
public class BeanUtil extends BeanUtils {

    /**
     * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
     * @param sources 数据源类
     * @param target 数据目标类
     * @return
     * @param <T>
     * @param <S>
     */
    public static <T, S> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            System.out.println(t.getClass().getTypeName());
            copyProperties(source, t);
            list.add(t);
        }
        return list;
    }

    public static <T> T test(T t){
        System.out.println(t.getClass().getTypeName());
        try {
            System.out.println(t.getClass().newInstance());
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

}
