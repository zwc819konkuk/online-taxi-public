package com.zwc.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zwc.internalcommon.constant.CommonStatusEnum;
import com.zwc.internalcommon.constant.OrderConstants;
import com.zwc.internalcommon.dto.OrderInfo;
import com.zwc.internalcommon.dto.PriceRule;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.OrderRequest;
import com.zwc.internalcommon.utils.RedisPrefixUtils;
import com.zwc.serviceorder.mapper.OrderInfoMapper;
import com.zwc.serviceorder.remote.ServicePriceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zwc
 * @since 2022-12-30
 */
@Service
public class OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String testMapper() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("110000");
        orderInfoMapper.insert(orderInfo);
        return " ";
    }

    public ResponseResult add(OrderRequest orderRequest) {
        //1. 判断计价规则是否是最新
        ResponseResult<Boolean> result = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        if (!(result.getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(), CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }

        //2. 判断下单的设备是否是黑名单设备
        if (isBlackDevice(orderRequest)){
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BALCK.getCode(), CommonStatusEnum.DEVICE_IS_BALCK.getValue());
        }

        //3. 判断下单的城市和计价规则是否正常
        if (!isPriceRuleExists(orderRequest)){
            return ResponseResult.fail(CommonStatusEnum.CITY_NO_SERVICE.getCode(),CommonStatusEnum.CITY_NO_SERVICE.getValue());
        }

        //4. 判断有正在运行的订单不允许下单
        if (isOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(), CommonStatusEnum.ORDER_GOING_ON.getValue());
        }

        //4. 创建订单
        OrderInfo orderInfo = new OrderInfo();

        BeanUtils.copyProperties(orderRequest,orderInfo);

        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderInfoMapper.insert(orderInfo);

        return ResponseResult.success();
    }

    /**
     * 判断有正在运行的订单不允许下单
     *
     * @param passengerId
     * @return
     */
    private int isOrderGoingOn(Long passengerId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", passengerId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstants.ORDER_START)
                .or().eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPATURE)
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.PASSENGER_GETOFF)
                .or().eq("order_status", OrderConstants.TO_START_PAY)
        );

        Integer validOrderNumber = orderInfoMapper.selectCount(queryWrapper);
        return validOrderNumber;
    }

    private boolean isPriceRuleExists(OrderRequest orderRequest){
        String fareType = orderRequest.getFareType();
        int index = fareType.indexOf("$");
        String cityCode = fareType.substring(0, index);
        String vehicleType = fareType.substring(index + 1);

        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);

        ResponseResult<Boolean> booleanResponseResult = servicePriceClient.ifPriceExists(priceRule);
        return booleanResponseResult.getData();
    }

    /**
     * 判断是否为黑名单的设备
     * @param orderRequest
     * @return
     */
    private boolean isBlackDevice(OrderRequest orderRequest){
        String deviceCode = orderRequest.getDeviceCode();
        //生成key
        String deviceCodeKey = RedisPrefixUtils.blackDeviceCodePrefix + deviceCode;
        //设置key[设置时间的时候要保持原子性]，看原来有没有key
        Boolean hasKey = stringRedisTemplate.hasKey(deviceCodeKey);
        if (hasKey) {
            String s = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            int i = Integer.parseInt(s);
            if (i >= 2) {
                //当前设备是一个黑名单设备
                return true;
            } else {
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        } else {
            stringRedisTemplate.opsForValue().setIfAbsent(deviceCodeKey, "1", 1, TimeUnit.HOURS);
        }
        return false;
    }
}
