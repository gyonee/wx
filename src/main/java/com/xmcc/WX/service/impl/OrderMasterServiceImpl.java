package com.xmcc.WX.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmcc.WX.Util.BigDecimalUtil;
import com.xmcc.WX.Util.IDUtils;
import com.xmcc.WX.common.*;
import com.xmcc.WX.dto.OrderDetailDto;
import com.xmcc.WX.dto.OrderMasterDto;
import com.xmcc.WX.entity.OrderDetail;
import com.xmcc.WX.entity.OrderMaster;
import com.xmcc.WX.entity.ProductInfo;
import com.xmcc.WX.exception.CustomException;
import com.xmcc.WX.repository.OrderMasterRepository;
import com.xmcc.WX.service.OrderDetailService;
import com.xmcc.WX.service.OrderMasterService;
import com.xmcc.WX.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    public ResultResponse insertOrder(OrderMasterDto orderMasterDto) {
        //取出订单项
        List<OrderDetailDto> items = orderMasterDto.getItems();
        //创建detail集合来存储OrderDetail
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        //初始化订单总金额
        BigDecimal totalPrice = new BigDecimal("0");
        //遍历订单项,获取商品详情
        for (OrderDetailDto orderDetailDto:items){
            //查询订单
            ResultResponse<ProductInfo> resultResponse = productInfoService.queryById(orderDetailDto.getProductId());
            //判断ResultResponse的code
            if (resultResponse.getCode() == ResultEnums.FAIL.getCode()){
                throw  new CustomException(resultResponse.getMsg());
            }
            //获取商品
            ProductInfo productInfo = resultResponse.getData();
            //比较库存
            if (productInfo.getProductStock() < orderDetailDto.getProductQuantity()){
                throw new CustomException(ProductEnums.PRODUCT_NOT_ENOUGH.getMsg());
            }
            //创建订单项
            OrderDetail orderDetail = OrderDetail.builder().detailId(IDUtils.createIdbyUUID()).productIcon(productInfo.getProductIcon())
                    .productId(orderDetailDto.getProductId()).productName(productInfo.getProductName())
                    .productPrice(productInfo.getProductPrice()).productQuantity(orderDetailDto.getProductQuantity())
                    .build();
            //添加到订单项
            orderDetailList.add(orderDetail) ;
            //减少库存数量
            productInfo.setProductStock(productInfo.getProductStock()-orderDetailDto.getProductQuantity());
            //更新数据库库存数据
            productInfoService.updateProduct(productInfo);
            //计算价格
            totalPrice = BigDecimalUtil.add(totalPrice,
                    BigDecimalUtil.multi(productInfo.getProductPrice(),orderDetailDto.getProductQuantity()));
        }
        //生成订单id
        String order_id = IDUtils.createIdbyUUID();
        //构建订单信息
        OrderMaster orderMaster = OrderMaster.builder()
                .orderId(order_id).buyerAddress(orderMasterDto.getAddress())
                .buyerName(orderMasterDto.getName()).buyerPhone(orderMasterDto.getPhone())
                .buyerOpenid(orderMasterDto.getOpenid()).orderAmount(totalPrice)
                .orderStatus(OrderEnums.NEW.getCode()).payStatus(PayEnums.WAIT.getCode()).build();
        //将生成的订单ID设置到订单项里面
        List<OrderDetail> orderDetails = orderDetailList.stream().map(orderDetail -> {
            orderDetail.setOrderId(order_id);
            return orderDetail;
        }).collect(Collectors.toList());
        //批量插入订单项
        orderDetailService.batchInsert(orderDetails);
        //插入订单
        orderMasterRepository.save(orderMaster);
        HashMap<String,String> map = Maps.newHashMap();
        map.put("orderId",order_id);
        return ResultResponse.success(map);
    }
}
