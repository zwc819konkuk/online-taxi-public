package com.zwc.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zwc.internalcommon.dto.PriceRule;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwc
 * @since 2023-01-05
 */
@Service
public class PriceRuleService {

    @Autowired
    PriceRuleMapper priceRuleMapper;
    public ResponseResult add(PriceRule priceRule){

        //拼接fareType
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode+vehicleType;
        priceRule.setFareType(fareType);

        //添加版本号
        // 问题1：增加了版本号，前面两个字段无法唯一确定一条记录
        // 问题2：找最大的版本号需要排序
        /*
        Map<String,Object> map = new HashMap<>();
        map.put("city_code",cityCode);
        map.put("vehicle_type",vehicleType);
        */
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer fareVersion = 0;
        if (priceRules.size()>0){
            fareVersion = priceRules.get(0).getFareVersion();
        }
        priceRule.setFareVersion(++fareVersion);

        priceRuleMapper.insert(priceRule);

        return ResponseResult.success();
    }
}
