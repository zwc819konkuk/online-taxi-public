package com.zwc.serviceprice.service;

import com.zwc.internalcommon.constant.CommonStatusEnum;
import com.zwc.internalcommon.dto.PriceRule;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.ForecastPriceDTO;
import com.zwc.internalcommon.response.DirectionResponse;
import com.zwc.internalcommon.response.ForecastPriceResponse;
import com.zwc.internalcommon.utils.BigDecimalUtils;
import com.zwc.serviceprice.mapper.PriceRuleMapper;
import com.zwc.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ForecastService {
    @Autowired
    ServiceMapClient serviceMapClient;

    @Autowired
    PriceRuleMapper priceRuleMapper;


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
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("city_code","110000");
        queryMap.put("vehicle_type","1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);
        if (priceRules.size()==0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }

        PriceRule priceRule = priceRules.get(0);

        log.info("根据距离/时长/计价规则，计算价格");
        double price = getPrice(distance, duration, priceRule);


        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);

        return ResponseResult.success(forecastPriceResponse);
    }

    /**
     * 根据，距离时长计价规则，计算最终价格
     * @param distance
     * @param duration
     * @param priceRule
     * @return
     */
    private double getPrice(Integer distance,Integer duration,PriceRule priceRule){
        double price = 0.0;
        //起步价
        double startFare = priceRule.getStartFare();
        price = BigDecimalUtils.add(price,startFare);

        //里程费
        //总里程 km
        double distanceMile = BigDecimalUtils.divide(distance,1000);

        //起步里程
        double startMile = (double)priceRule.getStartMile();
        double distanceSubtract = BigDecimalUtils.substract(distanceMile, startMile);

        //最终收费的里程数 km
        double mile = distanceSubtract<0?0:distanceSubtract;

        //公里单价
        double unitPricePerMile = priceRule.getUnitPricePerMile();

        //里程价格
        double mileFare = BigDecimalUtils.multiply(mile,unitPricePerMile);

        price = BigDecimalUtils.add(price,mileFare);


        //时长费

        //分钟
        double timeMinute = BigDecimalUtils.divide(duration,60);
        //单价
        double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        //时长总费用
        double timeFare = BigDecimalUtils.multiply(timeMinute,unitPricePerMinute);

        price = BigDecimalUtils.add(price,timeFare);

        BigDecimal priceDecimal = BigDecimal.valueOf(price);
        priceDecimal = priceDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);

        return priceDecimal.doubleValue();
    }

//    public static void main(String[] args) {
//        PriceRule priceRule = new PriceRule();
//        priceRule.setUnitPricePerMile(1.8);
//        priceRule.setUnitPricePerMinute(0.5);
//        priceRule.setStartFare(10.0);
//        priceRule.setStartMile(3);
//        System.out.println(getPrice(6500,1800,priceRule));
//    }
}
