package com.zwc.internalcommon.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zwc
 * @since 2023-01-05
 */
@Data
public class PriceRule implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cityCode;

    private String vehicleType;

    private Double startFare;

    private Integer startMile;

    private Double unitPricePerMile;

    private Double unitPricePerMinute;

    private Integer fareVersion;

    private String fareType;

}
