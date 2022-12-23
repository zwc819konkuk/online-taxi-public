package com.zwc.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zwc.internalcommon.constant.CommonStatusEnum;
import com.zwc.internalcommon.constant.DriverCarConstants;
import com.zwc.internalcommon.dto.DriverCarBindingRelationship;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zwc
 * @since 2022-12-23
 */
@Service
public class DriverCarBindingRelationshipService {
    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        //判断如果参数中的车辆和司机已经绑定过，则不允许再次绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        Integer count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (count.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getValue());
        }

        //司机被绑定了
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (count.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS.getCode(), CommonStatusEnum.DRIVER_BIND_EXISTS.getValue());
        }

        //车辆被绑定了
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (count.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS.getCode(), CommonStatusEnum.CAR_BIND_EXISTS.getValue());
        }

        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);

        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);

        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("");
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship){
        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> map = new HashMap<>();

        map.put("driver_id",driverCarBindingRelationship.getDriverId());
        map.put("car_id",driverCarBindingRelationship.getCarId());
        map.put("bind_state",DriverCarConstants.DRIVER_CAR_BIND);

        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(map);
        if (driverCarBindingRelationships.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getValue());
        }
        DriverCarBindingRelationship relationship = driverCarBindingRelationships.get(0);
        relationship.setBindState(DriverCarConstants.DRIVER_CAR_UNBIND);
        relationship.setUnBindingTime(now);

        driverCarBindingRelationshipMapper.updateById(relationship);
        return ResponseResult.success("");
    }

}
