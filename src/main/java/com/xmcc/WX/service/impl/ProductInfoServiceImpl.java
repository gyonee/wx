package com.xmcc.WX.service.impl;

import com.xmcc.WX.common.ResultEnums;
import com.xmcc.WX.common.ResultResponse;
import com.xmcc.WX.dto.ProductCategoryDto;
import com.xmcc.WX.dto.ProductInfoDto;
import com.xmcc.WX.entity.ProductCategory;
import com.xmcc.WX.entity.ProductInfo;
import com.xmcc.WX.repository.ProductCategoryRepository;
import com.xmcc.WX.repository.ProductInfoRepository;
import com.xmcc.WX.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ResultResponse queryList() {
        //查询所有分类
        List<ProductCategory> all = productCategoryRepository.findAll();
        //把productCategory 复制到dto里面
        List<ProductCategoryDto> productCategoryDtoList = all.stream().map(productCategory -> ProductCategoryDto.build(productCategory))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(all)){
            return ResultResponse.fail();
        }
        //获取类目编号集合 (从productCategoryDto里面取出Type)
        List<Integer> typelist
                = productCategoryDtoList.stream().map(productCategoryDto -> productCategoryDto.getCategoryType()).collect(Collectors.toList());
        //根据typelist查询商品列表
        List<ProductInfo> productInfoList
                = productInfoRepository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), typelist);
        //对目标集合(productCategoryDtoList)进行遍历 取出每个商品的类目编号 设置到对应的目录
            //1.将 product 设置到 foods 中
            //2.过滤：不同type 进行不同的封装
            //3.将 productInfo 转成Dto
        List<ProductCategoryDto> productCategoryDtos = productCategoryDtoList.parallelStream().map(productCategoryDto -> {//遍历productCategoryDto(已经有type了，差info)
            productCategoryDto.setProductInfoDtoList(productInfoList.stream()  //设置info(food)
                    .filter(productInfo -> productInfo.getCategoryType() == productCategoryDto.getCategoryType()) //遍历，第一个dto的type值对应的所有info
                    .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList())); //遍历，封装过滤出的info

            return productCategoryDto;

        }).collect(Collectors.toList());
        return ResultResponse.success(productCategoryDtos);
    }
}
