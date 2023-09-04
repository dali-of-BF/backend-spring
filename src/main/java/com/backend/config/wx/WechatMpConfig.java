package com.backend.config.wx;

import com.backend.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author FPH
 * @since 2023年9月4日21:13:41
 * 将appId与密钥设置到微信SDK中
 */
@Component
@RequiredArgsConstructor
public class WechatMpConfig {
    private final ApplicationProperties applicationProperties;
    @Bean
    public WxMpService wxMpService(){
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(this.wxMpConfigStorage());
        return wxMpService;
    }

    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAppId(applicationProperties.getWechat().getAppId());
        wxMpDefaultConfig.setSecret(applicationProperties.getWechat().getAppSecret());
        return wxMpDefaultConfig;
    }
}
