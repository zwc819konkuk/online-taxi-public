package com.zwc.servicemap.remote;


import com.zwc.internalcommon.constant.AmapConfigConstants;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        //组装请求的url参数
        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append(AmapConfigConstants.DIRECTION_URL);
        urlBuild.append("?");
        urlBuild.append("origin=" + depLongitude + "," + depLatitude);
        urlBuild.append("&");
        urlBuild.append("destination=" + destLongitude + "," + destLatitude);
        urlBuild.append("&");
        urlBuild.append("extensions=base");
        urlBuild.append("&");
        urlBuild.append("output=json");
        urlBuild.append("&");
        urlBuild.append("key=" + amapKey);
        log.info(urlBuild.toString());

        //调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuild.toString(), String.class);
        String directionString = directionEntity.getBody();
        log.info("高德地图，路径规划，返回信息：" + directionString);


        //解析接口
        DirectionResponse directionResponse =  parseDirectionEntity(directionString);

        return directionResponse;
    }

    private DirectionResponse parseDirectionEntity(String directionBody) {
        DirectionResponse directionResponse = null;

        try {

            //最外层
            JSONObject result = JSONObject.fromObject(directionBody);
            if (result.has(AmapConfigConstants.STATUS)) {
                int status = result.getInt(AmapConfigConstants.STATUS);
                if (status == 1) {
                    if (result.has(AmapConfigConstants.ROUTE)) {
                        JSONObject routeObject = result.getJSONObject(AmapConfigConstants.ROUTE);
                        JSONArray pathsArray = routeObject.getJSONArray(AmapConfigConstants.PATHS);
                        JSONObject pathArray = pathsArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();


                        if (pathArray.has(AmapConfigConstants.DISTANCE)) {
                            int distance = pathArray.getInt(AmapConfigConstants.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if (pathArray.has(AmapConfigConstants.DURATION)) {
                            int duration = pathArray.getInt(AmapConfigConstants.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }

        } catch (Exception e) {

        }
        return directionResponse;
    }
}
