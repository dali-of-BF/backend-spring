package com.fang.controller;

import com.fang.common.result.Result;
import com.fang.constants.ApiPathConstants;
import com.fang.service.userAgent.UserAgentService;
import eu.bitwalker.useragentutils.Browser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FPH
 * @since 2022年11月18日11:39:50
 */
@RestController
@Slf4j
@RequestMapping(ApiPathConstants.USER_AGENT)
@RequiredArgsConstructor
@Api(tags = "浏览器解析")
public class UserAgentController {

    private final UserAgentService userAgentService;

    @GetMapping("/get-agent")
    @ApiOperation("获取用户Agent")
    public Result<String> getAgent(HttpServletRequest request){
        return Result.success(userAgentService.getAgent(request),"");
    }

    @GetMapping("/get-browser")
    @ApiOperation("获取浏览器对象")
    public Result<Browser> getBrowserDetail(HttpServletRequest request){
        return Result.success(userAgentService.getBrowserDetail(request));
    }

    @GetMapping("/get-operation-system")
    @ApiOperation("获取操作系统对象")
    public Result<Browser> getOperationSystem(HttpServletRequest request){
        return Result.success(userAgentService.getOperationSystem(request));
    }
}
