package com.zwc.apidriver.remote;

import com.zwc.internalcommon.dto.Car;
import com.zwc.internalcommon.dto.DriverUser;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.DriverUserExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @RequestMapping(method = RequestMethod.PUT,value = "/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);


    @RequestMapping(method = RequestMethod.GET,value = "/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> checkDriver(@PathVariable("driverPhone") String driverPhone);

    @RequestMapping(method = RequestMethod.GET,value = "/car")
    public ResponseResult<Car> getCarById(@RequestParam Long carId);
}
