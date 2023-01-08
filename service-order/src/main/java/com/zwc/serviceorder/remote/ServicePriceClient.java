package com.zwc.serviceorder.remote;

import com.zwc.internalcommon.dto.PriceRule;
import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-price")
public interface ServicePriceClient {

    @GetMapping("/price-rule/is-new")
    public ResponseResult<Boolean> isNew(@RequestParam String fareType,@RequestParam Integer fareVersion);
    @RequestMapping(method = RequestMethod.POST,value = "/price-rule/if-exists")
    public ResponseResult<Boolean> ifPriceExists(@RequestBody PriceRule priceRule);
}
