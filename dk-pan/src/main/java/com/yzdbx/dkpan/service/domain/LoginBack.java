package com.yzdbx.dkpan.service.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: 一只大笨象
 * @Date: 2023/06/01
 * @description: 登录成功后返回的数据
 */
@Data
@Builder
public class LoginBack {
    private String nickName;
    private String token;
    private String email;
}
