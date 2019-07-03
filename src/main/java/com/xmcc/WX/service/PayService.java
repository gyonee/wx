package com.xmcc.WX.service;

import com.xmcc.WX.entity.OrderMaster;

public interface PayService {
    //创建支付
    void create(OrderMaster orderMaster);

    OrderMaster findOrderById(String orderId);
}
