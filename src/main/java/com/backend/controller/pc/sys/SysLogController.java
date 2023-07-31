package com.backend.controller.pc.sys;

import com.backend.annotation.Log;
import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.domain.dto.sys.log.LogPageDTO;
import com.backend.domain.entity.sys.SysLog;
import com.backend.service.sys.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author FPH
 */
@RestController
@RequestMapping(ApiPathConstants.SYS_LOG)
@RequiredArgsConstructor
@Api(tags = "系统日志管理")
@Log
public class SysLogController {

    private final SysLogService sysLogService;

    @GetMapping("page")
    @ApiOperation("获取日志分页")
    public Result<IPage<SysLog>> page(@Valid LogPageDTO dto){
        Page<SysLog> page = new Page<>(dto.getPage(), dto.getSize());
        return Result.success(sysLogService.page(page,
                new LambdaQueryWrapper<SysLog>()
                        .like(StringUtils.isNotBlank(dto.getIp()),SysLog::getIp,dto.getIp())
                        .eq(StringUtils.isNotBlank(dto.getStatus()),SysLog::getStatus,dto.getStatus())
                        .orderByDesc(SysLog::getOperTime)));
    }
}
