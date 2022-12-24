package com.zwc.apidriver.service;

import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    public ResponseResult checkAndSendVerificationCode(String driverphone){
        //查询 service-driver-user，该手机号是否存在

        //获取验证码

        //发送验证码，第三方

        //存入redis

        return ResponseResult.success("");

    }
}
