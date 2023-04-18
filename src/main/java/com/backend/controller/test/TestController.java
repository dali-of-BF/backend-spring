package com.backend.controller.test;

import com.backend.common.result.Result;
import com.backend.config.ApplicationProperties;
import com.backend.constants.ApiPathConstants;
import com.backend.constants.SwaggerGroupConstants;
import com.backend.utils.ClassUtils;
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

    @GetMapping("test")
    @ApiOperation("获取一个属性的所有方法的其中的值")
    public Result<Object> testProperties() throws IllegalAccessException {
        return Result.success(ClassUtils.getAllFieldKey(SwaggerGroupConstants.class));
    }
}
