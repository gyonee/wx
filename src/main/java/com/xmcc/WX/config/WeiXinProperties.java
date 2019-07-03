package com.xmcc.WX.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WeiXinProperties {
    private String appid;
    private String secret;

    private String mchid;
    private String mchkey;
    private String keypath;
    private String notifyUrl;
}
