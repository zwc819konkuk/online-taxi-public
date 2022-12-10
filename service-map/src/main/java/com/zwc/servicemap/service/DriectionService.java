package com.zwc.servicemap.service;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.DirectionResponse;
import com.zwc.servicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriectionService {
    @Autowired
    MapDirectionClient mapDirectionClient;

    /**
     * 根据起点和终点获取距离和时长，min and meter
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude){


        //调用第三方地图接口
        DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);


        return ResponseResult.success(direction);

    }
}
