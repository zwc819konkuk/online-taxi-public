package com.zwc.apipassenger.service;


import com.zwc.apipassenger.remote.ServicePassengerUserClient;
import com.zwc.internalcommon.dto.PassengerUser;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.dto.TokenResult;
import com.zwc.internalcommon.request.VerificationCodeDTO;
import com.zwc.internalcommon.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken){
        log.info("accessToken:"+accessToken);
        //解析token，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("phone:"+phone);


        //根据手机号查询用户信息
        ResponseResult<PassengerUser> userByPhone = servicePassengerUserClient.getUserByPhone(phone);

        return ResponseResult.success(userByPhone.getData());
    }
}
