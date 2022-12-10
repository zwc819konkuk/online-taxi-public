package com.zwc.servicemap.service;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;

@Service
public class DriectionService {

    /**
     * 根据起点和终点获取距离和时长，min and meter
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude){

        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(123);
        directionResponse.setDuration(22);

        return ResponseResult.success(directionResponse);

    }
}
