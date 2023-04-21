package com.backend.enums.sys;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * @author FPH
 * @since 是否超管枚举
 */
@Getter
public enum IsSuperEnum implements IEnum<String> {
    ENABLE("1", "超管"),
    DISABLE("0", "普通用户");

    private final String value;
    private final String desc;

    IsSuperEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
