package com.yzdbx.dkpan.service.domain;

import lombok.Data;

/**
 * @Author: 一只大笨象
 * @Date: 2023/05/31
 * @description:
 */
@Data
public class UserInfo {
    private Long userId;
    private String passWord;
    private String nickName;
    private String email;
    private String emailCode;
}
