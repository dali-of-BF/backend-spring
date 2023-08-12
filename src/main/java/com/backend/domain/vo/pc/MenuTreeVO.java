package com.backend.domain.vo.pc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author FPH
 * @since 2023年8月12日23:14:41
 *
 * 菜单树
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuTreeVO {
    private String id;
    private String menuName;
    private Integer status;
    private String parentId;
    private Integer menuLevel;
    private Integer menuType;
    private Integer sort;
    private String router;
    private String appId;
    private String permission;
    /**
     * 菜单孩子节点
     */
    private List<MenuTreeVO> childMenuList;
}
