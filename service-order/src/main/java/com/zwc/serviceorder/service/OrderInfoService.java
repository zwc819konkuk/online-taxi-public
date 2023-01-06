package com.zwc.serviceorder.service;

import com.zwc.internalcommon.constant.CommonStatusEnum;
import com.zwc.internalcommon.constant.OrderConstants;
import com.zwc.internalcommon.dto.OrderInfo;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.OrderRequest;
import com.zwc.serviceorder.mapper.OrderInfoMapper;
import com.zwc.serviceorder.remote.ServicePriceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwc
 * @since 2022-12-30
 */
@Service
public class OrderInfoService  {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    public String testMapper(){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("110000");
        orderInfoMapper.insert(orderInfo);
        return " ";
    }

    public ResponseResult add(OrderRequest orderRequest){
        //判断计价规则是否是最新
        ResponseResult<Boolean> result = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        if (!(result.getData())){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(),CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }

        //创建订单
        OrderInfo orderInfo = new OrderInfo();

        BeanUtils.copyProperties(orderRequest,orderInfo);

        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderInfoMapper.insert(orderInfo);

        return ResponseResult.success();
    }
}
