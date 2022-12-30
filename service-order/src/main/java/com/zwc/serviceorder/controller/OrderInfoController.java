package com.zwc.serviceorder.controller;


import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.OrderRequest;
import com.zwc.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest){

        log.info("service-order"+orderRequest.getAddress());

        return null;
    }

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("/test")
    public String testMapper(){
        return orderInfoService.testMapper();
    }
}
