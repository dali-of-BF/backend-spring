package com.fang.enums.sys;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * @author FPH
 */

@Getter
public enum SysAccountDeletedEnum implements IEnum<String> {

    UNDELETED("0", "未删除"),
    DELETED("1", "已删除");

    private final String value;
    private final String desc;

    SysAccountDeletedEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
