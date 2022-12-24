package com.zwc.apiboss.service;

import com.zwc.apiboss.remote.ServiceDriverUserClient;
import com.zwc.internalcommon.dto.Car;
import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addCar(Car car) {
        return serviceDriverUserClient.addCar(car);
    }
}
