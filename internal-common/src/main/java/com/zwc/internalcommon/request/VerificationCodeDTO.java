package com.zwc.internalcommon.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;

    private String driverPhone;

}
