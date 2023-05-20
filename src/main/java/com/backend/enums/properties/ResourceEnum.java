package com.backend.enums.properties;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * @author FPH
 * applicationProperties中资源表是否开启初始化枚举
 */

@Getter
public enum ResourceEnum implements IEnum<String> {
    /**
     * 不开启初始化
     */
    UNINIT("unInit", "不开启初始化"),
    /**
     * 开启初始化
     */
    INIT("init", "开启初始化");

    private final String value;
    private final String desc;

    ResourceEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
