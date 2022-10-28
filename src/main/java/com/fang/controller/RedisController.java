package com.fang.controller;

import com.fang.common.utils.ResultUtil;
import com.fang.constants.ApiPathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(ApiPathConstants.REDIS)
@RequiredArgsConstructor
@Api(tags = "redis测试类")
public class RedisController {

    private final RedisTemplate<String,String> redisTemplate;

    @PostMapping("/save")
    @ApiOperation("保存redis")
    public ResponseEntity<String> saveRedis(String key, String message){
        redisTemplate.opsForValue().set(key,message);
        return ResultUtil.success(redisTemplate.opsForValue().get(key));
    }

    @GetMapping("/query")
    @ApiOperation("查询redis")
    public ResponseEntity<String> query(String key){
        return ResultUtil.success(redisTemplate.opsForValue().get(key));
    }
}
