package com.zwc.serviceorder.controller;


import com.zwc.internalcommon.constant.HeaderParamConstants;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.OrderRequest;
import com.zwc.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwc
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 创建订单
     * @param orderRequest
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest){
        //通过header获取devicecode
//        String deviceCode = httpServletRequest.getHeader(HeaderParamConstants.DEVICE_CODE);
//        orderRequest.setDeviceCode(deviceCode);
        return orderInfoService.add(orderRequest);
    }


    @GetMapping("/test")
    public String testMapper(){
        return orderInfoService.testMapper();
    }
}
