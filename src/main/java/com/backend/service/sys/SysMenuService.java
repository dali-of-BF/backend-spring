package com.backend.service.sys;

import com.backend.domain.entity.sys.SysMenu;
import com.backend.domain.entity.sys.SysResource;
import com.backend.enums.sys.AppIdEnum;
import com.backend.mapper.sys.SysMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author FPH
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {
    private final SysSourceService sysSourceService;

    public List<SysMenu> refactorMenu() {
        List<SysMenu> menuList = Lists.newArrayList();
        Set<SysResource> resource = sysSourceService.getResource();
        Map<String, List<SysResource>> appToResourceMap =
                resource.stream().filter(i -> AppIdEnum.getAllAppIdType().contains(i.getAppId()))
                        .collect(Collectors.groupingBy(SysResource::getAppId));
        appToResourceMap.forEach((i, j) -> {
            SysMenu sysMenu = new SysMenu();

            menuList.add(sysMenu);
        });
        return menuList;
    }
}
