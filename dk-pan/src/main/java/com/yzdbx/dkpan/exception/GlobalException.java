package com.yzdbx.dkpan.exception;

import com.yzdbx.dkpan.entity.RestEntityResponse;
import com.yzdbx.dkpan.entity.RestVoidResponse;
import com.yzdbx.dkpan.entity.enums.HttpCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * @Author: 一只大笨象
 * @Date: 2023/05/31
 * @description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {
    /**
     * 默认异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestVoidResponse defaultHandler(Exception e) {
        log.error("业务出现了异常,异常是[{}]", e.getMessage(), e);
        return RestVoidResponse.failure(HttpCode.SYSTEM_ERROR.getCode(), HttpCode.SYSTEM_ERROR.getMsg());
    }

    /**
     * 参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidParamsException.class)
    public RestEntityResponse<Map<String, String>> paramsExceptionHandler(InvalidParamsException e) {
        return RestEntityResponse.failure(HttpCode.PARAMS_ERROR.getCode(), HttpCode.PARAMS_ERROR.getMsg());
    }

    @ExceptionHandler(BusinessException.class)
    public RestVoidResponse businnessExceptionHandler(BusinessException e) {
        log.error("出现了异常[{}]", e.getMessage(), e);
        return RestVoidResponse.failure(HttpCode.BUSINESS_ERROR.getCode(), e.getMessage() );
    }
}
