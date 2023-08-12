package com.backend.enums.sys;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * @author FPH
 */

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

    /**
     * 获取初始化的菜单级别（一级）
     * @return
     */
    public static MenuTypeEnum initMenuType(){
        return MenuTypeEnum.LEVEN_ONE;
    }

    /**
     * 通过当前菜单级别获取下一级菜单级别
     * @return
     */
    public static MenuTypeEnum nextMenuType(MenuTypeEnum menuTypeEnum){
        //下一级菜单级别
        Integer  nextValue = menuTypeEnum.getValue()+1;
        for (MenuTypeEnum typeEnum : MenuTypeEnum.values()) {
            if (typeEnum.getValue().equals(nextValue)) {
                return typeEnum;
            }
        }
        return null;
    }

}
