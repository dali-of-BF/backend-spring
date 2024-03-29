package com.fang.enums.sys;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * @author FPH
 */

@Getter
public enum DeletedEnum implements IEnum<Integer> {

    UNDELETED(0, "未删除"),
    DELETED(1, "已删除");

    private final Integer value;
    private final String desc;

    DeletedEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
