package com.xmcc.WX.service.impl;

import com.xmcc.WX.dao.impl.BatchDaoImpl;
import com.xmcc.WX.entity.OrderDetail;
import com.xmcc.WX.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderDetailServiceImpl extends BatchDaoImpl<OrderDetail> implements OrderDetailService {
    @Override
    @Transactional
    public void batchInsert(List<OrderDetail> list) {
        super.batchInsert(list);
    }
}
