package com.zwc.internalcommon.request;

import com.zwc.internalcommon.dto.PointDTO;
import lombok.Data;

@Data
public class ApiDriverPointRequest {

    public Long carId;

    public PointDTO[] points;
}
