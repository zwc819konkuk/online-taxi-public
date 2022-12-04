package com.zwc.serviceverificationcode.controller;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        System.out.println("size:" + size);
        //生成验证码
        int numberCode = (int) ((Math.random() * 9 + 1) * (Math.pow(10, size - 1)));
        System.out.println("generator src code:" + numberCode);

        //定义返回值
        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(numberCode);
        return ResponseResult.success(numberCodeResponse);
    }
}
