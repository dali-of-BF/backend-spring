package com.fang.controller;

import com.fang.common.result.Result;
import com.fang.config.ApplicationProperties;
import com.fang.constants.ApiPathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author FPH
 */
@RestController
@RequestMapping(ApiPathConstants.TEST)
@RequiredArgsConstructor
@Api(tags = "测试控制类")
public class TestController {

    private final ApplicationProperties applicationProperties;

    @GetMapping("testProperties")
    @ApiOperation("测试yml获取")
    public Result<Object> testProperties(){
        return Result.success(applicationProperties.getSecurity().getExpirationTime_rememberMe());
    }
}
