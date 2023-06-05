package com.yzdbx.dkpan.dao.repository;


import com.yzdbx.dkpan.controller.converter.UserInfoConverter;
import com.yzdbx.dkpan.dao.po.UserInfoPo;
import com.yzdbx.dkpan.dao.po.converter.UserInfoPoConverter;
import com.yzdbx.dkpan.entity.Constants;
import com.yzdbx.dkpan.dao.mysql.UserInfoMapper;
import com.yzdbx.dkpan.service.domain.UserInfo;
import com.yzdbx.dkpan.service.repository.UserInfoRepository;
import com.yzdbx.dkpan.util.EmailUtil;
import com.yzdbx.dkpan.entity.data.RedisMapper;
import com.yzdbx.dkpan.util.SnowFlakeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author 一只大笨象
 * @since 2023-05-30
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserInfoRepositoryImpl implements UserInfoRepository {
    private final UserInfoMapper userInfoMapper;
    private final RedisMapper redisMapper;
    private final UserInfoConverter userInfoConverter;
    private final UserInfoPoConverter poConverter;
    private final EmailUtil emailUtil;

    /**
     * 发送邮箱验证码
     *
     * @param email
     */
    @Override
    public String sendEmail(String email) {
        String emailCheckCode = emailUtil.sendMail(email);
        String key = String.format(Constants.CHECK_CODE_EMAIL_KEY, email);
        redisMapper.setKey(key, emailCheckCode, 1, TimeUnit.MINUTES);
        return emailCheckCode;
    }

    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    @Override
    public UserInfo findUserByEmail(String email) {
        UserInfoPo po = userInfoMapper.findUserByEmail(email);
        return userInfoConverter.from(po);
    }
    /**
     * 添加用户
     *
     * @param userInfo
     */
    @Override
    public void addUser(UserInfo userInfo) {
        UserInfoPo po = poConverter.from(userInfo);
        po.setCreateTime(LocalDateTime.now())
                .setUserId(new SnowFlakeUtil().nextId())
                .setStatus(true)
                .setTotalCapcity(Constants.DEFAULT_CAPCITY)
                .setUsedCapcity(0L);
        userInfoMapper.insert(po);
    }
}
