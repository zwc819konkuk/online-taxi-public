package com.zwc.internalcommon.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zwc
 * @since 2022-12-23
 */
@Data
public class DriverCarBindingRelationship implements Serializable {
    private Long id;

    /**
     * 司机id
     */
    private Long driverId;

    /**
     * 车id
     */
    private Long carId;

    /**
     * 1绑定2解绑
     */
    private Integer bindState;

    private LocalDateTime bindingTime;

    private LocalDateTime unBindingTime;

}
