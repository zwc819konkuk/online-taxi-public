package com.zwc.apidriver.service;

import com.zwc.apidriver.remote.ServiceDriverUserClient;
import com.zwc.apidriver.remote.ServiceVerificationCodeClient;
import com.zwc.internalcommon.constant.CommonStatusEnum;
import com.zwc.internalcommon.constant.DriverCarConstants;
import com.zwc.internalcommon.constant.IdentityConstant;
import com.zwc.internalcommon.constant.TokenConstants;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.VerificationCodeDTO;
import com.zwc.internalcommon.response.DriverUserExistsResponse;
import com.zwc.internalcommon.response.NumberCodeResponse;
import com.zwc.internalcommon.response.TokenResponse;
import com.zwc.internalcommon.utils.JwtUtils;
import com.zwc.internalcommon.utils.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

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
        ResponseResult<NumberCodeResponse> numberCodeResult = serviceVerificationCodeClient.numberCode(6);
        int numberCode = numberCodeResult.getData().getNumberCode();
        log.info("验证码:"+numberCode);

        //发送验证码，第三方：阿里短信，腾讯，华信等

        //存入redis
        //1key 2存入value
        String key = RedisPrefixUtils.generatorKeyByPhone(driverPhone, IdentityConstant.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

        return ResponseResult.success("");

    }

    /**
     * 校验验证码
     *
     * @param driverPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkCode(String driverPhone, String verificationCode) {
        //根据手机号去redis读取验证码
        //生成key
        String key = RedisPrefixUtils.generatorKeyByPhone(driverPhone,IdentityConstant.DRIVER_IDENTITY);

        //根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的value：" + codeRedis);

        //校验验证码
        if (StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }


        //颁发令牌 这里的身份标识应该用常量
        String accessToken= JwtUtils.generatorToken(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken= JwtUtils.generatorToken(driverPhone, IdentityConstant.DRIVER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);
        //将token存入到redis中
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstant.DRIVER_IDENTITY,TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstant.DRIVER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);
        //响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
