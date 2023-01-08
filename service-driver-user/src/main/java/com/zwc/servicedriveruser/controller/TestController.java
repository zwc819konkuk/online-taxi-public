package com.zwc.servicedriveruser.controller;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.servicedriveruser.mapper.DriverUserMapper;
import com.zwc.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private DriverUserService driverUserService;

    @GetMapping
    public ResponseResult test(){
        return driverUserService.testGetDriveruser();
    }

    @Autowired
    private DriverUserMapper driverUserMapper;
    //测试xml
    @GetMapping("/test-xml01")
    public int testXml(String arg){
        int i = driverUserMapper.select1("1");
        return i;
    }
}
