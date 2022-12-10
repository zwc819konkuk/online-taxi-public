package com.zwc.serviceprice.service;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.ForecastPriceDTO;
import com.zwc.internalcommon.response.DirectionResponse;
import com.zwc.internalcommon.response.ForecastPriceResponse;
import com.zwc.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastService {
    @Autowired
    ServiceMapClient serviceMapClient;


    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        log.info("出发地经度"+depLongitude);
        log.info("出发地纬度"+depLatitude);
        log.info("目的地经度"+destLongitude);
        log.info("目的地纬度"+destLatitude);

        log.info("调用地图服务，查询距离和时长");
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        ResponseResult<DirectionResponse> direction = serviceMapClient.direction(forecastPriceDTO);
        Integer distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();
        System.out.println("距离"+distance);
        System.out.println("时长"+duration);

        log.info("读取计价规则");

        log.info("根据距离/时长/计价规则，计算价格");

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.34);

        return ResponseResult.success(forecastPriceResponse);
    }

}
