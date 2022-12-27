package com.zwc.servicemap.service;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.response.TerminalResponse;
import com.zwc.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> add(String name,String desc){

        return terminalClient.add(name ,desc);
    }
}
