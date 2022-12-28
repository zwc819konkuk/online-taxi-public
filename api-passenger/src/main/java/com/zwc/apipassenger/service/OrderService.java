package com.zwc.apipassenger.service;

import com.zwc.apipassenger.remote.ServiceOrderClient;
import com.zwc.internalcommon.dto.ResponseResult;
import com.zwc.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    ServiceOrderClient serviceOrderClient;

    public ResponseResult add(OrderRequest orderRequest){
        return serviceOrderClient.add(orderRequest);
    }
}
