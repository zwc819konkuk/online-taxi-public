package com.zwc.serviceorder.controller;

import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
