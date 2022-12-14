package com.zwc.servicemap.service;

import com.zwc.internalcommon.constant.AmapConfigConstants;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.servicemap.remote.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Value("${amap.key}")
    private String amapKey;

    public ResponseResult initDicDistrict(String keywords){
        //请求地图
        String dicDistrict = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrict);
        //解析结果

        //插入数据库
        return ResponseResult.success();
    }
}
