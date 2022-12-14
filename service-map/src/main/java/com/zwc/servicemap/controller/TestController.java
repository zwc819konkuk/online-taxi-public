package com.zwc.servicemap.controller;

import com.zwc.internalcommon.dto.DicDistrict;
import com.zwc.servicemap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class TestController {

    @RequestMapping
    public String test(){
        return "map";
    }

    @Autowired
    private DicDistrictMapper dicDistrictMapper;
    @GetMapping("/testmap")
    public String testMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("address_code","110000");
        List<DicDistrict> dicDistricts = dicDistrictMapper.selectByMap(map);
        System.out.println(dicDistricts);
        return "null";
    }
}
