package com.zwc.internalcommon.constant;

import lombok.Data;
import lombok.Getter;


public enum CommonStatusEnum {

    /**
     * 验证码错误提示：1000-1099
     */
    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),
    /**
     * token类提示，1100-1199
     */
    TOKEN_ERROR(1199,"TOKEN错误"),
    /**
     * 用户提示：1200-1299
     */
    USER_NOT_EXIST(1022,"当前用户不存在"),
    /**
     * 计价规格不存在:1300-1399
     */
    PRICE_RULE_EMPTY(1300,"计价规格不存在"),
    /**
     * 成功
     */
    SUCCESS(1, "success"),
    /**
     * 失败
     */
    FAIL(0, "fail");

    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
