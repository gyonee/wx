package com.xmcc.WX.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.xmcc.WX.Util.JsonUtil;
import com.xmcc.WX.common.Constant;
import com.xmcc.WX.common.OrderEnums;
import com.xmcc.WX.entity.OrderMaster;
import com.xmcc.WX.exception.CustomException;
import com.xmcc.WX.repository.OrderMasterRepository;
import com.xmcc.WX.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private BestPayService bestPayService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    public void create(OrderMaster orderMaster) {
        PayRequest payRequest = new PayRequest();
        //微信用户OPenid
        payRequest.setOpenid(orderMaster.getBuyerOpenid());
        //订单金额
        payRequest.setOrderAmount(orderMaster.getOrderAmount().doubleValue());
        //订单ID
        payRequest.setOrderId(orderMaster.getOrderId());
        //订单名字
        payRequest.setOrderName(Constant.ORDER_NAME);
        //支付类型
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("微信支付的请求:{}", JsonUtil.object2string(payRequest));
        PayResponse response = bestPayService.pay(payRequest);
        log.info("微信支付的返回结果为:{}", JsonUtil.object2string(response));

    }

    @Override
    public OrderMaster findOrderById(String orderId) {
        Optional<OrderMaster> byId = orderMasterRepository.findById(orderId);
        if(!byId.isPresent()){
            //订单不存在就抛异常
            throw new CustomException(OrderEnums.ORDER_NOT_EXITS.getMsg());
        }
        return byId.get();

    }
}
