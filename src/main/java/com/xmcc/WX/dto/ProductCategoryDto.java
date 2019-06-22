package com.xmcc.WX.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xmcc.WX.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto implements Serializable {

    @JsonProperty("name")//封装成json返回name
    private String categoryName;

    @JsonProperty("type")//封装成json返回type
    private Integer categoryType;

    @JsonProperty("foods")//封装成json返回foods
    private List<ProductInfoDto> productInfoDtoList;

    //entity转换成dto
    public static ProductCategoryDto build(ProductCategory productCategory){
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        BeanUtils.copyProperties(productCategory,productCategoryDto);
        return productCategoryDto;
    }
}
