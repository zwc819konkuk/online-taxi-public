package com.zwc.servicemap.service;

import com.zwc.internalcommon.constant.AmapConfigConstants;
import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Value("${amap.key}")
    private String amapKey;

    public ResponseResult initDicDistrict(String keywords){

        //https://restapi.amap.com/v3/config/district
        // ?keywords=?
        // &subdistrict=2
        // &key=554407fe9befe3540ef0808fe34dffd3

        //拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.DISTRICT_URL);
        url.append("?");
        url.append("keywords="+keywords);
        url.append("&");
        url.append("subdistrict=3");
        url.append("&");
        url.append("key="+amapKey);


        //解析结果

        //插入数据库
        return ResponseResult.success();
    }
}
