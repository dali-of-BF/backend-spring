package com.backend.domain.vo.pc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FPH
 * @since 2023年8月12日23:14:41
 *
 * 菜单树
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeVO {
    private String id;
    private String menuName;
    private Integer status;
    private String parentId;
    private String menuLevel;
    private String menuType;
    private String sort;
    private String router;
    private String appId;
    private String permission;
    /**
     * 菜单孩子节点
     */
    private MenuTreeVO childMenuList;
}
