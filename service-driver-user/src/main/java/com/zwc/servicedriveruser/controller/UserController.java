package com.zwc.servicedriveruser.controller;

import com.zwc.internalcommon.constant.DriverCarConstants;
import com.zwc.internalcommon.dto.DriverUser;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.DriverUserExistsResponse;
import com.zwc.servicedriveruser.service.DriverUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private DriverUserService driverUserService;

    /**
     * 新增司机
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {
        log.info(JSONObject.fromObject(driverUser).toString());
        return driverUserService.addDriverUser(driverUser);
    }

    /**
     * 修改司机
     * @param
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser){
        return driverUserService.updateDriverUser(driverUser);
    }

    /**
     * 查询司机
     * @param driverPhone
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> getUser(@PathVariable("driverPhone") String driverPhone){
        ResponseResult<DriverUser> driverUserByPhone = driverUserService.getDriverUserByPhone(driverPhone);
        DriverUser byPhoneData = driverUserByPhone.getData();

        DriverUserExistsResponse response = new DriverUserExistsResponse();

        int isExist = DriverCarConstants.DRIVER_EXIST;
        if (byPhoneData == null){
            isExist = DriverCarConstants.DRIVER_NOT_EXIST;
            response.setIsExist(isExist);
            response.setDriverPhone(driverPhone);
        }else {
            response.setIsExist(isExist);
            response.setDriverPhone(byPhoneData.getDriverPhone());
        }

        return ResponseResult.success(response);
    }


}
