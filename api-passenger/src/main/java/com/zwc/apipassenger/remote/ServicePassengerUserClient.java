package com.zwc.apipassenger.remote;

import com.zwc.internalcommon.dto.PassengerUser;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @RequestMapping(method = RequestMethod.POST,value = "/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @RequestMapping(method = RequestMethod.GET,value = "/user/{phone}")
    public ResponseResult<PassengerUser> getUserByPhone(@PathVariable("phone" ) String phone);
}
