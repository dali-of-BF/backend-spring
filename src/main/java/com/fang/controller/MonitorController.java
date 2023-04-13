package com.fang.controller;

import com.fang.common.result.Result;
import com.fang.constants.ApiPathConstants;
import com.fang.domain.entity.system.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * system查看
 * @author FPH
 * @since 2023年4月12日23:04:16
 */
@RestController
@RequestMapping(ApiPathConstants.MONITOR)
@RequiredArgsConstructor
@Api(tags = "查看本机system")
public class MonitorController {

    @GetMapping("/server")
    @ApiOperation("获取监控信息")
    public Result<Server> getServer() throws Exception {
        Server server = new Server();
        server.copyTo();
        return Result.success(server);
    }
}
