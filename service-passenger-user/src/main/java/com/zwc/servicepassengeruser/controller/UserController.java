package com.zwc.servicepassengeruser.controller;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.VerificationCodeDTO;
import com.zwc.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("手机号"+passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String passengerPhone){

        return userService.getUserByPhone(passengerPhone);

    }
}
