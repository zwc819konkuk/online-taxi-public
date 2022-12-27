package com.zwc.apidriver.service;

import com.zwc.apidriver.remote.ServiceDriverUserClient;
import com.zwc.apidriver.remote.ServiceMapClient;
import com.zwc.internalcommon.dto.Car;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.ApiDriverPointRequest;
import com.zwc.internalcommon.request.PointRequest;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest){
        //获取carid
        Long carId = apiDriverPointRequest.getCarId();

        //通过carid获取tid和trid，调用service-driver-user
        ResponseResult<Car> carById = serviceDriverUserClient.getCarById(carId);
        Car car = carById.getData();
        String tid = car.getTid();
        String trid = car.getTrid();

        //调用地图去上传
        PointRequest pointRequest = new PointRequest();
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        pointRequest.setPoints(apiDriverPointRequest.getPoints());
        return serviceMapClient.upload(pointRequest);

    }
}
