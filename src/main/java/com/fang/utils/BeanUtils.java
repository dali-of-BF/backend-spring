package com.fang.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FPH
 * @since 2022年6月2日15:15:33
 * BeanUtils重写
 */
public class  BeanUtils extends org.springframework.beans.BeanUtils {

//    public static <T> List<T> copyToList(List<T> os,List<T> to){
//        for (T o : os) {
//            String typeName = to.getClass().getTypeName();
//            org.springframework.beans.BeanUtils.copyProperties(o,object);
//            to.add(object);
//        }
//        return objects;
//    }

    public static <T> T test(T t){
        System.out.println(t.getClass().getTypeName());
        System.out.println(t.getClass().getClasses().length);
        return t;
    }
}
