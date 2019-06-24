package com.xmcc.WX.service;

import com.xmcc.WX.common.ResultResponse;
import com.xmcc.WX.entity.ProductInfo;

public interface ProductInfoService {
    ResultResponse queryList();

    //根据id查询商品信息
    ResultResponse<ProductInfo> queryById(String productId);

    //修改库存
    void updateProduct(ProductInfo productInfo);
}
