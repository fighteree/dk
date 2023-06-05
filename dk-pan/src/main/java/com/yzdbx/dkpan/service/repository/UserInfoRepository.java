package com.yzdbx.dkpan.service.repository;


import com.yzdbx.dkpan.service.domain.UserInfo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author 一只大笨象
 * @since 2023-05-30
 */
public interface UserInfoRepository  {
    /**
     * 发送邮箱验证码
     * @param email
     */
    String sendEmail(String email);

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    UserInfo findUserByEmail(String email);

    /**
     * 添加用户
     * @param userInfo
     */
    void addUser(UserInfo userInfo);
}
