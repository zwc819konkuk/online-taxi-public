package com.zwc.servicedriveruser.controller;


import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.dto.DriverUserWorkStatus;
import com.zwc.servicedriveruser.service.DriverUserWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwc
 * @since 2022-12-25
 */
@RestController
public class DriverUserWorkStatusController {
    @Autowired
    DriverUserWorkStatusService driverUserWorkStatusService;

    @RequestMapping("/driver-user-work-status")
    public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus){
        return driverUserWorkStatusService.changeWorkStatus(driverUserWorkStatus.getDriverId(), driverUserWorkStatus.getWorkStatus());
    }

}
