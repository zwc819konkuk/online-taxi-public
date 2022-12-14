package com.zwc.apipassenger.controller;

import com.zwc.apipassenger.service.OrderService;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 乘客下单
     * @return
     */
    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest){
        return orderService.add(orderRequest);
    }
}
