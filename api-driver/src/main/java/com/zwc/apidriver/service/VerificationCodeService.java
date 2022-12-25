package com.zwc.apidriver.service;

import com.zwc.apidriver.remote.ServiceDriverUserClient;
import com.zwc.internalcommon.constant.CommonStatusEnum;
import com.zwc.internalcommon.constant.DriverCarConstants;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.DriverUserExistsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult checkAndSendVerificationCode(String driverPhone){
        //查询 service-driver-user，该手机号是否存在
        ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult = serviceDriverUserClient.checkDriver(driverPhone);
        DriverUserExistsResponse data = driverUserExistsResponseResponseResult.getData();
        int isExist = data.getIsExist();
        if (isExist== DriverCarConstants.DRIVER_NOT_EXIST){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_NOT_EXISTS.getValue());
        }
        log.info(driverPhone+"的司机存在");
        //获取验证码

        //发送验证码，第三方

        //存入redis

        return ResponseResult.success("");

    }
}
