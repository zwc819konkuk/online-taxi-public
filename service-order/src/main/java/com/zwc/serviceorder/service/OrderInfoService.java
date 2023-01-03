package com.zwc.serviceorder.service;

import com.zwc.internalcommon.dto.OrderInfo;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.OrderRequest;
import com.zwc.serviceorder.mapper.OrderInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String testMapper(){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("110000");
        orderInfoMapper.insert(orderInfo);
        return " ";
    }

    public ResponseResult add(OrderRequest orderRequest){
        OrderInfo orderInfo = new OrderInfo();

        BeanUtils.copyProperties(orderRequest,orderInfo);

        orderInfoMapper.insert(orderInfo);

        return ResponseResult.success();
    }
}
