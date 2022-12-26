package com.zwc.servicemap.service;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.PointRequest;
import com.zwc.servicemap.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    private PointClient pointClient;

    public ResponseResult upload(PointRequest pointRequest){
        return pointClient.upload(pointRequest);
    }
}
