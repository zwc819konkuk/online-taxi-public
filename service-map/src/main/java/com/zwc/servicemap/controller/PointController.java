package com.zwc.servicemap.controller;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.PointRequest;
import com.zwc.servicemap.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    private PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest){

        return pointService.upload(pointRequest);
    }
}
