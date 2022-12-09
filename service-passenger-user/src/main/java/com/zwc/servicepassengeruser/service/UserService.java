package com.zwc.servicepassengeruser.service;

import com.zwc.internalcommon.constant.CommonStatusEnum;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.dto.PassengerUser;
import com.zwc.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;
    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("userservcie被调用，手机号：" + passengerPhone);
        //根据手机号查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers.size()==0?"无记录":passengerUsers.get(0).getPassengerName());


        //判断用户信息是否存在
        if (passengerUsers.size()==0){
            //如果不存在插入用户
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setState((byte) 0);

            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);

            passengerUserMapper.insert(passengerUser);

        }
        return ResponseResult.success();

    }

    /**
     * 根据手机号查询用户信息
     * @param passengerPhone
     * @return
     */
    public ResponseResult getUserByPhone(String passengerPhone){
        //根据手机号查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);

        if (passengerUsers.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXIST.getCode(),CommonStatusEnum.USER_NOT_EXIST.getValue());
        }else {
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }
}
