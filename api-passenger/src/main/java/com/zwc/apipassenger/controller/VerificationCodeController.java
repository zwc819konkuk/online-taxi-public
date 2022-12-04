package com.zwc.apipassenger.controller;


import com.zwc.apipassenger.remote.ServiceVerificationcodeClient;
import com.zwc.apipassenger.request.VerificationCodeDTO;
import com.zwc.apipassenger.service.VerificationCodeService;
import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {


    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("接收到得手机号参数" + passengerPhone);

        return verificationCodeService.generatorCode(passengerPhone);

    }
}
