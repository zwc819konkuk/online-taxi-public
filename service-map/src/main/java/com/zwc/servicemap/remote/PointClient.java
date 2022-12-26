package com.zwc.servicemap.remote;

import com.zwc.internalcommon.constant.AmapConfigConstants;
import com.zwc.internalcommon.dto.PointDTO;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PointClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult upload(PointRequest pointRequest){

        //拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.POINT_UPLOAD);
        url.append("?");
        url.append("key="+amapKey);
        url.append("&");
        url.append("sid="+amapSid);
        url.append("&");
        url.append("tid="+pointRequest.getTid());
        url.append("&");
        url.append("trid="+pointRequest.getTrid());
        url.append("&");
        url.append("points=");
        PointDTO[] points = pointRequest.getPoints();
        url.append("%5B");
        for (PointDTO point : points) {
            url.append("%7B");
            String locatetime = point.getLocatetime();
            String location = point.getLocation();

            url.append("%22location%22");
            url.append("%3A");
            url.append("%22"+location+"%22");
            url.append("%2C");
            url.append("%22locatetime%22");
            url.append("%3A");
            url.append(locatetime);

            url.append("%7D");
        }
        url.append("%5D");

        System.out.println(url.toString());
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        System.out.println(stringResponseEntity);
        return ResponseResult.success();
    }
}
