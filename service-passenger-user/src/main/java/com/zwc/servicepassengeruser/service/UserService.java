package com.zwc.servicepassengeruser.service;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.VerificationCodeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("userservcie被调用，手机号：" + passengerPhone);
        //根据手机号查询用户信息
        //判断用户信息是否存在
        //如果不存在插入用户
        return ResponseResult.success();
    }
}
