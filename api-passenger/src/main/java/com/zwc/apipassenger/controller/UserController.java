package com.zwc.apipassenger.controller;

import com.zwc.apipassenger.service.UserService;
import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request){

        //从http请求中获取accessToken
        String accessToken = request.getHeader("Authorization");


        return userService.getUserByAccessToken(accessToken);
    }
}
