package com.zwc.serviceprice.service;

import com.zwc.internalcommon.constant.CommonStatusEnum;
import com.zwc.internalcommon.dto.PriceRule;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.ForecastPriceDTO;
import com.zwc.internalcommon.response.DirectionResponse;
import com.zwc.internalcommon.response.ForecastPriceResponse;
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
    private  double getPrice(Integer distance,Integer duration,PriceRule priceRule){
        BigDecimal price = new BigDecimal(0);
        //起步价
        Double startFare = priceRule.getStartFare();
        BigDecimal startFareBigDecimal = new BigDecimal(startFare);
        price = price.add(startFareBigDecimal);

        //里程费
        //总里程 m
        BigDecimal distanceBigDecimal = new BigDecimal(distance);
        //总里程 km
        BigDecimal distanceMileBigDecimal = distanceBigDecimal.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
        //起步里程
        Integer startMile = priceRule.getStartMile();
        BigDecimal startMileBigDecimal = new BigDecimal(startMile);
        double distanceSubtract = distanceMileBigDecimal.subtract(startMileBigDecimal).doubleValue();
        //最终收费的里程数 km
        Double mile = distanceSubtract<0?0:distanceSubtract;
        BigDecimal mileDecimal = new BigDecimal(mile);
        //公里单价
        BigDecimal unitPricePerMileDecimal = new BigDecimal(priceRule.getUnitPricePerMile());
        //里程价格
        BigDecimal mileFare = mileDecimal.multiply(unitPricePerMileDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
        price = price.add(mileFare);


        //时长费
        BigDecimal durationDecimal = new BigDecimal(duration);
        //分钟
        BigDecimal timeDecimal = durationDecimal.divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);

        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        BigDecimal unitPricePerMinuteDecimal = new BigDecimal(unitPricePerMinute);
        //时长总费用
        BigDecimal timeFare = timeDecimal.multiply(unitPricePerMinuteDecimal);
        price = price.add(timeFare).setScale(2,BigDecimal.ROUND_HALF_UP);

        return price.doubleValue();
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
