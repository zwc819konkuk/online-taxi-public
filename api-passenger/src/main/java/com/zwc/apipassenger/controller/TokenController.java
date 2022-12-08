package com.zwc.apipassenger.controller;

import com.zwc.apipassenger.service.TokenService;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;
    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse){

        ResponseResult responseResult = tokenService.refreshToken(tokenResponse.getRefreshToken());

        return responseResult;
    }
}
