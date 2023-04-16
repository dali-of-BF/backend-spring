package com.fang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fang.common.result.Result;
import com.fang.constants.ApiPathConstants;
import com.fang.domain.dto.BasePageDTO;
import com.fang.domain.entity.sys.SysResource;
import com.fang.service.sys.SysSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FPH
 * @since 2022年11月18日11:39:50
 */
@RestController
@Slf4j
@RequestMapping(ApiPathConstants.RESOURCE)
@RequiredArgsConstructor
@Api(tags = "资源表扫描")
public class ResourceController {

    private final SysSourceService sysSourceService;

    /**
     * 一般分组是default
     */
    public static final String DEFAULT_GROUP = "default";

    @GetMapping("/get-resources")
    @ApiOperation("获取系统接口集合")
    public Result<List<SysResource>> getResourceByGroupName(){
        return Result.success(sysSourceService.getResourceByGroupName(DEFAULT_GROUP)) ;
    }

    @PostMapping("/do-refresh-resource")
    @ApiOperation("扫描接口并且更新sys_resource表")
    public Result<List<SysResource>> doRefreshResource(){
        return Result.success(sysSourceService.doRefreshResource()) ;
    }

    @GetMapping("/list")
    @ApiOperation("获取资源表信息")
    public Result<IPage<SysResource>> getResourceList(){
        BasePageDTO basePageDTO = new BasePageDTO();
        Page<SysResource> page = new Page<>(basePageDTO.getPage(),basePageDTO.getSize());
        return Result.success(sysSourceService.page(page));
    }
}
