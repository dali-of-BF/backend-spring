package com.backend.controller.test;

import com.backend.common.result.Result;
import com.backend.config.ApplicationProperties;
import com.backend.constants.ApiPathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FPH
 * @since 2023年9月12日09:39:58
 */
@RestController
@RequestMapping(ApiPathConstants.TEST_CONFIG)
@RequiredArgsConstructor
@Api(tags = "配置文件测试类")
public class ConfigTestController {
    private final ApplicationProperties applicationProperties;

    @GetMapping("/get-config")
    @ApiOperation("获取自定义配置文件")
    public Result<ApplicationProperties> getConfig(){
        return Result.success(applicationProperties);
    }
}
