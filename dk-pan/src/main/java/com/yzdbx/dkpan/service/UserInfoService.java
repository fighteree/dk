package com.yzdbx.dkpan.service;

import com.yzdbx.dkpan.service.domain.LoginBack;
import com.yzdbx.dkpan.service.domain.UserInfo;

/**
 * @Author: 一只大笨象
 * @Date: 2023/05/30
 * @description:
 */
public interface UserInfoService {
    /**
     * 用户注册
     *
     * @param userInfo
     */
    void userRegisterHandler(UserInfo userInfo);

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @param type  0:注册 1:登录
     * @return
     */
    String sendEmail(String email, Integer type);

    /**
     * 用户登录
     *  @param
     * @param
     * @param loginType 1:密码登录 2:邮箱验证码登录
     * @param emailCode 用户填写的邮箱验证码
     */
    LoginBack userLoginHandler(UserInfo loginParams, Integer loginType, String emailCode);
}
