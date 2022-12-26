package com.zwc.servicemap.remote;

import com.zwc.internalcommon.constant.AmapConfigConstants;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.TerminalResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TerminalClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public ResponseResult add (String name){

        //拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TERMINAL_ADD_URL);
        url.append("?");
        url.append("key="+amapKey);
        url.append("&");
        url.append("sid="+amapSid);
        url.append("&");
        url.append("name="+name);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);

        /**
         * {
         *     "data": {
         *         "name": "车辆2",
         *         "tid": 614677564,
         *         "sid": 856978
         *     },
         *     "errcode": 10000,
         *     "errdetail": null,
         *     "errmsg": "OK"
         * }
         */
        String body = stringResponseEntity.getBody();
        JSONObject object = JSONObject.fromObject(body);
        JSONObject data = object.getJSONObject("data");
        String tid = data.getString("tid");
        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);
        return ResponseResult.success(terminalResponse);
    }
}
