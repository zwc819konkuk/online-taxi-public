package com.zwc.internalcommon.request;

import com.zwc.internalcommon.dto.PointDTO;
import lombok.Data;

@Data
public class PointRequest {

    private String tid;
    private String trid;
    private PointDTO[] points;
}
