package com.zwc.apipassenger.service;


import com.zwc.internalcommon.dto.PassengerUser;
import com.zwc.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    public ResponseResult getUserByAccessToken(String accessToken){
        log.info("accessToken:"+accessToken);
        //解析token，拿到手机号

        //根据手机号查询用户信息

        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("张三");
        passengerUser.setProfilePhoto("头像");
        return ResponseResult.success(passengerUser);
    }
}
