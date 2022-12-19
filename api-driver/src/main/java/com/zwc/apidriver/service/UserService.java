package com.zwc.apidriver.service;

import com.zwc.apidriver.remote.ServiceDriverUserClient;
import com.zwc.internalcommon.dto.DriverUser;
import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult updateUser(DriverUser driverUser){
        return serviceDriverUserClient.updateUser(driverUser);
    }
}
