package com.fang.controller;

import com.fang.common.result.Result;
import com.fang.constants.ApiPathConstants;
import com.fang.domain.entity.sys.SysResource;
import com.fang.service.sys.SysSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SwaggerResource;

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
    private static final String DEFAULT_GROUP = "default";
    @GetMapping("/get-api")
    @ApiOperation("获取接口")
    public Result<List<SwaggerResource>> getAgent(){
        return Result.success(sysSourceService.getResource());
    }

    @GetMapping("/get-resources")
    @ApiOperation("获取接口集合")
    public Result<List<SysResource>> getResourceByGroupName(){
        return Result.success(sysSourceService.getResourceByGroupName(DEFAULT_GROUP)) ;
    }
}
