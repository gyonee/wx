package com.xmcc.WX.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayConfig {
    public BestPayService bestPayService(){
        //微信公众账号支付配置
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId("wxcec0b9e65c084712");
        wxPayH5Config.setAppSecret("05a7e861c1985ced86af77fb8f7163bc");
        wxPayH5Config.setMchId("1529533061");
        wxPayH5Config.setMchKey("qwertyuiopasdfghjklzxcvbnm123456");
        wxPayH5Config.setNotifyUrl("http://xmcc.natapp1.cc/sell/pay/notify");

        //支付类, 所有方法都在这个类里
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);

        return bestPayService;
    }

}
