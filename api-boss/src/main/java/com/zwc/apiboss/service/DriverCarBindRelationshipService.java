package com.zwc.apiboss.service;

import com.zwc.apiboss.remote.ServiceDriverUserClient;
import com.zwc.internalcommon.dto.DriverCarBindingRelationship;
import com.zwc.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverCarBindRelationshipService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        return serviceDriverUserClient.bind(driverCarBindingRelationship);
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        return serviceDriverUserClient.unbind(driverCarBindingRelationship);
    }
}
