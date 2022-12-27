package com.zwc.servicedriveruser.controller;


import com.zwc.internalcommon.dto.Car;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zwc
 * @since 2022-12-19
 */
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {

        return carService.addCar(car);
    }

    @GetMapping("/car")
    public ResponseResult<Car> getCarById(Long carId){
        return carService.getCarById(carId);
    }
}
