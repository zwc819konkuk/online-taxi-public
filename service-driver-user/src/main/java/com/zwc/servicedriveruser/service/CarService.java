package com.zwc.servicedriveruser.service;

import com.zwc.internalcommon.dto.Car;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.servicedriveruser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarService {
    @Autowired
    private CarMapper carMapper;


    public ResponseResult addCar(Car car){
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
        carMapper.insert(car);
        return ResponseResult.success("");
    }
}
