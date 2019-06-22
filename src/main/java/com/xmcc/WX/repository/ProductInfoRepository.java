package com.xmcc.WX.repository;

import com.xmcc.WX.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    //根据类目编号(ProductStatus)和状态查询商品(CategoryType)
    List<ProductInfo> findByProductStatusAndCategoryTypeIn(Integer status,List<Integer> typelist);
}
