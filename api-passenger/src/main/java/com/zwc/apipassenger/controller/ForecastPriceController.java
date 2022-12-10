package com.zwc.apipassenger.controller;

import com.zwc.apipassenger.service.ForecastPriceService;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ForecastPriceController {

    @Autowired
    private ForecastPriceService forecastPriceService;


    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {

        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();





        return forecastPriceService.forecastPrice(depLongitude,depLatitude,destLongitude,destLatitude);
    }
}
