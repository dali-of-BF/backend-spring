package com.backend.controller.test;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

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

    private final WxMaService wxMaService;

    @ApiOperation("获取微信授权信息")
    @ApiImplicitParam(name = "code",value = "前端授权登录后传来的code", required = true,paramType = "query")
    @PostMapping(value = "/wechatSession")
    public Result<WxMaJscode2SessionResult> wechatSession(@RequestParam String code) throws WxErrorException {
        //获取openId、unionid、session_key
        return Result.success(wxMaService.getUserService().getSessionInfo(code));
    }


    @ApiOperation("小程序手机号登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessionKey", value = "sessionKey", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "encryptedData", value = "加密串", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(name = "iv", value = "偏移量", paramType = "query", dataType = "string", required = true)
    })
    @PostMapping(value = "/wechatLogin")
    @ResponseBody
    public Result<WxMaJscode2SessionResult> wechatLogin(HttpServletRequest request,
                                                        @RequestParam @NotBlank(message = "sessionKey不能为空") String code) throws WxErrorException {
        WxMaPhoneNumberInfo phoneInfo = wxMaService.getUserService().getPhoneNoInfo(code);
        return Result.success(phoneInfo.getPurePhoneNumber());
    }

}
