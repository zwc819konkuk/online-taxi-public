package com.zwc.servicemap.remote;

import com.zwc.internalcommon.constant.AmapConfigConstants;
import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapDicDistrictClient {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${amap.key}")
    private String amapKey;

    public String dicDistrict(String keywords){

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

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);

        //解析结果

        //插入数据库
        return forEntity.getBody();
    }
}
