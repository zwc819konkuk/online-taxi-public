package com.zwc.servicemap.service;

import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    public ResponseResult add(String name){

        return terminalClient.add(name );
    }
}
