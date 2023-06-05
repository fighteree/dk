package com.yzdbx.dkpan.entity.enums;

/**
 * @Author: 一只大笨象
 * @Date: 2023/05/31
 * @description:
 */
public enum HttpCode {
    PARAMS_ERROR(400, "参数异常"),
    SYSTEM_ERROR(999, "系统繁忙"),


    BUSINESS_ERROR(777, "业务出错");

    int code;
    String msg;

    HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

