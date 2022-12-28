package com.zwc.apipassenger.controller;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.OrderRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * 乘客下单
     * @return
     */
    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest){
        System.out.println(orderRequest);
        return null;
    }
}
