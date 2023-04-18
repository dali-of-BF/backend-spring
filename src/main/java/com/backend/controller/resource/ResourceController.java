package com.backend.controller.resource;

import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.constants.SwaggerGroupConstants;
import com.backend.domain.dto.BasePageDTO;
import com.backend.domain.entity.sys.SysResource;
import com.backend.service.sys.SysSourceService;
import com.backend.utils.ClassUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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

    @GetMapping("/get-resources")
    @ApiOperation("获取系统接口集合")
    public Result<List<SysResource>> getResourceByGroupName(String groupName) throws IllegalAccessException {
        return Result.success(sysSourceService.getResourceByGroupNameList(StringUtils.isNotBlank(groupName) ?
                Collections.singletonList(groupName):ClassUtils.getAllFieldValue(SwaggerGroupConstants.class))) ;
    }

    @PostMapping("/do-refresh-resource")
    @ApiOperation("扫描接口并且更新sys_resource表")
    public Result<List<SysResource>> doRefreshResource() throws IllegalAccessException {
        return Result.success(sysSourceService.doRefreshResource()) ;
    }

    @GetMapping("/page")
    @ApiOperation("分页获取系统表列表")
    public Result<IPage<SysResource>> getResourceList(BasePageDTO dto){
        Page<SysResource> page = new Page<>(dto.getPage(),dto.getSize());
        return Result.success(sysSourceService.page(page));
    }
}
