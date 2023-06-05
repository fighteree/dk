package com.yzdbx.dkpan.exception;

/**
 * @Author: 一只大笨象
 * @Date: 2023/05/31
 * @description: 业务异常
 */
public class BusinessException extends RuntimeException{

    public BusinessException(String msg) {
        super(msg);
    }
}
