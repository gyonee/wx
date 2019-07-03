package com.xmcc.WX.controller;

import com.xmcc.WX.entity.OrderMaster;
import com.xmcc.WX.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("pay")
@Slf4j
public class PayController {
    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public void create(@RequestParam("orderId") String orderId,@RequestParam("returnUrl") String returnUrl){
        //根据ID查询订单
        OrderMaster orderById = payService.findOrderById(orderId);
        //根据订单创建支付
        payService.create(orderById);
    }

    @RequestMapping("test")
    public void test(){
        log.info("异步回调成功");
    }
}
