package com.backend.enums.sys;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * @author FPH
 */

@Getter
public enum AppIdEnum implements IEnum<String> {

    PC("pc", "pc端"),
    WX("wx", "wx端");

    private final String value;
    private final String desc;

    AppIdEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
