package com.zwc.servicemap.controller;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.TrackResponse;
import com.zwc.servicemap.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @PostMapping("/add")
    public ResponseResult<TrackResponse> add(String tid){

        return trackService.add(tid);
    }
}
