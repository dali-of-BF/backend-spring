package com.backend.service.sys;

import com.backend.domain.entity.sys.SysMenu;
import com.backend.domain.entity.sys.SysResource;
import com.backend.enums.sys.AppIdEnum;
import com.backend.enums.sys.MenuTypeEnum;
import com.backend.enums.sys.StatusEnum;
import com.backend.mapper.sys.SysMenuMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FPH
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {
    private final SysSourceService sysSourceService;

    @Transactional(rollbackFor = Exception.class)
    public List<SysMenu> refactorMenu() {
        //清除二级三级菜单
        this.remove(new LambdaQueryWrapper<SysMenu>()
                .notIn(SysMenu::getMenuLevel, Collections.singletonList(MenuTypeEnum.LEVEN_ONE.getValue())));
        //最终要返回的菜单集合
        List<SysMenu> menuList = Lists.newArrayList();
        //二级菜单返回集合
        List<SysMenu> menuTwoList = Lists.newArrayList();
        Set<SysResource> resource = sysSourceService.getResource();
        Map<String, List<SysResource>> appToResourceMap = resource.stream()
                .filter(i -> AppIdEnum.getAllAppIdType().contains(i.getAppId())).collect(Collectors.groupingBy(SysResource::getAppId));
        appToResourceMap.forEach((i, j) -> {
            String sortLvTwoAppId = "";
            String sortLvThreeMenuName = "";
            //这里的sortLvThree是为第三级排序
            int sortLvThree = 0;
            int sortLvTwo = 0;
            //针对tag进行排序，以便进行sort
            List<SysResource> collect = j.stream().sorted(Comparator.comparing(SysResource::getTag)).collect(Collectors.toList());
            //这里处理二级菜单
            List<SysResource> menuLvTwoResource = collect.stream().map(tag -> {
                SysResource sysResource = new SysResource();
                sysResource.setAppId(tag.getAppId());
                sysResource.setTag(tag.getTag());
                return sysResource;
            }).collect(Collectors.toSet())
                    .stream().sorted(Comparator.comparing(SysResource::getAppId)).collect(Collectors.toList());
            for (SysResource sysResource : menuLvTwoResource) {
                SysMenu sysMenu = new SysMenu();
                sysMenu.setMenuName(sysResource.getTag());
                sysMenu.setMenuType(MenuTypeEnum.LEVEN_TWO.getValue());
                sysMenu.setMenuLevel(MenuTypeEnum.LEVEN_TWO.getValue());
                //判断排序
                if (!sysResource.getAppId().equals(sortLvTwoAppId)) {
                    //这里表示应设为二级菜单，其余为三级菜单，一级菜单自己手动配置
                    sortLvTwo = 0;
                }
                sortLvTwo++;
                sortLvTwoAppId = sysResource.getAppId();
                sysMenu.setSort(sortLvTwo);
                sysMenu.setStatus(StatusEnum.ENABLE.getValue());
                sysMenu.setAppId(sysResource.getAppId());
                menuTwoList.add(sysMenu);
            }
            //先保存，生成id以供三级菜单的父菜单找得到
            this.saveBatch(menuTwoList);
            //这里处理三级菜单
            for (SysResource sysResource : collect) {
                SysMenu sysMenu = new SysMenu();
                sysMenu.setMenuName(sysResource.getRemark());
                sysMenu.setMenuType(MenuTypeEnum.LEVEN_THREE.getValue());
                sysMenu.setMenuLevel(MenuTypeEnum.LEVEN_THREE.getValue());
                //判断排序
                if (!sysResource.getTag().equals(sortLvThreeMenuName)) {
                    //这里表示应设为二级菜单，其余为三级菜单，一级菜单自己手动配置
                    sortLvThree = 0;
                }
                sortLvThree++;
                sortLvThreeMenuName = sysResource.getTag();
                sysMenu.setSort(sortLvThree);
                sysMenu.setAppId(i);
                sysMenu.setStatus(StatusEnum.ENABLE.getValue());
                //判断此menu归属于哪个二级菜单下
                SysMenu menuTwo = menuTwoList.stream().filter(o ->
                        sysMenu.getAppId().equals(o.getAppId()) && sysResource.getTag().equals(o.getMenuName()))
                        .findFirst().orElse(null);
                sysMenu.setParentId(Objects.nonNull(menuTwo)?menuTwo.getId():"-1");
                menuList.add(sysMenu);
            }
        });
        this.saveBatch(menuList);
        return menuList;
    }
}
