package com.zwc.servicemap.controller;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.TerminalResponse;
import com.zwc.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @PostMapping("/add")
    public ResponseResult<TerminalResponse> add(String name){
        return terminalService.add(name);
    }


}
