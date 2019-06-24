package com.xmcc.WX.service;

import com.xmcc.WX.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    //批量插入
    void batchInsert(List<OrderDetail> list);
}
