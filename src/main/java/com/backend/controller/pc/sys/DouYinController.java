package com.backend.controller.pc.sys;

import com.backend.annotation.Log;
import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.service.sys.DouYinService;
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
@RequestMapping(ApiPathConstants.SYS_DOU_YIN)
@RequiredArgsConstructor
@Deprecated
@Api(tags = "抖音")
@Log(excludeMethodType={})
public class DouYinController {
    private final DouYinService douYinService;
    @GetMapping("parseDouYinUrl")
    @ApiOperation("抖音分享链接解析")
    public Result<String> parseDouYinUrl(String url)  {
        douYinService.parseUrl(url);
        return Result.success("OJBK");
    }
}
