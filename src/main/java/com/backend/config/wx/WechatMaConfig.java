package com.backend.config.wx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.backend.config.ApplicationProperties;
import com.backend.constants.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author FPH
 * @since 2023年9月4日21:13:41
 * 将appId与密钥设置到微信SDK中
 */
@Component
@RequiredArgsConstructor
public class WechatMaConfig {
    private final ApplicationProperties applicationProperties;
    @Bean
    public WxMaService wxMaService(){
        WxMaServiceImpl wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(this.wxMaConfigStorage());
        return wxMaService;
    }

    public WxMaConfig wxMaConfigStorage(){
        WxMaDefaultConfigImpl wxMaDefaultConfig = new WxMaDefaultConfigImpl();
        wxMaDefaultConfig.setAppid(applicationProperties.getWechat().getAppId());
        wxMaDefaultConfig.setSecret(applicationProperties.getWechat().getAppSecret());
        wxMaDefaultConfig.setMsgDataFormat(Constant.JSON);
        return wxMaDefaultConfig;
    }
}
