package com.zwc.apidriver.remote;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.PointRequest;
import com.zwc.internalcommon.response.TerminalResponse;
import com.zwc.internalcommon.response.TrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value = "/point/upload")
    public ResponseResult<TerminalResponse> upload(@RequestBody PointRequest pointRequest);


}
