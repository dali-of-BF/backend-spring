package com.backend.enums.sys;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

@Getter
public enum MenuTypeEnum implements IEnum<Integer> {
    /**
     * 一级菜单
     */
    LEVEN_ONE(1, "一级菜单"),
    LEVEN_TWO(2, "二级菜单"),
    LEVEN_THREE(3, "三级菜单");

    private final Integer value;
    private final String desc;

    MenuTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
