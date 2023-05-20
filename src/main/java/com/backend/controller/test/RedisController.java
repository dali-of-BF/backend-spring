package com.backend.controller.test;

import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis测试控制器
 * @author FPH
 * @since 2022年10月28日10:03:44
 */
@RestController
@RequestMapping(ApiPathConstants.TEST_REDIS)
@RequiredArgsConstructor
@Api(tags = "redis测试类")
public class RedisController {

    private final RedisUtils redisUtils;

    @PostMapping("/save")
    @ApiOperation("保存redis")
    public Result<String> saveRedis(String key, String message){
        redisUtils.setCacheObject(key,message);
        return Result.success("保存成功");
    }

    @GetMapping("/query")
    @ApiOperation("查询redis")
    public Result<String> query(String key){
        return Result.success(redisUtils.getCacheObject(key),"");
    }
}
