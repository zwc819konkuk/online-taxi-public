package com.zwc.serviceprice.controller;


import com.zwc.internalcommon.dto.PriceRule;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.serviceprice.mapper.PriceRuleMapper;
import com.zwc.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwc
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Autowired
    PriceRuleService priceRuleService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule){
        return priceRuleService.add(priceRule);
    }

}
