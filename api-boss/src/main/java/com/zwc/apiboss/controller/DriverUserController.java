package com.zwc.apiboss.controller;

import com.zwc.apiboss.service.DriverUserService;
import com.zwc.internalcommon.dto.DriverUser;
import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverUserController {
    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/driver-user")
    public ResponseResult addDirverUser(@RequestBody DriverUser driverUser){
        return driverUserService.addDriverUser(driverUser);
    }
}
