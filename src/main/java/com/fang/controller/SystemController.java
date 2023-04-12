package com.fang.controller;

import com.fang.common.result.Result;
import com.fang.constants.ApiPathConstants;
import com.fang.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;

import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * system查看
 * @author FPH
 * @since 2023年4月12日23:04:16
 */
@RestController
@RequestMapping(ApiPathConstants.SYSTEM)
@RequiredArgsConstructor
@Api(tags = "查看本机system")
public class SystemController {

    @GetMapping("/current-platform")
    @ApiOperation("当前平台")
    public Result<String> currentPlatform(){
        return Result.success(SystemInfo.getCurrentPlatform().getName(),"");
    }
    @GetMapping("/runTimeMX")
    @ApiOperation("当前平台")
    public Result<String> query(){
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        String asd = ManagementFactory.getRuntimeMXBean().getName();
        return Result.success(TimeUtils.toText(new Date(startTime)),"");
    }
}
