package com.zwc.servicedriveruser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("driver_car_binding_relationship")
public class DriverCarBindingRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
