package com.xmcc.WX.service;

import com.xmcc.WX.common.ResultResponse;
import com.xmcc.WX.dto.OrderMasterDto;

public interface OrderMasterService {

    ResultResponse insertOrder(OrderMasterDto orderMasterDto);
}
