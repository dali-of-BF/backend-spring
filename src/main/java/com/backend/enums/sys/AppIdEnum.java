package com.backend.enums.sys;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * @author FPH
 */

@Getter
public enum AppIdEnum implements IEnum<String> {
    /**
     * pc端
     */
    PC("pc", "pc端"),
    WX("wx", "wx端");

    private final String value;
    private final String desc;

    AppIdEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static List<String> getAllAppIdType(){
        List<String> ret = Lists.newArrayList();
        for (AppIdEnum appIdEnum : AppIdEnum.values()) {
            ret.add(appIdEnum.getValue());
        }
        return ret;
    }
}
