package com.backend.enums.sys;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * @author FPH
 */

@Getter
public enum StatusEnum implements IEnum<Integer> {
    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private final Integer value;
    private final String desc;

    StatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
