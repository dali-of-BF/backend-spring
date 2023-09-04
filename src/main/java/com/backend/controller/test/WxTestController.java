package com.backend.controller.test;

import com.backend.constants.ApiPathConstants;
import com.backend.constants.Constant;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * @author FPH
 * @since 2023年9月4日21:59:10
 *
 */
@RestController
@RequestMapping(ApiPathConstants.TEST_WX)
@RequiredArgsConstructor
@Api(tags = "微信小程序测试类")
@Slf4j
public class WxTestController {
    private final WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(String returnUrl){
        String url = "https://qianguxuanyu.cn/backend/test/wx/userinfo";
        String encode = null;
        try {
            encode = URLEncoder.encode(returnUrl, Constant.UTF8);
        }catch (Exception e){
            log.error(e.getMessage());
        }
//        String redirectUrl = wxMpService.getOAuth2Service();
        return null;
    }
}
