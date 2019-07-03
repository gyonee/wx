package com.xmcc.WX.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WechatConfig {
    @Autowired
    private WeiXinProperties weiXinProperties;
    @Bean//文档中需要用到这个对象
    public WxMpService wxMpService(){
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        //设置微信配置的存储
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }
    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){

        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        //设置appid  这个在项目中肯定是通过配置来实现
        wxMpInMemoryConfigStorage.setAppId(weiXinProperties.getAppid());//wxcec0b9e65c084712
        //设置密码
        wxMpInMemoryConfigStorage.setSecret(weiXinProperties.getSecret());//05a7e861c1985ced86af77fb8f7163bc
        return wxMpInMemoryConfigStorage;
    }

}
